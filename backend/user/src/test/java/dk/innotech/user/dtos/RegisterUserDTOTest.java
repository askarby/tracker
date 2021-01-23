package dk.innotech.user.dtos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static dk.innotech.user.assertions.Assertions.assertThat;
import static dk.innotech.user.assertions.Assertions.assertThatBean;

@DisplayName("Unit test for RegisterUserDTO")
public class RegisterUserDTOTest {
    private RegisterUserDTO dto;
    private ObjectMapper mapper;

    @BeforeEach
    public void createDTO() {
        dto = RegisterUserDTO.builder()
                .username("johndoe")
                .password("secret")
                .fullName("John Doe, Jr.")
                .build();
        mapper = new ObjectMapper();
    }

    @Test
    @DisplayName("should have properties with getters and setters")
    public void properties() {
        assertThatBean(RegisterUserDTO.class).hasBeanProperties("username", "password", "fullName");
    }

    @Test
    @DisplayName("should be documented with Swagger")
    public void documented() {
        assertThat(RegisterUserDTO.class).hasApiModel().withDescription();


        assertThat(RegisterUserDTO.class).hasApiModelField("username")
                .withNotes()
                .withExample()
                .thatIsRequired();
        assertThat(RegisterUserDTO.class).hasApiModelField("password")
                .withNotes()
                .withExample()
                .thatIsRequired();
        assertThat(RegisterUserDTO.class).hasApiModelField("fullName")
                .withNotes()
                .withExample()
                .thatIsRequired();
    }

    @Test
    @DisplayName("should serialize and deserialize (to and from JSON) as expected")
    public void jsonSerialization() throws JsonProcessingException {
        var asJson = mapper.writeValueAsString(dto);
        var asObject = mapper.readValue(asJson, RegisterUserDTO.class);
        org.assertj.core.api.Assertions.assertThat(asObject).isEqualTo(dto);
    }
}
