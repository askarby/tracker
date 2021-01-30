package dk.innotech.user.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Unit test of DefaultRole enum")
class DefaultRoleTest {
    private List<String> roles;

    @BeforeEach
    public void acquireRoles() {
        roles = Arrays.stream(DefaultRole.values())
                .map(DefaultRole::asText)
                .collect(Collectors.toList());
    }

    @Test
    @DisplayName("should contain default role for system user")
    public void systemUser() {
        assertThat(roles).contains("ROLE_SYSTEM");
    }

    @Test
    @DisplayName("should contain default role for 'plain' user")
    public void plainUser() {
        assertThat(roles).contains("ROLE_USER");
    }

    @Test
    @DisplayName("should contain default role for administrator")
    public void adminUser() {
        assertThat(roles).contains("ROLE_ADMIN");
    }
}