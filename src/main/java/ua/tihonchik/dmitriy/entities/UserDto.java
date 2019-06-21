package ua.tihonchik.dmitriy.entities;

import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public class UserDto extends SimplifiedUser {

    private Set<String> roles;

    public UserDto(@NonNull User user) {
        super(user.getId(), user.getEmail(), user.getName());
        this.roles = user.getRoles().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
