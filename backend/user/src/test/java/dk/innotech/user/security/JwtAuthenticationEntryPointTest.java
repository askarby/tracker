package dk.innotech.user.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit test of JwtAuthenticationEntryPoint (exception mapper for failed auth)")
class JwtAuthenticationEntryPointTest {

    @InjectMocks
    private JwtAuthenticationEntryPoint entryPoint;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private ServletOutputStream outputStream;

    private AuthenticationException exception;

    @BeforeEach
    public void prepare() throws IOException {
        exception = new BadCredentialsException("Something went horribly wrong!");

        when(response.getOutputStream()).thenReturn(outputStream);
    }

    @Test
    @DisplayName("should set the HTTP status to 401")
    public void setStatus() throws IOException {
        entryPoint.commence(request, response, exception);
        verify(response).setStatus(eq(HttpServletResponse.SC_UNAUTHORIZED));
    }

    @Test
    @DisplayName("should set the content type to 'application/json'")
    public void setResponseType() throws IOException {
        entryPoint.commence(request, response, exception);
        verify(response).setContentType(eq(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    @DisplayName("should set write the body as json")
    public void setBody() throws IOException {
        entryPoint.commence(request, response, exception);
        verify(mapper).writeValueAsBytes(any());
        verify(outputStream).write(any());
    }
}