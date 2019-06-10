package ua.tihonchik.dmitriy.security;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface TokenAuthenticationService {
    Optional<Authentication> getAuthentication(HttpServletRequest request);
}
