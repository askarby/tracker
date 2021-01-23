package dk.innotech.user.dtos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static dk.innotech.user.assertions.Assertions.assertThat;
import static dk.innotech.user.assertions.Assertions.assertThatBean;

@DisplayName("Unit test for CreateUserDTO")
public class CreateUserDTOTest {
    private CreateUserDTO dto;
    private ObjectMapper mapper;

    @BeforeEach
    public void createDTO() {
        dto = CreateUserDTO.builder()
                .username("johndoe")
                .password("secret")
                .fullName("John Doe, Jr.")
                .roleWithExpiration("ROLE_USER", 19238723191238L)
                .roleWithExpiration("ROLE_ADMIN", 19238723978574L)
                .build();
        mapper = new ObjectMapper();
    }

    @Test
    @DisplayName("should have properties with getters and setters")
    public void properties() {
        assertThatBean(CreateUserDTO.class).hasBeanProperties("username", "password", "fullName", "rolesWithExpiration");
    }

    @Test
    @DisplayName("should be documented with Swagger")
    public void documented() {
        assertThat(CreateUserDTO.class).hasApiModel().withDescription();


        assertThat(CreateUserDTO.class).hasApiModelField("username")
                .withNotes()
                .withExample()
                .thatIsRequired();
        assertThat(CreateUserDTO.class).hasApiModelField("password")
                .withNotes()
                .withExample()
                .thatIsRequired();
        assertThat(CreateUserDTO.class).hasApiModelField("fullName")
                .withNotes()
                .withExample()
                .thatIsRequired();
        assertThat(CreateUserDTO.class).hasApiModelField("rolesWithExpiration")
                .withNotes()
                .withExample()
                .thatIsRequired();
    }

    @Test
    @DisplayName("should serialize and deserialize (to and from JSON) as expected")
    public void jsonSerialization() throws JsonProcessingException {
        var asJson = mapper.writeValueAsString(dto);
        var asObject = mapper.readValue(asJson, CreateUserDTO.class);
        org.assertj.core.api.Assertions.assertThat(asObject).isEqualTo(dto);
    }
}
