package dk.innotech.user.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Service
public class JwtFactory {
    private final String CLAIMS_ROLES_KEY = "roles";

    private String secret;
    private int jwtExpirationInMs;

    @Value("${security.jwt.secret}")
    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Value("${security.jwt.expirationInMs}")
    public void setJwtExpirationInMs(int jwtExpirationInMs) {
        this.jwtExpirationInMs = jwtExpirationInMs;
    }

    // generate token for user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();
        claims.put(CLAIMS_ROLES_KEY, roles.stream().map(GrantedAuthority::getAuthority).collect(toList()));
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
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