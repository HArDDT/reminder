package ua.tihiy.reminder.users;

import java.util.HashSet;
import java.util.Set;

public class User {

    private int id;
    private String email;
    private String password;
    private String name;
    private Set<String> roles = new HashSet<>();

    public User() {
    }

    public User(int id, String email, String name, String password, Set<String> roles) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.roles = roles;
    }

    public User(String email, String password, String name) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public boolean hasRole(String role) {
        return roles.contains(role.toUpperCase());
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
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


    public Set<String> getRoles() {
        return roles;
    }


    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
