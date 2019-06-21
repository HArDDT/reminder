package ua.tihonchik.dmitriy.entities;

import org.springframework.security.core.GrantedAuthority;

import java.util.Set;
import java.util.UUID;

public class User {

    private Object id;
    private String email;
    private String password;
    private String name;
    private Set<GrantedAuthority> roles;

    public User() {
    }

    public User(Object id, String email, String name, String password, Set<GrantedAuthority> roles) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.roles = roles;
    }

    public User(String email, String password, String name) {
        this.id = UUID.randomUUID().toString();
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public boolean hasRole(String role) {
        return roles.stream().anyMatch((s) -> s.getAuthority().toLowerCase().equals(role.toLowerCase()));
    }


    public Object getId() {
        return id;
    }


    public void setId(Object id) {
        this.id = id;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public Set<GrantedAuthority> getRoles() {
        return roles;
    }


    public void setRoles(Set<GrantedAuthority> roles) {
        this.roles = roles;
    }
}
