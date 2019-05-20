package ua.tihonchik.dmitriy.controllers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.tihonchik.dmitriy.entities.User;
import ua.tihonchik.dmitriy.services.UserService;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class UserController {

    private UserService userService;
    private PasswordEncoder encoder;
    //private User user;

    public UserController(UserService userService, PasswordEncoder encoder){//, User user) {
        this.userService = userService;
        this.encoder = encoder;
        //this.user = user;
    }

    @PostMapping(value = "/protected/create_user")
    public int createEvent(@RequestBody Map<String, Object> payload) {

        Object objRoles = payload.get("roles");
        List roles;
        if (Objects.isNull(objRoles) || !(objRoles instanceof List)){
            throw new IllegalArgumentException("");
        }else {
            roles = (List)objRoles;
        }
        User user = userService.getUserPrototypeBean();
        user.setEmail(String.valueOf(payload.get("email")));
        user.setPassword(encoder.encode(String.valueOf(payload.get("password"))));
        user.setName(String.valueOf(payload.get("name")));
        user.setRoles(roles);

        return userService.createUser(user);

//        return userService.createUser(new User(
//                String.valueOf(payload.get("email")),
//                encoder.encode(String.valueOf(payload.get("password"))),
//                String.valueOf(payload.get("name")),
//                (List)(payload.get("roles"))));
    }

}
