package ua.tihonchik.dmitriy.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import ua.tihonchik.dmitriy.entities.User;

import javax.validation.constraints.NotNull;


public class UserAuthentication extends AbstractAuthenticationToken {

    private User user;

    public UserAuthentication(@NotNull User user) {
        super(user.getRoles());
        this.user = user;
        this.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return user;
    }

    @Override
    public Object getPrincipal() {
        return user;
    }
}
