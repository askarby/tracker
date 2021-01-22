package dk.innotech.user.dtos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static dk.innotech.user.assertions.Assertions.assertThat;
import static dk.innotech.user.assertions.Assertions.assertThatBean;

@DisplayName("Unit test for AuditDetailsDTO")
public class AuditDetailsDTOTest {
    private AuditDetailsDTO dto;
    private ObjectMapper mapper;

    @BeforeEach
    public void createDTO() {
        dto = AuditDetailsDTO.builder()
                .userId(Long.MAX_VALUE)
                .timestamp(System.currentTimeMillis())
                .build();
        mapper = new ObjectMapper();
    }

    @Test
    @DisplayName("should have properties with getters and setters")
    public void properties() {
        assertThatBean(AuditDetailsDTO.class).hasBeanProperties("userId", "timestamp");
    }

    @Test
    @DisplayName("should be documented with Swagger")
    public void documented() {
        assertThat(AuditDetailsDTO.class).hasApiModel().withDescription();

        assertThat(AuditDetailsDTO.class).hasApiModelField("userId")
                .withNotes()
                .withExample()
                .thatIsRequired();

        assertThat(AuditDetailsDTO.class).hasApiModelField("timestamp")
                .withNotes()
                .withExample()
                .thatIsRequired();
    }

    @Test
    @DisplayName("should serialize and deserialize (to and from JSON) as expected")
    public void jsonSerialization() throws JsonProcessingException {
        var asJson = mapper.writeValueAsString(dto);
        var userId = JsonPath.read(asJson, "by");
        org.assertj.core.api.Assertions.assertThat(userId).isEqualTo(dto.getUserId());
    }
}
