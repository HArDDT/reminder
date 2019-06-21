package ua.tihonchik.dmitriy.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ua.tihonchik.dmitriy.entities.User;
import ua.tihonchik.dmitriy.security.TokenHandlerImpl;
import ua.tihonchik.dmitriy.services.UserService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

@RestController
public class AuthController {

    private UserService service;
    private TokenHandlerImpl handler;
    private PasswordEncoder passwordEncoder;

    public AuthController(UserService service, TokenHandlerImpl handler, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.handler = handler;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "/sing-up")
    public Map<String, String> getAuthentication(@RequestParam("email") String email, @RequestParam("password") String pass) {

        User userDB = service.getUserByEmail(email)
                .filter(user -> user.getEmail().equals(email) && passwordEncoder.matches(pass, user.getPassword()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials!"));

        String token = handler.generateAccessToken(userDB.getId(), LocalDateTime.now().plusDays(1));

        return Collections.singletonMap("X-Auth-Token", token);

    }

}
