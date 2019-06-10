package ua.tihonchik.dmitriy.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ua.tihonchik.dmitriy.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Component
public class TokenAuthenticationService {

    private static final String AUTH_HEADER_NAME = "X-Auth-Token";
    private TokenHandler handler;
    private UserService service;
    private Logger logger = LoggerFactory.getLogger(TokenAuthenticationService.class);

    public TokenAuthenticationService(TokenHandler handler, UserService service) {
        this.handler = handler;
        this.service = service;
    }

    public Optional<Authentication> getAuthentication(@NotNull HttpServletRequest request) {

        String header = request.getHeader(AUTH_HEADER_NAME);

        try {
            return Optional.ofNullable(header)
                    .flatMap(handler::extractUserId)
                    .flatMap(service::getUserById)
                    .map(UserAuthentication::new);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return Optional.empty();
        }

    }

}
