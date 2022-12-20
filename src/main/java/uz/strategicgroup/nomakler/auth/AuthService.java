package uz.strategicgroup.nomakler.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.strategicgroup.nomakler.auth.dto.CheckOTPDTO;
import uz.strategicgroup.nomakler.auth.dto.LoginDTO;
import uz.strategicgroup.nomakler.auth.dto.SignupDTO;
import uz.strategicgroup.nomakler.collection.message.MessageSender;
import uz.strategicgroup.nomakler.collection.otp.OTPService;
import uz.strategicgroup.nomakler.collection.role.ERole;
import uz.strategicgroup.nomakler.collection.role.RoleRepository;
import uz.strategicgroup.nomakler.collection.user.*;
import uz.strategicgroup.nomakler.enums.EAuthResponse;
import uz.strategicgroup.nomakler.exception.exception.UniversalException;
import uz.strategicgroup.nomakler.response.MessageResponse;

import java.util.*;

/**
 * @author : Bakhromjon Khasanboyev
 * @since : 01/11/22, Tue, 20:36
 **/
@Service
public class AuthService {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;


    @Autowired
    UserDetailsServiceImpl userDetailsService;


    @Autowired
    private OTPService otpService;

    @Autowired
    private MessageSender messageSender;

    public ResponseEntity<?> authenticateUser(LoginDTO loginRequest) {
        UserDetailsImpl userDetails = null;
        Optional<User> userOptional = userRepository.findByPhoneAndNotIsDeleted(loginRequest.getPhone());
        if (userOptional.isEmpty()) {
            throw new UniversalException("Bad credentials", HttpStatus.BAD_REQUEST);
        }
        User user = userOptional.get();
        if (Objects.nonNull(loginRequest.getPassword())) {
            if (!encoder.matches(loginRequest.getPassword(), user.getPassword())) {
                throw new UniversalException("Bad credentials", HttpStatus.BAD_REQUEST);
            }
        } else {
            CheckOTPDTO checkOTPDTO = new CheckOTPDTO(loginRequest.getPhone(), loginRequest.getOTP());
            checkOTP(checkOTPDTO);
        }
        userDetails = UserDetailsImpl.build(user);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        authentication.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(userDetails.getId(),
                        userDetails.getUsername(),
                        null));
    }

    public ResponseEntity<?> registerUser(SignupDTO signupDTO) {
        Boolean existsUser = userRepository.existsByPhoneAndNotIsDeleted(signupDTO.getPhone());
        if (existsUser != null && existsUser) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Phone is already taken!"));
        }
        User user = new User(signupDTO.getPhone(), signupDTO.getName());
        user.setRole(ERole.ROLE_USER);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }


    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(new MessageResponse("You've been signed out!"));
    }

    public ResponseEntity<?> checkOTP(CheckOTPDTO checkOTPDTO) {
        int OTP = otpService.getOtp(checkOTPDTO.getPhone());
        if (OTP != checkOTPDTO.getOtp()) {
            throw new UniversalException("Incorrect code", HttpStatus.BAD_REQUEST);
        }
        otpService.clearOTP(checkOTPDTO.getPhone());
        return ResponseEntity.ok(new MessageResponse("successfully code!"));
    }

    public ResponseEntity<?> distributor(String phone) {
        String authResponse = null;
        Optional<User> userOptional = userRepository.findByPhoneAndNotIsDeleted(phone);
        User user = userOptional.orElseThrow(() -> {
            throw new UniversalException("User not found %s with phone".formatted(phone), HttpStatus.BAD_REQUEST);
        });
        if (user != null) {
            if (user.getPassword() == null) {
                messageSender.send(phone);
                authResponse = EAuthResponse.LOGIN_OTP.toString();
            } else {
                authResponse = EAuthResponse.LOGIN_PASSWORD.toString();
            }
        } else {
            messageSender.send(phone);
            authResponse = EAuthResponse.REGISTER.toString();
        }

        return ResponseEntity.ok(authResponse);
    }
}
