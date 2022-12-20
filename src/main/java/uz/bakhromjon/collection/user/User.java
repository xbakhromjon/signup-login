package uz.bakhromjon.collection.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import uz.bakhromjon.collection.role.ERole;
import uz.bakhromjon.collection.role.Role;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author : Bakhromjon Khasanboyev
 * @since : 31/10/22, Mon, 21:41
 **/
@Setter
@Getter
@AllArgsConstructor
@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "phone"),
        })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String phone;

    private String name;


    @Size(max = 120)
    private String password;

    private String fakePassword = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString());


    @Enumerated(EnumType.STRING)
    private ERole role;

    private Boolean isDeleted = false;

    public User() {
    }

    public User(String phone, String name) {
        this.phone = phone;
        this.name = name;
    }
}