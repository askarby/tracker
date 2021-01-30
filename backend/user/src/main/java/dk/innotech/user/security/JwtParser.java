package dk.innotech.user.security;

import dk.innotech.user.dtos.UserDTO;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class JwtParser {
    private String secret;

    @Value("${security.jwt.secret}")
    public void setSecret(String secret) {
        this.secret = secret;
    }

    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            throw new BadCredentialsException("Invalid credentials", ex);
        } catch (ExpiredJwtException ex) {
            throw new CredentialsExpiredException("Token has Expired", ex);
        }
    }

    public UserDetailsFromDTO getUserDetails(String token) {
        var claims = parseClaims(token).getBody();
        var user = UserDTO.builder()
                .username(claims.getSubject())
                .id(claims.get(JwtTokenClaims.USER_ID_KEY, Long.class))
                .rolesWithExpiration(claims.get(JwtTokenClaims.ROLES_KEY, Map.class))
                .accountExpiresOn(claims.get(JwtTokenClaims.ACCOUNT_EXPIRES_KEY, Long.class))
                .locked(claims.get(JwtTokenClaims.ACCOUNT_LOCKED_KEY, Boolean.class))
                .credentialsExpiresOn(claims.get(JwtTokenClaims.CREDENTIALS_EXPIRES_KEY, Long.class))
                .build();
        return new UserDetailsFromDTO(user, "");
    }

    private Jws<Claims> parseClaims(String authToken) throws RuntimeException {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
    }
}
