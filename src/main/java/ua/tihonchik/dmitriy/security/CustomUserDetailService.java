package ua.tihonchik.dmitriy.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ua.tihonchik.dmitriy.repositories.UserRepository;

@Component
public class CustomUserDetailService implements UserDetailsService {

    private UserRepository repository;
    private Logger logger = LoggerFactory.getLogger(CustomUserDetailService.class);

    public CustomUserDetailService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.loadUserByUsername(username);
    }


}
