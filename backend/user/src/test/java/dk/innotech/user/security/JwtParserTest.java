package dk.innotech.user.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JwtParser.class)
@TestPropertySource(properties = {
        "security.jwt.secret=jwtsecret",
})
@DisplayName("Unit test of JwtParser")
class JwtParserTest {
    @Autowired
    private JwtParser parser;

    private String validToken;
    private String expiredToken;

    @BeforeEach
    public void readToken() throws IOException {
        var base = "src/test/resources/dk/innotech/user/security/";
        validToken = read(base + "valid-token.txt");
        expiredToken = read(base + "expired-token.txt");
    }

    private String read(String from) throws IOException {
        Path path = Paths.get(from);
        return Files.readAllLines(path).get(0).trim();
    }

    @Nested
    @DisplayName("with valid token")
    class ValidToken {
        @Test
        @DisplayName("should validate token")
        public void validateToken() {
            assertThat(parser.validateToken(validToken)).isTrue();
        }

        @Test
        @DisplayName("should retrieve user details from token")
        public void retrieveUserDetails() {
            assertThat(parser.getUserDetails(validToken)).isNotNull();
        }
    }

    @Nested
    @DisplayName("with invalid token")
    class InvalidToken {
        @Test
        @DisplayName("should throw BadCredentialsException when token is invalid")
        public void badToken() {
            assertThatExceptionOfType(BadCredentialsException.class)
                    .isThrownBy(() -> parser.validateToken("absolute garbage (unparseable as token)"));
        }

        @Test
        @DisplayName("should throw CredentialsExpiredException when token has expired")
        public void expiredToken() {
            assertThatExceptionOfType(CredentialsExpiredException.class)
                    .isThrownBy(() -> parser.validateToken(expiredToken));
        }
    }

    @Nested
    @DisplayName("injected properties")
    class InjectedProperties {
        @Test
        @DisplayName("should inject jwt token secret")
        public void injectTokenSecret() {
            var field = ReflectionUtils.findField(JwtParser.class, "secret");
            ReflectionUtils.makeAccessible(field);
            var secret = ReflectionUtils.getField(field, parser);
            assertThat(secret).isEqualTo("jwtsecret");
        }
    }
}