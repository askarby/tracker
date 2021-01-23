package dk.innotech.user.dtos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static dk.innotech.user.assertions.Assertions.assertThat;
import static dk.innotech.user.assertions.Assertions.assertThatBean;

@DisplayName("Unit test for AuditDTO")
public class AuditDTOTest {
    private AuditDTO dto;
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
        dto = AuditDTO.builder()
                .created(created)
                .updated(updated)
                .build();
        mapper = new ObjectMapper();
    }

    @Test
    @DisplayName("should have properties with getters and setters")
    public void properties() {
        assertThatBean(AuditDTO.class).hasBeanProperties("created", "updated");
    }

    @Test
    @DisplayName("should be documented with Swagger")
    public void documented() {
        assertThat(AuditDTO.class).hasApiModel().withDescription();

        assertThat(AuditDTO.class).hasApiModelField("created").withNotes();
        assertThat(AuditDTO.class).hasApiModelField("updated").withNotes();
    }

    @Test
    @DisplayName("should serialize and deserialize (to and from JSON) as expected")
    public void jsonSerialization() throws JsonProcessingException {
        var asJson = mapper.writeValueAsString(dto);
        var asObject = mapper.readValue(asJson, AuditDTO.class);
        org.assertj.core.api.Assertions.assertThat(asObject).isEqualTo(dto);
    }
}
