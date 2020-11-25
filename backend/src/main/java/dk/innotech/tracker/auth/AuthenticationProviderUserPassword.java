package dk.innotech.tracker.auth;

import edu.umd.cs.findbugs.annotations.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationException;
import io.micronaut.security.authentication.AuthenticationFailed;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.authentication.UserDetails;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import org.reactivestreams.Publisher;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.HashMap;

@Singleton
public class AuthenticationProviderUserPassword implements AuthenticationProvider {

    @Override
    public Publisher<AuthenticationResponse> authenticate(@Nullable HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
        return Flowable.create(emitter -> {
            if (authenticationRequest.getIdentity().equals("sherlock") &&
                    authenticationRequest.getSecret().equals("password")) {
                var roles = new ArrayList<String>();
                roles.add("ROLE_ADMIN");


                var etc = new HashMap<String, Object>();
                etc.put("idleTimeoutMinutes", 5);
                etc.put("fullName", "Mr. Sherlock Holmes");

                var attributes = new HashMap<String, Object>();
                attributes.put("etc", etc);


                emitter.onNext(new UserDetails((String) authenticationRequest.getIdentity(), roles, attributes));
                emitter.onComplete();
            } else {
                emitter.onError(new AuthenticationException(new AuthenticationFailed()));
            }
        }, BackpressureStrategy.ERROR);
    }
}