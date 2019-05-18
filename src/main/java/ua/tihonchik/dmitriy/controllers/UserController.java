package ua.tihonchik.dmitriy.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.tihonchik.dmitriy.entities.User;
import ua.tihonchik.dmitriy.services.UserService;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/protected/create_user")
    public int createEvent(@RequestBody Map<String, Object> payload) {
        return userService.createUser(new User(
                String.valueOf(payload.get("email")),
                String.valueOf(payload.get("password")),
                String.valueOf(payload.get("name")),
                (List)(payload.get("roles"))));
    }

}
