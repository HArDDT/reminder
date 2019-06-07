package ua.tihonchik.dmitriy.controllers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.tihonchik.dmitriy.entities.User;
import ua.tihonchik.dmitriy.security.TokenHandler;
import ua.tihonchik.dmitriy.services.UserService;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Optional;

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
    public void getAuthentication(HttpServletResponse response, @RequestParam("email") String email, @RequestParam("password") String pass){
        Optional<User> optional = service.getUserByEmail(email);
        if (optional.isEmpty()){
            System.out.println("User not found!");
        }else {
            User user = optional.get();
            if (user.getEmail().equals(email) && passwordEncoder.matches(pass, user.getPassword())){
                String token = handler.generateAccessToken(user.getId(), LocalDateTime.now().plusMinutes(15));
                response.addHeader("X-Auth-Token", token);
            }
        }

    }

}
