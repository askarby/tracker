package dk.innotech.user.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;

import static java.util.Collections.emptyList;

@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfiguration {

    @Bean
    public Docket api() {
        var docket = new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("dk.innotech"))
                .paths(PathSelectors.any())
                .build();

        return docket
                .apiInfo(apiInfo())
                .securitySchemes(Arrays.asList(apiKey()))
                .useDefaultResponseMessages(false);
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "User (and Authentication) API",
                "Contains functionality for creating, assigning roles and authenticating users",
                "API TOS",
                "Terms of service",
                new Contact("InnoTech Solutions ApS", "www.inno-tech.dk", "contact@inno-tech.dk"),
                "License of API", "API license URL", emptyList());
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }
}