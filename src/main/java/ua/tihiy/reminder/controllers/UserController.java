package ua.tihiy.reminder.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.tihiy.reminder.entities.User;
import ua.tihiy.reminder.entities.UserDto;
import ua.tihiy.reminder.services.UserService;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/protected/user")
    public Object createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping(value = "/protected/user", params = "mail")
    public UserDto getUserByEmail(@RequestParam("mail") String email){
        return userService.getUserByEmail(email).map(UserDto::new).orElse(null);
    }

    @GetMapping(value = "/protected/user", params = "id")
    public UserDto getUserById(@RequestParam("id") int id){
        return userService.getUserById(id).map(UserDto::new).orElse(null);
    }

    @GetMapping(value = "/protected/users")
    public Collection<UserDto> getUsers(){
        return userService.getUsers().stream().map(UserDto::new).collect(Collectors.toSet());
    }

    @DeleteMapping(value = "/protected/user/{id}")
    public void deleteUser(@PathVariable int id){
        userService.deleteUser(id);
    }

}
