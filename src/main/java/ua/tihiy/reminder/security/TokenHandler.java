package ua.tihiy.reminder.security;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TokenHandler {

    Optional<Integer> extractUserId(String token);

    String generateAccessToken(int id, LocalDateTime expires);

}
