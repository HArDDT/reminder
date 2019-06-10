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
public class TokenAuthenticationServiceImpl implements TokenAuthenticationService {

    private static final String AUTH_HEADER_NAME = "X-Auth-Token";
    private TokenHandler handler;
    private UserService service;
    private Logger logger = LoggerFactory.getLogger(TokenAuthenticationServiceImpl.class);

    public TokenAuthenticationServiceImpl(TokenHandler handler, UserService service) {
        this.handler = handler;
        this.service = service;
    }

    @Override
    public Optional<Authentication> getAuthentication(@NotNull HttpServletRequest request) {

        try {
            return Optional.ofNullable(request.getHeader(AUTH_HEADER_NAME))
                    .flatMap(handler::extractUserId)
                    .flatMap(service::getUserById)
                    .map(UserAuthentication::new);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return Optional.empty();
        }

    }

}
