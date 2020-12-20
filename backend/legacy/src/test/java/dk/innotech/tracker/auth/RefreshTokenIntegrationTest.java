package dk.innotech.tracker.auth;

import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.SignedJWT;
import io.micronaut.context.ApplicationContext;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.security.authentication.UserDetails;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.generator.RefreshTokenGenerator;
import io.micronaut.security.token.jwt.endpoints.TokenRefreshRequest;
import io.micronaut.security.token.jwt.render.AccessRefreshToken;
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.*;

import javax.inject.Inject;
import java.text.ParseException;
import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@MicronautTest
public class RefreshTokenIntegrationTest {
    private String refreshEndpoint = "/oauth/access_token";

    @Inject
    @Client("/")
    RxHttpClient client;

    @Inject
    RefreshTokenRepository refreshTokenRepository;

    @AfterEach
    public void cleanUp() {
        refreshTokenRepository.deleteAll();
    }

    @Test
    @DisplayName("Refresh token is present on auth response")
    public void hasRefreshToken() throws ParseException {
        var credentials = new UsernamePasswordCredentials("sherlock", "password");
        HttpRequest request = HttpRequest.POST("/login", credentials);
        HttpResponse<BearerAccessRefreshToken> response = client.toBlocking().exchange(request, BearerAccessRefreshToken.class);
        assertThat(response).isNotNull();

        var token = (BearerAccessRefreshToken) response.body();

        var accessToken = token.getAccessToken();
        assertThat(accessToken).isNotNull();
        assertThat(JWTParser.parse(accessToken)).isInstanceOf(SignedJWT.class);

        var refreshToken = token.getRefreshToken();
        assertThat(refreshToken).isNotNull();
    }

    @Test
    @DisplayName("Refresh token gets successfully renewed")
    public void successfulRenew() throws InterruptedException {
        var previousTokenCount = refreshTokenRepository.count();

        var credentials = new UsernamePasswordCredentials("sherlock", "password");
        var request = HttpRequest.POST("/login", credentials);
        var response = client.toBlocking().retrieve(request, BearerAccessRefreshToken.class);

        // Wait for database to have persisted new refresh token
        while (previousTokenCount + 1 != refreshTokenRepository.count()) {
            Thread.sleep(50);
        }

        assertThat(response).hasFieldOrProperty("accessToken");
        assertThat(response).hasFieldOrProperty("refreshToken");

        // Wait for iat to change
        Thread.sleep(1_000);

        var refreshResponse = client.toBlocking().retrieve(HttpRequest.POST("/oauth/access_token",
                new TokenRefreshRequest(response.getRefreshToken())), AccessRefreshToken.class);

        assertThat(refreshResponse).hasFieldOrProperty("accessToken");
        assertThat(refreshResponse.getAccessToken()).isNotEqualTo(response.getAccessToken());
    }

    @Test
    @DisplayName("Does not issue new token, based on invalid refresh token")
    public void renewInvalidRefreshToken() {
        var unsignedRefreshedToken = "foo";

        var bodyArgument = Argument.of(BearerAccessRefreshToken.class);
        var errorArgument = Argument.of(Map.class);

        var postRequest = HttpRequest.POST(refreshEndpoint, new TokenRefreshRequest(unsignedRefreshedToken));
        var thrown = catchThrowableOfType(() -> client.toBlocking().exchange(postRequest, bodyArgument, errorArgument), HttpClientResponseException.class);
        assertThat(thrown).hasFieldOrPropertyWithValue("status", HttpStatus.BAD_REQUEST);

        var optionalMapping = thrown.getResponse().getBody(Map.class);
        assertThat(optionalMapping).isPresent();

        var mapping = optionalMapping.get();
        assertThat(mapping.get("error")).isEqualTo("invalid_grant");
        assertThat(mapping.get("error_description")).isEqualTo("Refresh token is invalid");
    }
}

class OnEmbeddedServer {
    private EmbeddedServer embeddedServer;
    private HttpClient client;
    private RefreshTokenGenerator refreshTokenGenerator;
    private RefreshTokenRepository refreshTokenRepository;

    @BeforeEach
    public void setup() {
        embeddedServer = ApplicationContext.run(EmbeddedServer.class, Collections.emptyMap());

        var applicationContext = embeddedServer.getApplicationContext();
        client = applicationContext.createBean(HttpClient.class, embeddedServer.getURL());
        refreshTokenGenerator = applicationContext.getBean(RefreshTokenGenerator.class);
        refreshTokenRepository = applicationContext.getBean(RefreshTokenRepository.class);
    }

    @AfterEach
    public void cleanUp() {
        refreshTokenRepository.deleteAll();
        embeddedServer.close();
    }

    @Test
    @DisplayName("Does not renew token, based on revoked refresh token")
    public void renewInvokedRefreshToken() {
        UserDetails user = new UserDetails("sherlock", Collections.emptyList());

        var refreshToken = refreshTokenGenerator.createKey(user);
        var refreshTokenOptional = refreshTokenGenerator.generate(user, refreshToken);

        assertThat(refreshTokenOptional).isPresent();

        var signedRefreshToken = refreshTokenOptional.get();
        var previousTokenCount = refreshTokenRepository.count();
        refreshTokenRepository.save(user.getUsername(), refreshToken, Boolean.TRUE);

        assertThat(refreshTokenRepository.count()).isEqualTo(previousTokenCount + 1);

        Argument<BearerAccessRefreshToken> bodyArgument = Argument.of(BearerAccessRefreshToken.class);
        Argument<Map> errorArgument = Argument.of(Map.class);

        var thrown = catchThrowableOfType(() -> client.toBlocking().exchange(HttpRequest.POST("/oauth/access_token", new TokenRefreshRequest(signedRefreshToken)), bodyArgument, errorArgument), HttpClientResponseException.class);
        assertThat(thrown).hasFieldOrPropertyWithValue("status", HttpStatus.BAD_REQUEST);

        var optionalMapping = thrown.getResponse().getBody(Map.class);
        assertThat(optionalMapping).isPresent();

        var mapping = optionalMapping.get();
        assertThat(mapping.get("error")).isEqualTo("invalid_grant");
        assertThat(mapping.get("error_description")).isEqualTo("refresh token revoked");
    }
}
