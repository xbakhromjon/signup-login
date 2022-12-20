package uz.strategicgroup.nomakler.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.strategicgroup.nomakler.collection.role.ERole;
import uz.strategicgroup.nomakler.collection.role.Role;
import uz.strategicgroup.nomakler.collection.role.RoleRepository;
import uz.strategicgroup.nomakler.collection.user.User;
import uz.strategicgroup.nomakler.collection.user.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;

@Configuration
public class DataLoader implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Value(value = "${spring.jpa.hibernate.ddl-auto}")
    private String ddl;

    @Override
    public void run(String... args) throws Exception {
        if (ddl.equalsIgnoreCase("create") || ddl.equalsIgnoreCase("create-drop")) {
            Role role1 = new Role(1, ERole.ROLE_USER);
            Role role2 = new Role(2, ERole.ROLE_MODERATOR);
            Role role3 = new Role(3, ERole.ROLE_ADMIN);
            roleRepository.saveAll(new ArrayList<>(Arrays.asList(role1, role2, role3)));

            User user = new User("998945520609", "name");
            userRepository.save(user);
        }
    }
}
