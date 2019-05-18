package ua.tihonchik.dmitriy.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class User implements UserDetails {

    private int id;
    private String email;
    private String password;
    private String name;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;
    private Set<GrantedAuthority> roles;

    public User() {

    }

    public User(int id, String email, String name, String password, Set<GrantedAuthority> roles) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.roles = roles;
    }

    public User(String email, String password, String name, Set<GrantedAuthority> roles) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.roles = roles;
    }

    public User(String email, String password, String name, Collection<String> roles) {
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
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
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

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setRoles(Set<GrantedAuthority> roles) {
        this.roles = roles;
    }

    public void setRoles(Collection<String> stringRoles) {
        this.roles = stringRoles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
