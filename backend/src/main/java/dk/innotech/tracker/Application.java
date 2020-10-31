package dk.innotech.tracker;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
        info = @Info(
                title = "InnoTech Solutions Tracker",
                version = "1.0",
                description = "Tracker API",
                license = @License(name = "Proprietary", url = "https://tracker.inno-tech.dk/license"),
                contact = @Contact(url = "https://tracker.inno-tech.dk", name = "Tracker support", email = "tracker@inno-tech.dk")
        )
)
public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}
