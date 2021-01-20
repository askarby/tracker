package dk.innotech.user.controllers;

import dk.innotech.user.mocks.DummyBindingBean;
import dk.innotech.user.services.AlreadyExistsException;
import dk.innotech.user.services.NotExistsException;
import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit test of CustomExceptionHandler")
public class CustomExceptionHandlerTest {
    private CustomExceptionHandler handler;

    @BeforeEach
    public void createHandler() {
        handler = new CustomExceptionHandler();
    }

    @Nested
    @DisplayName("handle validation errors")
    class ValidationErrors {

        @Mock
        private WebRequest request;

        @Test
        @DisplayName("should return ResponseEntity with headers and status")
        public void properReturnEntity() throws NoSuchMethodException {
            // Given
            var method = DummyBindingBean.class.getMethod("getA");
            var methodParameter = new MethodParameter(method, -1);
            var bean = new DummyBindingBean();

            var bindingResult = new BeanPropertyBindingResult(bean, "DummyBean");
            bindingResult.addError(new ObjectError("DummyBean", "A is not set"));

            var exception = new MethodArgumentNotValidException(methodParameter, bindingResult);
            var headers = new HttpHeaders();
            headers.add("Some header", "Some value");

            // When
            var entity = handler.handleMethodArgumentNotValid(exception, headers, HttpStatus.BAD_REQUEST, request);

            // Then
            assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            assertThat(entity.getHeaders()).isEqualTo(headers);
            assertThat(entity.getBody()).isNotNull();
        }
    }

    @Nested
    @DisplayName("Handle application-specific exceptions")
    class AppSpecificErrors {
        @Test
        @DisplayName("should not pass on any headers, set status to conflict (409) when intercepting AlreadyExistsException")
        public void handleAlreadyExistsException() {
            // Given
            var message = "Something already exists, I'm gonna ka-boom!";
            var exception = new AlreadyExistsException(message);

            // When
            var entity = handler.handleException(exception, null);

            // Then
            assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
            assertThat(entity.getHeaders()).isEqualTo(HttpHeaders.EMPTY);
            assertThat(entity.getBody()).asString().contains(message);
        }

        @Test
        @DisplayName("should not pass on any headers, set status to conflict (409) when intercepting AlreadyExistsException")
        public void handleNotExistsException() {
            // Given
            var message = "Something does not exists, I'm gonna ka-boom!";
            var exception = new NotExistsException(message);

            // When
            var entity = handler.handleException(exception, null);

            // Then
            assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            assertThat(entity.getHeaders()).isEqualTo(HttpHeaders.EMPTY);
            assertThat(entity.getBody()).asString().contains(message);
        }
    }
}
