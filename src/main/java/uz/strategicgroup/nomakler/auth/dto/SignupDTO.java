package uz.strategicgroup.nomakler.auth.dto;

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
public class SignupDTO {
    private String phone;
    private String name;
}
