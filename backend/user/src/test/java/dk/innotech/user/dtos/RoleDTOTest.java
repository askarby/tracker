package dk.innotech.user.dtos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static dk.innotech.user.assertions.Assertions.assertThat;
import static dk.innotech.user.assertions.Assertions.assertThatBean;

@DisplayName("Unit test for RoleDTO")
public class RoleDTOTest {
    private RoleDTO dto;
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

        dto = RoleDTO.builder()
                .name("ROLE_TEST")
                .defaultRole(true)
                .audit(audit)
                .daTitle("Test-rolle")
                .enTitle("Test role")
                .build();
        mapper = new ObjectMapper();
    }

    @Test
    @DisplayName("should have properties with getters and setters")
    public void properties() {
        assertThatBean(RoleDTO.class).hasBeanProperties("name", "defaultRole", "audit", "daTitle", "enTitle");
    }

    @Test
    @DisplayName("should be documented with Swagger")
    public void documented() {
        assertThat(RoleDTO.class).hasApiModel().withDescription();


        assertThat(RoleDTO.class).hasApiModelField("name")
                .withNotes()
                .withExample()
                .thatIsRequired();
        assertThat(RoleDTO.class).hasApiModelField("defaultRole").withNotes();
        assertThat(RoleDTO.class).hasApiModelField("audit")
                .withNotes()
                .thatIsRequired();
        assertThat(RoleDTO.class).hasApiModelField("daTitle")
                .withNotes()
                .withExample()
                .thatIsRequired();
        assertThat(RoleDTO.class).hasApiModelField("enTitle")
                .withNotes()
                .withExample()
                .thatIsRequired();
    }

    @Test
    @DisplayName("should serialize and deserialize (to and from JSON) as expected")
    public void jsonSerialization() throws JsonProcessingException {
        var asJson = mapper.writeValueAsString(dto);
        var asObject = mapper.readValue(asJson, RoleDTO.class);
        org.assertj.core.api.Assertions.assertThat(asObject).isEqualTo(dto);
    }
}
