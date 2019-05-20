package ua.tihonchik.dmitriy.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

@Component
public class CustomUserDetails implements UserDetails {

    private int id;
    private String email;
    private String password;
    private String name;
    private boolean enabled = true;
    private Set<GrantedAuthority> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

//    public boolean hasRole(String role) {
//        return getAuthorities().stream().anyMatch((s) -> s.getAuthority().toLowerCase().equals(role.toLowerCase()));
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public void setRoles(Collection<String> stringRoles) {
//        this.roles = stringRoles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
//    }

    public void setRoles(Set<GrantedAuthority> roles) {
        this.roles = roles;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
