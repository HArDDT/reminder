package ua.tihonchik.dmitriy.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ua.tihonchik.dmitriy.entities.User;
import ua.tihonchik.dmitriy.security.TokenHandler;
import ua.tihonchik.dmitriy.services.UserService;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@RestController
public class AuthController {

    private UserService service;
    private TokenHandler handler;
    private PasswordEncoder passwordEncoder;

    public AuthController(UserService service, TokenHandler handler, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.handler = handler;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "/sing-up")
    public void getAuthentication(HttpServletResponse response, @RequestParam("email") String email, @RequestParam("password") String pass) {

        try {
            User userDB = service.getUserByEmail(email)
                    .filter(user -> user.getEmail().equals(email) && passwordEncoder.matches(pass, user.getPassword()))
                    .orElseThrow(IllegalAccessException::new);
            String token = handler.generateAccessToken(userDB.getId(), LocalDateTime.now().plusDays(1));
            response.addHeader("X-Auth-Token", token);
        } catch (IllegalAccessException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden", ex);
        }


    }

}
