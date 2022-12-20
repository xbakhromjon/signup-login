package uz.bakhromjon.collection.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import uz.bakhromjon.collection.otp.OTPService;

/**
 * @author : Bakhromjon Khasanboyev
 **/
@Component
public class MessageSender {
    @Autowired
    private OTPService otpService;
    public ResponseEntity<?> send(String phone) {
        int OTP = otpService.generateOTP(phone);
        System.out.println(OTP);
        //TODO send message
        return ResponseEntity.ok("success");
    }
}
