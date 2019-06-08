package ua.tihonchik.dmitriy.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ua.tihonchik.dmitriy.services.UserService;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Component
public class TokenHandler {

    private final SecretKey secretKey;
    private static final String JWT_KEY = "jwtReminderKey";

    public TokenHandler() {
        byte[] decodedKey = Base64.getDecoder().decode(JWT_KEY.getBytes());
        secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    public Optional<Object> extractUserId(@NotNull String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            Claims body = claimsJws.getBody();
            return Optional.ofNullable(body.getId());
        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }

    public String generateAccessToken(@NonNull Object id, @NonNull LocalDateTime expires) {
        return Jwts.builder()
                .setId(String.valueOf(id))
                .setExpiration(Date.from(expires.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public boolean tokenIsExpired(@NotNull String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            Claims body = claimsJws.getBody();
            LocalDateTime expirationDate = body.getExpiration().toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDateTime();

            return expirationDate.isBefore(LocalDateTime.now());
        } catch (RuntimeException e) {
            throw new RuntimeException("bad token!");
        }
    }

}
