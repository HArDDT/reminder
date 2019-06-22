package ua.tihiy.reminder.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.tihiy.reminder.entities.User;
import ua.tihiy.reminder.services.UserService;

public class UserDetailsServiceImpl implements UserDetailsService {

    private TokenHandler handler;
    private UserService service;

    public UserDetailsServiceImpl(TokenHandler handler, UserService service) {
        this.handler = handler;
        this.service = service;
    }

    @Override
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
        User user = handler.extractUserId(token)
                .flatMap(service::getUserById)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        return new UserDetailsImpl(user);
    }



}
