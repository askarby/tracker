package dk.innotech.tracker.auth;

import dk.innotech.tracker.user.UserService;
import edu.umd.cs.findbugs.annotations.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.*;
import io.reactivex.Flowable;
import org.reactivestreams.Publisher;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.HashMap;

@Singleton
public class AuthenticationProviderUserPassword implements AuthenticationProvider {
    private final UserService userService;

    public AuthenticationProviderUserPassword(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Publisher<AuthenticationResponse> authenticate(@Nullable HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
        String username = (String) authenticationRequest.getIdentity();
        String password = (String) authenticationRequest.getSecret();
        return userService.fromValidCredentials(username, password)
                .map((user) -> {
                    var roles = new ArrayList<String>();
                    roles.add("ROLE_ADMIN");


                    var etc = new HashMap<String, Object>();
                    etc.put("idleTimeoutMinutes", 5);
                    etc.put("fullName", user.getFullName());

                    var attributes = new HashMap<String, Object>();
                    attributes.put("etc", etc);

                    return new UserDetails(user.getUsername(), roles, attributes);
                })
                .cast(AuthenticationResponse.class)
                .onErrorResumeNext(t -> {
                    return Flowable.just(new AuthenticationFailed());
                });

    }
}