package ua.tihonchik.dmitriy.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import ua.tihonchik.dmitriy.entities.User;
import ua.tihonchik.dmitriy.entities.UserImpl;
import ua.tihonchik.dmitriy.services.UserService;

import java.util.Collection;
import java.util.Optional;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/protected/create_user")
    public int createEvent(@RequestBody UserImpl user) {
        return userService.createUser((User) user);
    }

    @GetMapping(value = "/protected/get-user-by-email/{email}")
    public User getUserByEmail(@PathVariable String email){
        return userService.getUserByEmail(email).orElseGet(null);
    }

    @GetMapping(value = "/protected/get-user-by-id/{id}")
    public User getUserById(@PathVariable int id){
        return userService.getUserById(id).orElseGet(null);
    }

    @GetMapping(value = "/protected/get-users")
    public Collection<User> getUsers(){
        return userService.getUsers();
    }

    @DeleteMapping(value = "/protected/delete-user/{id}")
    public void deleteUser(@PathVariable int id){
        userService.deleteUser(id);
    }

}
