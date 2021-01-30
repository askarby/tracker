package dk.innotech.user.security;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Collections.emptyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit test of CustomJwtAuthenticationFilter")
class CustomJwtAuthenticationFilterTest {

    @InjectMocks
    private CustomJwtAuthenticationFilter filter;

    @Mock
    private JwtParser tokenUtil;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain chain;

    @BeforeEach
    public void setupSecurityContextHolder() {
        SecurityContextHolder.setContext(securityContext);
    }

    @AfterEach
    public void clearSecurityContextHolder() {
        SecurityContextHolder.createEmptyContext();
    }

    @Nested
    @DisplayName("applicable filter")
    class Applicable {

        @BeforeEach
        public void configure() {
            var fakeToken = "Bearer <fake token>";
            when(request.getRequestURI()).thenReturn("/bob");
            when(request.getHeader("Authorization")).thenReturn(fakeToken);

            when(tokenUtil.validateToken(anyString())).thenReturn(true);

            var userDetails = mock(UserDetailsFromDTO.class);
            when(userDetails.getAuthorities()).thenReturn(emptyList());
            when(tokenUtil.getUserDetails(anyString())).thenReturn(userDetails);
        }

        @Test
        @DisplayName("should apply valid authentication token to SecurityContext, when request has valid token")
        public void withValidToken() throws ServletException, IOException {
            filter.doFilter(request, response, chain);
            verify(securityContext).setAuthentication(any(UsernamePasswordAuthenticationToken.class));
        }

        @Test
        @DisplayName("should invoke filter chain")
        public void invokeChain() throws IOException, ServletException {
            filter.doFilter(request, response, chain);
            verify(chain).doFilter(request, response);
        }
    }

    @Nested
    @DisplayName("non-applicable filter")
    class NonApplicable {
        @Test
        @DisplayName("should ignore requests to '/login'")
        public void requestToLoginUrl() throws ServletException, IOException {
            when(request.getRequestURI()).thenReturn("/login");

            filter.doFilter(request, response, chain);

            verify(request, never()).getHeader(anyString());
            verify(tokenUtil, never()).getUserDetails(anyString());
            verify(securityContext, never()).setAuthentication(any());
        }

        @Test
        @DisplayName("should ignore when request has no token")
        public void noToken() throws ServletException, IOException {
            when(request.getRequestURI()).thenReturn("/bob");

            filter.doFilter(request, response, chain);

            verify(securityContext, never()).setAuthentication(any());
        }

        @Test
        @DisplayName("should ignore when request has invalid token")
        public void invalid() throws ServletException, IOException {
            var fakeToken = "JWT <fake token>";
            when(request.getRequestURI()).thenReturn("/bob");
            when(request.getHeader("Authorization")).thenReturn(fakeToken);

            filter.doFilter(request, response, chain);

            verify(securityContext, never()).setAuthentication(any());
        }
    }
}