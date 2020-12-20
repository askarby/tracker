package dk.innotech.tracker.auth;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken;

@Client("/")
interface GreetingTestClient {

    @Post("/login")
    BearerAccessRefreshToken login(@Body UsernamePasswordCredentials credentials);

    @Consumes(MediaType.TEXT_PLAIN)
    @Get("/greeting/secured-hello")
    String securedGreeting(@Header String authorization);
}