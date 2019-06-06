package ua.tihonchik.dmitriy.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.validation.constraints.NotNull;
import java.util.Base64;
import java.util.Optional;

@Component
public class TokenHandler {

    private final SecretKey secretKey;
    private static final String JWT_KEY = "jwtReminderKey";

    public TokenHandler() {
        byte[] decodedKey = Base64.getDecoder().decode(JWT_KEY);
        secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    public Optional<Integer> extractUserId(@NotNull String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            Claims body = claimsJws.getBody();
            return Optional.ofNullable(body.getId()).map(Integer::valueOf);
        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }

}
