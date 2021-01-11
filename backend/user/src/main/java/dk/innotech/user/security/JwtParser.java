package dk.innotech.user.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtParser {
    private String secret;

    @Value("${security.jwt.secret}")
    public void setSecret(String secret) {
        this.secret = secret;
    }

    public boolean validateToken(String authToken) {
        try {
            parseClaims(authToken);
            return true;
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", ex);
        } catch (ExpiredJwtException ex) {
            throw new CredentialsExpiredException("Token has Expired", ex);
        }
    }

    public String getUsernameFromToken(String token) {
        Claims claims = parseClaims(token).getBody();

        return claims.getSubject();
    }

    public List<GrantedAuthority> getRolesFromToken(String authToken) {
        Claims claims = parseClaims(authToken).getBody();
        List<String> rolesFromClaims = claims.get("roles", List.class);
        return rolesFromClaims.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private Jws<Claims> parseClaims(String authToken) throws RuntimeException {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
    }
}
