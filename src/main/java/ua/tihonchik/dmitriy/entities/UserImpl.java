package ua.tihonchik.dmitriy.entities;

import org.springframework.security.core.GrantedAuthority;

import java.util.Set;
import java.util.UUID;

public class UserImpl implements User {

    private Object id;
    private String email;
    private String password;
    private String name;
    private Set<GrantedAuthority> roles;

    public UserImpl() {
    }

    public UserImpl(Object id, String email, String name, String password, Set<GrantedAuthority> roles) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.roles = roles;
    }

    public UserImpl(String email, String password, String name) {
        this.id = UUID.randomUUID().toString();
        this.email = email;
        this.name = name;
        this.password = password;
    }

    @Override
    public boolean hasRole(String role) {
        return roles.stream().anyMatch((s) -> s.getAuthority().toLowerCase().equals(role.toLowerCase()));
    }

    @Override
    public Object getId() {
        return id;
    }

    @Override
    public void setId(Object id) {
        this.id = id;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Set<GrantedAuthority> getRoles() {
        return roles;
    }

    @Override
    public void setRoles(Set<GrantedAuthority> roles) {
        this.roles = roles;
    }
}
