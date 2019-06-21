package ua.tihonchik.dmitriy.security;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TokenHandler {

    Optional<Integer> extractUserId(String token);

    String generateAccessToken(int id, LocalDateTime expires);

}
