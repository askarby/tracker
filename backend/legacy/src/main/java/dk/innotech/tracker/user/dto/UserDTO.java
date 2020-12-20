package dk.innotech.tracker.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private Instant dateCreated;
    private Instant dateUpdated;
    private String username;
    private String fullName;
}
