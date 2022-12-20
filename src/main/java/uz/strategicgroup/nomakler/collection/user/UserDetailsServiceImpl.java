package uz.strategicgroup.nomakler.collection.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author : Bakhromjon Khasanboyev
 * @since : 31/10/22, Mon, 21:48
 **/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetailsImpl loadUserByUsername(String phone) throws UsernameNotFoundException {
        User user = userRepository.findByPhoneAndNotIsDeleted(phone)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with phone: " + phone));
        return UserDetailsImpl.build(user);
    }


}


