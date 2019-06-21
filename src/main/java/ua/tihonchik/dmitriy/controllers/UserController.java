package ua.tihonchik.dmitriy.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.tihonchik.dmitriy.entities.SimplifiedUserToFront;
import ua.tihonchik.dmitriy.entities.User;
import ua.tihonchik.dmitriy.services.UserService;

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
    public SimplifiedUserToFront getUserByEmail(@RequestParam("mail") String email){
        return userService.getUserByEmail(email).map(SimplifiedUserToFront::new).orElse(null);
    }

    @GetMapping(value = "/protected/user", params = "id")
    public SimplifiedUserToFront getUserById(@RequestParam("id") int id){
        return userService.getUserById(id).map(SimplifiedUserToFront::new).orElse(null);
    }

    @GetMapping(value = "/protected/users")
    public Collection<SimplifiedUserToFront> getUsers(){
        return userService.getUsers().stream().map(SimplifiedUserToFront::new).collect(Collectors.toSet());
    }

    @DeleteMapping(value = "/protected/user/{id}")
    public void deleteUser(@PathVariable String id){
        userService.deleteUser(id);
    }

}
