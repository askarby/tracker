package dk.innotech.user.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtFactory {
    private String secret;
    private long jwtExpirationInMs;

    @Value("${security.jwt.secret}")
    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Value("${security.jwt.expirationInMs}")
    public void setJwtExpirationInMs(long jwtExpirationInMs) {
        this.jwtExpirationInMs = jwtExpirationInMs;
    }

    public String generateToken(UserDetailsFromDTO userDetails) {
        Map<String, Object> claims = new HashMap<>();
        var user = userDetails.getDTO();
        claims.put(JwtTokenClaims.ROLES_KEY, user.getRolesWithExpiration());
        claims.put(JwtTokenClaims.USER_ID_KEY, userDetails.getUserId());
        claims.put(JwtTokenClaims.ACCOUNT_EXPIRES_KEY, user.getAccountExpiresOn());
        claims.put(JwtTokenClaims.ACCOUNT_LOCKED_KEY, user.isLocked());
        claims.put(JwtTokenClaims.CREDENTIALS_EXPIRES_KEY, user.getCredentialsExpiresOn());
        return serializeToken(claims, userDetails.getUsername());
    }

    private String serializeToken(Map<String, Object> claims, String subject) {
        var now = System.currentTimeMillis();
        var issuedAt = new Date(now);
        var expiresAt = new Date(now + jwtExpirationInMs);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(issuedAt)
                .setExpiration(expiresAt)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

}