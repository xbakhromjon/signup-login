package uz.strategicgroup.nomakler.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author : Bakhromjon Khasanboyev
 * @since : 31/10/22, Mon, 21:57
 **/
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    private String phone;
    private String password;
    private Integer OTP;
}
