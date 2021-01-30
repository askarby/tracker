package dk.innotech.user.security;

import io.swagger.annotations.ApiOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.annotation.Secured;
import springfox.documentation.builders.OperationBuilder;
import springfox.documentation.spi.service.contexts.OperationContext;

import java.util.stream.Stream;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit test of SwaggerAuthDecorator")
class SwaggerAuthDecoratorTest {

    private SwaggerAuthDecorator decorator;

    @Mock
    private OperationContext context;

    @Mock
    private OperationBuilder builder;

    @BeforeEach
    public void prepare() {
        decorator = new SwaggerAuthDecorator();

        when(context.operationBuilder()).thenReturn(builder);
    }

    @Nested
    @DisplayName("without notes")
    class Apply {
        @Test
        @DisplayName("should apply mark-up when class is annotated with @Secured")
        public void applyWhenClassHasSecuredAnnotation() {
            var operation = mockApiOperationWithNotes(null);
            var secured = mockSecuredWithRoles("USER", "SUPERUSER");

            when(context.findAnnotation(eq(ApiOperation.class))).thenReturn(of(operation));
            when(context.findControllerAnnotation(eq(Secured.class))).thenReturn(of(secured));

            decorator.apply(context);

            var expected = "\n\n<b>🔑 Security</b> Requires (one of) the following roles: <ul><li><pre>ROLE_USER</pre></li><li><pre>ROLE_SUPERUSER</pre></li></ul>";
            verify(builder).notes(eq(expected));
            verify(builder).authorizations(any());
        }

        @Test
        @DisplayName("should apply mark-up when method is annotated with @Secured")
        public void applyWhenMethodHasSecuredAnnotation() {
            var operation = mockApiOperationWithNotes(null);
            var secured = mockSecuredWithRoles("USER", "SUPERUSER");

            when(context.findAnnotation(eq(ApiOperation.class))).thenReturn(of(operation));
            when(context.findAnnotation(eq(Secured.class))).thenReturn(of(secured));

            decorator.apply(context);

            var expected = "\n\n<b>🔑 Security</b> Requires (one of) the following roles: <ul><li><pre>ROLE_USER</pre></li><li><pre>ROLE_SUPERUSER</pre></li></ul>";
            verify(builder).notes(eq(expected));
            verify(builder).authorizations(any());
        }
    }

    @Nested
    @DisplayName("with notes")
    class Enhance {
        @Test
        @DisplayName("should enhance existing mark-up when class is annotated with @Secured")
        public void enhanceWhenClassHasSecuredAnnotation() {
            var existingNotes = "Existing (should be prepended)";
            var operation = mockApiOperationWithNotes(existingNotes);
            var secured = mockSecuredWithRoles("USER", "SUPERUSER");

            when(context.findAnnotation(eq(ApiOperation.class))).thenReturn(of(operation));
            when(context.findControllerAnnotation(eq(Secured.class))).thenReturn(of(secured));

            decorator.apply(context);

            var expected = existingNotes +
                    "\n\n<b>🔑 Security</b> Requires (one of) the following roles: <ul><li><pre>ROLE_USER</pre></li><li><pre>ROLE_SUPERUSER</pre></li></ul>";
            verify(builder).notes(eq(expected));
            verify(builder).authorizations(any());
        }

        @Test
        @DisplayName("should enhance existing mark-up when method is annotated with @Secured")
        public void enhanceWhenMethodHasSecuredAnnotation() {
            var existingNotes = "Existing (should be prepended)";
            var operation = mockApiOperationWithNotes(existingNotes);
            var secured = mockSecuredWithRoles("USER", "SUPERUSER");

            when(context.findAnnotation(eq(ApiOperation.class))).thenReturn(of(operation));
            when(context.findAnnotation(eq(Secured.class))).thenReturn(of(secured));

            decorator.apply(context);

            var expected = existingNotes +
                    "\n\n<b>🔑 Security</b> Requires (one of) the following roles: <ul><li><pre>ROLE_USER</pre></li><li><pre>ROLE_SUPERUSER</pre></li></ul>";
            verify(builder).notes(eq(expected));
            verify(builder).authorizations(any());
        }
    }

    @Nested
    @DisplayName("ignoring")
    class Ignore {
        @Test
        @DisplayName("should ignore when class has no ApiOperation annotation")
        public void classWithoutApiOperation() {
            when(context.findAnnotation(eq(ApiOperation.class))).thenReturn(empty());

            decorator.apply(context);

            verify(builder, never()).authorizations(any());
        }

        @Test
        @DisplayName("should ignore when method has no ApiOperation annotation")
        public void methodWithoutApiOperation() {
            when(context.findAnnotation(eq(ApiOperation.class))).thenReturn(empty());

            decorator.apply(context);

            verify(builder, never()).authorizations(any());
        }

        @Test
        @DisplayName("should ignore when class and method has no Secured annotation")
        public void withoutSecured() {
            var operation = mockApiOperationWithNotes(null);
            when(context.findAnnotation(eq(ApiOperation.class))).thenReturn(of(operation));

            decorator.apply(context);

            verify(builder, never()).authorizations(any());
        }
    }

    private ApiOperation mockApiOperationWithNotes(String existingNotes) {
        var operation = mock(ApiOperation.class);
        when(operation.notes()).thenReturn(existingNotes);
        return operation;
    }

    private Secured mockSecuredWithRoles(String... roles) {
        var secured = mock(Secured.class);
        when(secured.value()).thenReturn(Stream.of(roles)
                .map((each) -> each.startsWith("ROLE_") ? each : "ROLE_" + each)
                .toArray(String[]::new)
        );
        return secured;
    }
}