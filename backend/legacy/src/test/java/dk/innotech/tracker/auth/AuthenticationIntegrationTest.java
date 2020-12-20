package dk.innotech.tracker.auth;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.SignedJWT;
import io.micronaut.http.*;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@MicronautTest
@Tag("IntegrationTest")
public class AuthenticationIntegrationTest {
    private String endpoint = "/greeting/secured-hello";

    @Inject
    @Client("/")
    RxHttpClient client;

    @Test
    @DisplayName("Approved call to secured endpoint (with valid credentials)")
    public void validJwtCredentials() {
        var tracking = new AuthenticationTest(client, String.class);
        assertThatCode(() -> tracking.login("sherlock", "password")).doesNotThrowAnyException();

        assertThat(tracking.loginResponse).hasFieldOrPropertyWithValue("status", HttpStatus.OK);
        assertThat(tracking.loginResponse.body()).hasFieldOrPropertyWithValue("username", "sherlock");
        assertThat(tracking.loginResponse.body()).hasFieldOrProperty("accessToken");
        assertThat(tracking.accessToken).isInstanceOf(SignedJWT.class);

        assertThatCode(() -> tracking.get(endpoint)).doesNotThrowAnyException();

        assertThat(tracking.response).hasFieldOrPropertyWithValue("status", HttpStatus.OK);
        assertThat(tracking.response.body()).isEqualTo("sherlock");
    }

    @Test
    @DisplayName("Declines call to secured endpoint (with invalid credentials)")
    public void invalidJwtCredentials() {
        MutableHttpRequest request = HttpRequest.GET(endpoint);
        request.header(HttpHeaders.AUTHORIZATION, "Bearer absoluteNonsenseBearerTokenWillRespondWithUnauthorized");
        assertThatThrownBy(() -> client.toBlocking().exchange(request))
                .isInstanceOf(HttpClientResponseException.class)
                .hasFieldOrPropertyWithValue("status", HttpStatus.UNAUTHORIZED);

    }

    @Test
    @DisplayName("Declines call to secured endpoint (with missing credentials)")
    public void missingJwtCredentials() {
        assertThatThrownBy(() -> client.toBlocking().exchange(HttpRequest.GET(endpoint)))
                .isInstanceOf(HttpClientResponseException.class)
                .hasFieldOrPropertyWithValue("status", HttpStatus.UNAUTHORIZED);
    }

    @Nested
    @MicronautTest
    @DisplayName("Using Client")
    class WithAppClient {
        @Inject
        GreetingTestClient client;

        @Test
        @DisplayName("Approved call to secured endpoint (with valid credentials)")
        public void validJwtCredentials() throws ParseException {
            var credentials = new UsernamePasswordCredentials("sherlock", "password");
            BearerAccessRefreshToken response = client.login(credentials);
            var accessToken = response.getAccessToken();

            assertThat(response).isNotNull();
            assertThat(accessToken).isNotNull();
            assertThat(JWTParser.parse(accessToken)).isInstanceOf(SignedJWT.class);

            var message = client.securedGreeting("Bearer " + accessToken);
            assertThat(message).isEqualTo("sherlock");
        }
    }
}

class AuthenticationTest<T> {
    private final RxHttpClient client;
    private final Class<T> responseType;

    public HttpResponse<BearerAccessRefreshToken> loginResponse;
    public JWT accessToken;
    public HttpResponse<T> response;

    public AuthenticationTest(RxHttpClient client, Class<T> responseType) {
        this.client = client;
        this.responseType = responseType;
    }

    public void login(String username, String password) throws Exception {
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
        HttpRequest request = HttpRequest.POST("/login", credentials);
        loginResponse = client.toBlocking().exchange(request, BearerAccessRefreshToken.class);
        accessToken = JWTParser.parse(loginResponse.body().getAccessToken());
    }

    public HttpResponse<T> get(String endpoint) {
        MutableHttpRequest request = HttpRequest.GET(endpoint);
        if (accessToken != null) {
            request.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken.serialize());
        }
        response = client.toBlocking().exchange(request, responseType);
        return response;
    }
}