package dk.innotech.user.dtos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static dk.innotech.user.assertions.Assertions.assertThat;
import static dk.innotech.user.assertions.Assertions.assertThatBean;

@DisplayName("Unit test for UserDTO")
public class UserDTOTest {
    private UserDTO dto;
    private ObjectMapper mapper;

    @BeforeEach
    public void createDTO() {
        var created = AuditDetailsDTO.builder()
                .userId(12398731283123L)
                .timestamp(System.currentTimeMillis() - 3_600_000)
                .build();
        var updated = AuditDetailsDTO.builder()
                .userId(239474328234L)
                .timestamp(System.currentTimeMillis())
                .build();
        var audit = AuditDTO.builder()
                .created(created)
                .updated(updated)
                .build();

        dto = UserDTO.builder()
                .id(42L)
                .audit(audit)
                .username("johndoe")
                .fullName("John Doe, Jr.")
                .roleWithExpiration("ROLE_USER", System.currentTimeMillis())
                .roleWithExpiration("ROLE_ADMIN", System.currentTimeMillis())
                .build();
        mapper = new ObjectMapper();
    }

    @Test
    @DisplayName("should have properties with getters and setters")
    public void properties() {
        assertThatBean(UserDTO.class).hasBeanProperties("id", "audit", "username", "fullName", "rolesWithExpiration");
    }

    @Test
    @DisplayName("should be documented with Swagger")
    public void documented() {
        assertThat(UserDTO.class).hasApiModel().withDescription();

        assertThat(UserDTO.class).hasApiModelField("id")
                .withNotes()
                .withExample()
                .thatIsRequired();
        assertThat(UserDTO.class).hasApiModelField("audit")
                .withNotes()
                .thatIsRequired();
        assertThat(UserDTO.class).hasApiModelField("username")
                .withNotes()
                .withExample()
                .thatIsRequired();
        assertThat(UserDTO.class).hasApiModelField("fullName")
                .withNotes()
                .withExample()
                .thatIsRequired();
        assertThat(UserDTO.class).hasApiModelField("rolesWithExpiration")
                .withNotes()
                .withExample()
                .thatIsRequired();
    }

    @Test
    @DisplayName("should serialize and deserialize (to and from JSON) as expected")
    public void jsonSerialization() throws JsonProcessingException {
        var asJson = mapper.writeValueAsString(dto);
        var asObject = mapper.readValue(asJson, UserDTO.class);
        org.assertj.core.api.Assertions.assertThat(asObject).isEqualTo(dto);
    }
}
