package ua.tihonchik.dmitriy.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.tihonchik.dmitriy.entities.SimplifiedUser;
import ua.tihonchik.dmitriy.entities.User;
import ua.tihonchik.dmitriy.entities.UserImpl;
import ua.tihonchik.dmitriy.services.UserService;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/protected/create_user")
    public Object createUser(@RequestBody UserImpl user) {
        return userService.createUser(user);
    }

    @GetMapping(value = "/protected/get-user-by-email/{email}")
    public SimplifiedUser getUserByEmail(@PathVariable String email){
        Optional<User> optionalUser = userService.getUserByEmail(email);
        return optionalUser.map(SimplifiedUser::new).orElse(null);
    }

    @GetMapping(value = "/protected/get-user-by-id/{id}")
    public SimplifiedUser getUserById(@PathVariable String id){
        Optional<User> optionalUser = userService.getUserById(id);
        return optionalUser.map(SimplifiedUser::new).orElse(null);
    }

    @GetMapping(value = "/protected/get-users")
    public Collection<SimplifiedUser> getUsers(){
        return userService.getUsers().stream().map(SimplifiedUser::new).collect(Collectors.toSet());
    }

    @DeleteMapping(value = "/protected/delete-user/{id}")
    public void deleteUser(@PathVariable String id){
        userService.deleteUser(id);
    }

}
