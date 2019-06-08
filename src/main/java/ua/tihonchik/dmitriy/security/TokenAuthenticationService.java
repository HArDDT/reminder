package ua.tihonchik.dmitriy.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ua.tihonchik.dmitriy.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Optional;

@Component
public class TokenAuthenticationService {

    private static final String AUTH_HEADER_NAME = "X-Auth-Token";
    private TokenHandler handler;
    private UserService service;

    public TokenAuthenticationService(TokenHandler handler , UserService service) {
        this.handler = handler;
        this.service = service;
    }

    public Optional<Authentication> getAuthentication(@NotNull HttpServletRequest request) {

        String header = request.getHeader(AUTH_HEADER_NAME);

        if (Objects.nonNull(header) && !header.isBlank() && !handler.tokenIsExpired(header)) {
            return Optional.of(header)
                    .flatMap(handler::extractUserId)
                    .flatMap(service::getUserById)
                    .map(UserAuthentication::new);
        }
        return Optional.empty();
    }



}
