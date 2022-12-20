package uz.bakhromjon.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.bakhromjon.auth.dto.CheckOTPDTO;
import uz.bakhromjon.auth.dto.LoginDTO;
import uz.bakhromjon.auth.dto.SignupDTO;
import uz.bakhromjon.collection.otp.OTPService;

/**
 * @author : Bakhromjon Khasanboyev
 * @since : 31/10/22, Mon, 21:54
 **/
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthService service;


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDTO loginRequest) {
        return service.authenticateUser(loginRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupDTO signUpRequest) {
        return service.registerUser(signUpRequest);
    }

    @PostMapping("/check-otp")
    public ResponseEntity<?> checkOTP(@RequestBody CheckOTPDTO checkOTPDTO) {
        return service.checkOTP(checkOTPDTO);
    }

    @PostMapping("/distributor")
    public ResponseEntity<?> distributor(@RequestParam String phone) {
        return service.distributor(phone);
    }


    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        return service.logoutUser();
    }
}