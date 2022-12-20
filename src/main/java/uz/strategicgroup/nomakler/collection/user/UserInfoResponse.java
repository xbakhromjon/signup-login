package uz.strategicgroup.nomakler.collection.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author : Bakhromjon Khasanboyev
 * @since : 31/10/22, Mon, 21:57
 **/
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {
    private Long id;
    private String phone;
    private List<String> roles;
}
