package uz.bakhromjon.collection.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author : Bakhromjon Khasanboyev
 * @since : 31/10/22, Mon, 21:47
 **/
public interface UserDetailsService {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
