package dk.innotech.user.json;

import dk.innotech.user.dtos.AuditDetailsDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Unit test for EmptyAuditDetailsFilterTest")
public class EmptyAuditDetailsFilterTest {
    private EmptyAuditDetailsFilter filter;

    @BeforeEach
    public void createFilter() {
        filter = new EmptyAuditDetailsFilter();
    }

    @Test
    @DisplayName("should accept when having timestamp and user id")
    public void includeWhenValid() {
        var dto = AuditDetailsDTO.builder()
                .userId(42L)
                .timestamp(System.currentTimeMillis())
                .build();

        assertThat(filter.equals(dto)).isFalse();
    }

    @Test
    @DisplayName("should reject when having no timestamp")
    public void excludeWhenHavingNoTimestamp() {
        var dto = AuditDetailsDTO.builder()
                .userId(42L)
                .build();

        assertThat(filter.equals(dto)).isTrue();
    }

    @Test
    @DisplayName("should reject when having no user id")
    public void excludeWhenHavingNoUserId() {
        var dto = AuditDetailsDTO.builder()
                .timestamp(System.currentTimeMillis())
                .build();

        assertThat(filter.equals(dto)).isTrue();
    }

    @Test
    @DisplayName("should reject when value is null")
    public void excludeWhenNull() {
        assertThat(filter.equals(null)).isTrue();
    }

    @Test
    @DisplayName("should reject when value is not an AuditDetailsDTO instance")
    public void excludeWhenNotAuditDetailsDTO() {
        assertThat(filter.equals("Dummy string")).isTrue();
    }


}
