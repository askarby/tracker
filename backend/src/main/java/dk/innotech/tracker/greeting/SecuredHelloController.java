package dk.innotech.tracker.greeting;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.security.Principal;

@Controller("/greeting")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class SecuredHelloController {

    @Get(uri = "/secured-hello")
    @Operation(summary = "Greets the logged in principle",
            description = "A friendly greeting is returned",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(
            content = @Content(mediaType = "application/json",
                    schema = @Schema(type = "string"))
    )
    @ApiResponse(responseCode = "404", description = "Person not found")
    @Tag(name = "greeting")
    public GreetingResponse index(Principal principal) {
        return new GreetingResponse("Hello", principal.getName());
    }
}