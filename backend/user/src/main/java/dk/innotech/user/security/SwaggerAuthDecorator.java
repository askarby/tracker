package dk.innotech.user.security;

import io.swagger.annotations.ApiOperation;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static j2html.TagCreator.*;

/**
 * Adds authentication / authorization information to Swagger API docs.<p/>
 *
 * Will apply:
 * <ul>
 *     <li>Authorizations (Padlock icon)</li>
 *     <li>Notes about required roles (adds to existing or creates new notes)</li>
 * </ul>
 *
 * ...based on information from Spring's @{@link Secured} annotation.
 */
@Component
@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER + 1)
public class SwaggerAuthDecorator implements OperationBuilderPlugin {
    private final SecurityReference jwtSecurityReference;

    public SwaggerAuthDecorator() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "Scopes do not apply to JWT");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;

        jwtSecurityReference = new SecurityReference("JWT", authorizationScopes);
    }

    @Override
    public void apply(final OperationContext context) {
        String notes = getExistingNotes(context);

        var roles = getRoles(context);
        if (!roles.isEmpty()) {
            // Add information about required roles to notes
            notes += "\n\n" + join(
                    b("🔑 Security"),
                    "Requires (one of) the following roles:",
                    ul(each(roles, role -> li(pre(role))))
            ).render();

            // Add authorization requirement (renders padlock)
            context.operationBuilder().authorizations(asList(jwtSecurityReference));
        }

        context.operationBuilder().notes(notes);
    }

    private String getExistingNotes(final OperationContext context) {
        return context.findAnnotation(ApiOperation.class)
                .map(ApiOperation::notes)
                .orElse("");
    }

    private List<String> getRoles(final OperationContext context) {
        return context.findAnnotation(Secured.class)
                .or(() -> context.findControllerAnnotation(Secured.class))
                .map(Secured::value)
                .map(Arrays::asList)
                .orElse(emptyList());
    }

    @Override
    public boolean supports(final DocumentationType delimiter) {
        return SwaggerPluginSupport.pluginDoesApply(delimiter);
    }
}
