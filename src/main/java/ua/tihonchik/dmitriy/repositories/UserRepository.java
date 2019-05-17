package ua.tihonchik.dmitriy.repositories;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserRepository {

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}
