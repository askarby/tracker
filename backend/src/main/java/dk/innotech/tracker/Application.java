package dk.innotech.tracker;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                title = "InnoTech Solutions Tracker",
                version = "1.0",
                description = "Tracker API",
                license = @License(name = "Proprietary", url = "https://tracker.inno-tech.dk/license"),
                contact = @Contact(url = "https://tracker.inno-tech.dk", name = "Tracker support", email = "tracker@inno-tech.dk")
        )
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}
