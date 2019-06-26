package ua.tihiy.reminder.dto;

import org.springframework.lang.NonNull;
import ua.tihiy.reminder.entities.User;

import java.util.Set;

public class UserDto extends SimplifiedUser {

    private Set<String> roles;

    public UserDto(@NonNull User user) {
        super(user.getId(), user.getEmail(), user.getName());
        this.roles = user.getRoles();
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
