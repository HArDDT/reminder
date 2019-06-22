package ua.tihiy.reminder.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Component
public class TokenHandlerImpl implements TokenHandler {

    private final SecretKey secretKey;
    private static final String JWT_KEY = "jwtReminderKey";
    private Logger logger = LoggerFactory.getLogger(TokenHandlerImpl.class);

    public TokenHandlerImpl() {
        byte[] decodedKey = Base64.getDecoder().decode(JWT_KEY.getBytes());
        secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    @Override
    public Optional<Integer> extractUserId(@NotNull String token) {
        try {
            return Optional.ofNullable(Jwts.parser().
                    setSigningKey(secretKey).
                    parseClaimsJws(token).
                    getBody().
                    getId()).map(Integer::parseInt);
        } catch (ExpiredJwtException ex) {
            logger.error("token is expire!");
        } catch (MalformedJwtException ex) {
            logger.error("incorrect token!");
        } catch (IllegalArgumentException ex) {
            logger.error(ex.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public String generateAccessToken(int id, @NonNull LocalDateTime expires) {
        return Jwts.builder()
                .setId(String.valueOf(id))
                .setExpiration(Date.from(expires.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

}
