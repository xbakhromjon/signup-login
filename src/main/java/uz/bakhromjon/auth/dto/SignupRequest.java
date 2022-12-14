package uz.bakhromjon.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * @author : Bakhromjon Khasanboyev
 * @since : 31/10/22, Mon, 21:58
 **/
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    private String username;
    private String password;
    private String email;
    private Set<String> role;
}
