package dk.innotech.user.controllers.authentication;

import dk.innotech.user.security.CustomUserDetailsService;
import dk.innotech.user.security.JwtFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Validated
@Api(value = "Endpoint for authentication related operations", tags = "Authentication")
public class AuthenticationController {
    private AuthenticationManager authenticationManager;
    private CustomUserDetailsService userDetailsService;
    private JwtFactory tokenFactory;

    public AuthenticationController(AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService, JwtFactory tokenFactory) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.tokenFactory = tokenFactory;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation("Creates a JWT token for authenticating other requests (basically stateless login)")
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@Valid @RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            var token = new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword());
            authenticationManager.authenticate(token);
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
        final var userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final var token = tokenFactory.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

}
