package dk.innotech.tracker.user.endpoint;

import dk.innotech.tracker.user.UserService;
import dk.innotech.tracker.user.dto.UserDTO;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.reactivex.Flowable;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.reactivestreams.Publisher;

@Controller("/users")
@Secured(SecurityRule.IS_ANONYMOUS)
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @Post()
    @Produces(single = true)
    @Operation(summary = "Create user",
            description = "Creates a new user"
    )
    @ApiResponse(
            content = @Content(mediaType = "application/json")
    )
    @ApiResponse(responseCode = "200", description = "Successfully created a new user")
    @Tag(name = "users")
    public Publisher<UserDTO> createUser(@Body Flowable<CreateUserRequest> request) {
        return request
                .flatMap(payload -> service.createUser(payload.getUsername(), payload.getPassword(), payload.getFullName()));
    }
}

