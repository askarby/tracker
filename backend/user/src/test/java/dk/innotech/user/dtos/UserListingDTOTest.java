package dk.innotech.user.dtos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static dk.innotech.user.assertions.Assertions.assertThat;
import static dk.innotech.user.assertions.Assertions.assertThatBean;

@DisplayName("Unit test for UserListingDTO")
public class UserListingDTOTest {
    private UserListingDTO dto;
    private ObjectMapper mapper;

    @BeforeEach
    public void createDTO() {
        dto = UserListingDTO.builder()
                .id(42L)
                .username("johndoe")
                .fullName("John Doe, Jr.")
                .build();
        mapper = new ObjectMapper();
    }

    @Test
    @DisplayName("should have properties with getters and setters")
    public void properties() {
        assertThatBean(UserListingDTO.class).hasBeanProperties("id", "username", "fullName");
    }

    @Test
    @DisplayName("should be documented with Swagger")
    public void documented() {
        assertThat(UserListingDTO.class).hasApiModel().withDescription();

        assertThat(UserListingDTO.class).hasApiModelField("id")
                .withNotes()
                .withExample()
                .thatIsRequired();
        assertThat(UserListingDTO.class).hasApiModelField("username")
                .withNotes()
                .withExample()
                .thatIsRequired();
        assertThat(UserListingDTO.class).hasApiModelField("fullName")
                .withNotes()
                .withExample()
                .thatIsRequired();
    }

    @Test
    @DisplayName("should serialize and deserialize (to and from JSON) as expected")
    public void jsonSerialization() throws JsonProcessingException {
        var asJson = mapper.writeValueAsString(dto);
        var asObject = mapper.readValue(asJson, UserListingDTO.class);
        org.assertj.core.api.Assertions.assertThat(asObject).isEqualTo(dto);
    }
}
