package dk.innotech.user.security;

import dk.innotech.user.dtos.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("Unit test for UserDetailsFromDTO")
class UserDetailsFromDTOTest {
    private final long ONE_HOUR_MILLISECONDS = 60 * 60 * 1000;

    private UserDetailsFromDTO dto;
    private UserDTO user;
    private String password;

    @BeforeEach
    public void createDTO() {
        user = mock(UserDTO.class);
        password = "password";
        dto = new UserDetailsFromDTO(user, password);
    }

    @Test
    @DisplayName("should be possible to retrieve DTO itself")
    public void retrieveDTO() {
        assertThat(dto.getDTO()).isEqualTo(user);
    }

    @Test
    @DisplayName("should be possible to retrieve password")
    public void retrievePassword() {
        assertThat(dto.getPassword()).isEqualTo(password);
    }

    @Test
    @DisplayName("should be possible to retrieve username")
    public void retrieveUsername() {
        var username = "username";
        when(user.getUsername()).thenReturn(username);
        assertThat(dto.getUsername()).isEqualTo(username);
    }

    @Nested
    @DisplayName("account expiration")
    class AccountExpiration {
        @Test
        @DisplayName("should be possible to determine if account has expired")
        public void determineExpiration() {
            var hasExpiredOn = System.currentTimeMillis() - ONE_HOUR_MILLISECONDS;
            when(user.getAccountExpiresOn()).thenReturn(hasExpiredOn);
            assertThat(dto.isAccountNonExpired()).isFalse();
        }

        @Test
        @DisplayName("should be possible to determine if account has not expired")
        public void determineNonExpiration() {
            var expiresOn = System.currentTimeMillis() + ONE_HOUR_MILLISECONDS;
            when(user.getAccountExpiresOn()).thenReturn(expiresOn);
            assertThat(dto.isAccountNonExpired()).isTrue();
        }

        @Test
        @DisplayName("should never expire an account with system role")
        public void accountWithSystemRole() {
            var roles = new HashMap<String, Long>();
            roles.put(DefaultRole.SYSTEM.asText(), System.currentTimeMillis() + ONE_HOUR_MILLISECONDS);

            var hasExpiredOn = System.currentTimeMillis() - ONE_HOUR_MILLISECONDS;
            when(user.getAccountExpiresOn()).thenReturn(hasExpiredOn);
            when(user.getRolesWithExpiration()).thenReturn(roles);

            assertThat(dto.isAccountNonExpired()).isTrue();
        }
    }

    @Nested
    @DisplayName("account locked")
    class AccountLocked {

        @Test
        @DisplayName("should be possible to determine if account has been locked")
        public void determineIfLocked() {
            when(user.isLocked()).thenReturn(true);
            assertThat(dto.isAccountNonLocked()).isFalse();
        }

        @Test
        @DisplayName("should be possible to determine if account has not been locked")
        public void determineIfNonLocked() {
            when(user.isLocked()).thenReturn(false);
            assertThat(dto.isAccountNonLocked()).isTrue();
        }

        @Test
        @DisplayName("should never lock an account with system role")
        public void accountWithSystemRole() {
            var roles = new HashMap<String, Long>();
            roles.put(DefaultRole.SYSTEM.asText(), System.currentTimeMillis() + ONE_HOUR_MILLISECONDS);

            when(user.isLocked()).thenReturn(true);
            when(user.getRolesWithExpiration()).thenReturn(roles);

            assertThat(dto.isAccountNonLocked()).isTrue();
        }
    }

    @Nested
    @DisplayName("credentials expiration")
    class CredentialsExpiration {
        @Test
        @DisplayName("should be possible to determine if credentials has expired")
        public void determineExpiration() {
            var hasExpiredOn = System.currentTimeMillis() - ONE_HOUR_MILLISECONDS;
            when(user.getCredentialsExpiresOn()).thenReturn(hasExpiredOn);
            assertThat(dto.isCredentialsNonExpired()).isFalse();
        }

        @Test
        @DisplayName("should be possible to determine if credentials has not expired")
        public void determineNonExpiration() {
            var expiresOn = System.currentTimeMillis() + ONE_HOUR_MILLISECONDS;
            when(user.getCredentialsExpiresOn()).thenReturn(expiresOn);
            assertThat(dto.isCredentialsNonExpired()).isTrue();
        }

        @Test
        @DisplayName("should never expire credentials for an account with system role")
        public void accountWithSystemRole() {
            var roles = new HashMap<String, Long>();
            roles.put(DefaultRole.SYSTEM.asText(), System.currentTimeMillis() + ONE_HOUR_MILLISECONDS);

            var hasExpiredOn = System.currentTimeMillis() - ONE_HOUR_MILLISECONDS;
            when(user.getCredentialsExpiresOn()).thenReturn(hasExpiredOn);
            when(user.getRolesWithExpiration()).thenReturn(roles);

            assertThat(dto.isCredentialsNonExpired()).isTrue();
        }
    }

    @Test
    @DisplayName("should always be enabled")
    public void determineIfEnabled() {
        assertThat(dto.isEnabled()).isTrue();
    }

    @Test
    @DisplayName("should be possible to retrieve user's id")
    public void retrieveId() {
        var id = 42L;
        when(user.getId()).thenReturn(id);
        assertThat(dto.getUserId()).isEqualTo(id);
    }
}