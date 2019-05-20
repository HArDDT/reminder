package ua.tihonchik.dmitriy.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class User {

    private int id;
    private String email;
    private String password;
    private String name;
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


    public boolean hasRole(String role) {
        return roles.stream().anyMatch((s) -> s.getAuthority().toLowerCase().equals(role.toLowerCase()));
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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
