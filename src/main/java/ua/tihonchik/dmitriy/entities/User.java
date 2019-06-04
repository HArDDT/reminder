package ua.tihonchik.dmitriy.entities;

import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

public interface User {

    boolean hasRole(String role);

    int getId();

    void setId(int id);

    String getEmail();

    void setEmail(String email);

    String getPassword();

    void setPassword(String password);

    String getName();

    void setName(String name);

    Set<GrantedAuthority> getRoles();

    void setRoles(Set<GrantedAuthority> roles);

}
