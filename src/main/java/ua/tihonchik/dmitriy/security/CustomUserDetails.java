package ua.tihonchik.dmitriy.security;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CustomUserDetails implements UserDetails {

    private int id;
    private String email;
    private String password;
    private String name;
    private boolean enabled = true;
    private Set<GrantedAuthority> roles;

    public CustomUserDetails() {

    }

    public CustomUserDetails(int id, String email, String name, String password, Set<GrantedAuthority> roles) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.roles = roles;
    }

    public CustomUserDetails(String email, String password, String name, Set<GrantedAuthority> roles) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.roles = roles;
    }

    public CustomUserDetails(String email, String password, String name, Collection<String> roles) {
        this.email = email;
        this.name = name;
        this.password = password;
        setRoles(roles);
    }

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

    public boolean hasRole(String role) {
        return getAuthorities().stream().anyMatch((s) -> s.getAuthority().toLowerCase().equals(role.toLowerCase()));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRoles(Collection<String> stringRoles) {
        this.roles = stringRoles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }

}
