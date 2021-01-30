package dk.innotech.user.security;

import dk.innotech.user.dtos.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.ReflectionUtils;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JwtFactory.class)
@TestPropertySource(properties = {
        "security.jwt.secret=jwtsecret",
        "security.jwt.expirationInMs=3600000"
})
@DisplayName("Unit test of JwtFactory")
class JwtFactoryTest {
    private static final long ONE_HOUR_IN_MILLISECONDS = 60 * 60 * 1000L;

    @Autowired
    private JwtFactory factory;

    @Test
    @DisplayName("should generate token from UserDetailsFromDTO")
    public void generateToken() {
        var user = UserDTO.builder()
                .id(42L)
                .username("johndoe")
                .roleWithExpiration("ROLE_USER", System.currentTimeMillis() + ONE_HOUR_IN_MILLISECONDS)
                .roleWithExpiration("ROLE_SUPERUSER", System.currentTimeMillis() + ONE_HOUR_IN_MILLISECONDS)
                .accountExpiresOn(123L)
                .locked(true)
                .credentialsExpiresOn(456L)
                .build();
        var userDetails = new UserDetailsFromDTO(user, "");

        var token = factory.generateToken(userDetails);
        var parsed = Jwts.parser().setSigningKey((String) valueOfField("secret")).parseClaimsJws(token);

        var body = parsed.getBody();
        assertThat(body.getSubject()).isEqualTo("johndoe");
        assertThat(body.get(JwtTokenClaims.ROLES_KEY, Map.class)).containsKeys("ROLE_USER", "ROLE_SUPERUSER");
        assertThat(body.get(JwtTokenClaims.USER_ID_KEY, Long.class)).isEqualTo(user.getId());
        assertThat(body.get(JwtTokenClaims.ACCOUNT_EXPIRES_KEY, Long.class)).isEqualTo(123L);
        assertThat(body.get(JwtTokenClaims.ACCOUNT_LOCKED_KEY, Boolean.class)).isTrue();
        assertThat(body.get(JwtTokenClaims.CREDENTIALS_EXPIRES_KEY, Long.class)).isEqualTo(456L);
    }

    @Nested
    @DisplayName("injected properties")
    class InjectedProperties {
        @Test
        @DisplayName("should inject jwt token secret")
        public void injectTokenSecret() {
            assertThat(valueOfField("secret")).isEqualTo("jwtsecret");
        }

        @Test
        @DisplayName("should inject jwt token expiration")
        public void injectTokenExpiration() {
            assertThat(valueOfField("jwtExpirationInMs")).isEqualTo(ONE_HOUR_IN_MILLISECONDS);
        }

    }

    private Object valueOfField(String fieldName) {
        var field = ReflectionUtils.findField(JwtFactory.class, fieldName);
        ReflectionUtils.makeAccessible(field);
        return ReflectionUtils.getField(field, factory);
    }
}