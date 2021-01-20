package dk.innotech.user.models;

import dk.innotech.user.mocks.DummyBindingBean;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("Unit test for ErrorResponse")
public class ErrorResponseTest {

    private ErrorResponse response;

    @BeforeEach
    public void createResponse() {
        response = new ErrorResponse();
    }

    @Test
    @DisplayName("should have initial state with sane defaults")
    public void initialState() {
        assertThat(response.getTimestamp()).isBeforeOrEqualTo(new Date());
        assertThat(response.getStatus()).isEqualTo(500);
        assertThat(response.getErrors()).asList().isEmpty();
    }

    @Nested
    @DisplayName("adding errors (through addError-method)")
    class AddingErrors {
        @Test
        @DisplayName("should add string 'as is'")
        public void addString() {
            var error = "Ka-boom";
            var response = ErrorResponse.fromError(error);
            assertThat(response.getErrors()).asList().containsExactly(error);

        }

        @Test
        @DisplayName("should add AuthenticationException with cause")
        public void addAuthenticationExceptionWithCause() {
            var cause = new RuntimeException("I'm the cause");
            var error = new BadCredentialsException("Fault", cause);
            var response = ErrorResponse.fromError(error);
            var expected = String.format("%s - %s", cause.getMessage(), error.getMessage());
            assertThat(response.getErrors()).asList().containsExactly(expected);
        }

        @Test
        @DisplayName("should add AuthenticationException without cause")
        public void addAuthenticationExceptionWithoutCause() {
            var error = new BadCredentialsException("Fault");
            var response = ErrorResponse.fromError(error);
            assertThat(response.getErrors()).asList().containsExactly(error.getMessage());
        }

        @Test
        @DisplayName("should add MethodArgumentNotValidException")
        public void addMethodArgumentNotValidException() throws NoSuchMethodException {
            // Given
            var method = DummyBindingBean.class.getMethod("getA");
            var methodParameter = new MethodParameter(method, -1);
            var bean = new DummyBindingBean();

            var bindingResult = new BeanPropertyBindingResult(bean, "DummyBean");
            var messages = List.of("A is not set", "A is a bad name for a field");
            messages.forEach(message -> bindingResult.addError(new FieldError("DummyBean", "a", message)));

            var error = new MethodArgumentNotValidException(methodParameter, bindingResult);

            var response = ErrorResponse.fromError(error);
            assertThat(response.getErrors()).asList().containsAll(messages);
        }

        @Test
        @DisplayName("should add Throwable (message support for 'any' throwable)")
        public void addThrowableMessage() {
            var error = new RuntimeException("Fault");
            var response = ErrorResponse.fromError(error);
            assertThat(response.getErrors()).asList().containsExactly(error.getMessage());
        }
    }

    @Test
    @DisplayName("should be able to create map-representation")
    public void createsMap() {
        var error = "ka-boom!";
        response.addError(error);

        assertThat(response.asMap())
                .extractingByKey("timestamp", as(InstanceOfAssertFactories.DATE))
                .isEqualTo(response.getTimestamp());

        assertThat(response.asMap())
                .extractingByKey("status")
                .isEqualTo(response.getStatus());

        assertThat(response.asMap())
                .extractingByKey("errors")
                .asList()
                .containsExactly(error);
    }

    @Test
    @DisplayName("should be able to create ResponseEntity-representation")
    public void createsResponseEntity() {
        var headers = new HttpHeaders();
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        var responseEntity = response.asResponseEntity(headers, status);

        assertThat(responseEntity).isInstanceOf(ResponseEntity.class);
        assertThat(responseEntity.getBody())
                .extracting("timestamp", "status", "errors")
                .containsExactly(response.getTimestamp(), response.getStatus(), response.getErrors());
        assertThat(responseEntity)
                .hasFieldOrPropertyWithValue("headers", headers);
        assertThat(responseEntity)
                .hasFieldOrPropertyWithValue("status", status);
    }

    @Test
    @DisplayName("should be able to create instance from error object")
    public void createFromError() {
        var response = ErrorResponse.fromError("ka-boom!");
        assertThat(response).isNotNull();
        assertThat(response).isInstanceOf(ErrorResponse.class);
    }
}
