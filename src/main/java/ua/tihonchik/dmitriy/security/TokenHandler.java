package ua.tihonchik.dmitriy.security;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TokenHandler {

    Optional<Object> extractUserId(String token);

    String generateAccessToken(Object id, LocalDateTime expires);

}
