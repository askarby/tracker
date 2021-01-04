package dk.innotech.user.controllers.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateUserRequest {
    private String username;
    private String password;
    private String fullName;
}
