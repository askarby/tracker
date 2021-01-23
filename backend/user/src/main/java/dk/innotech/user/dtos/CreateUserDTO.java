package dk.innotech.user.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "Model for creating a user")
public class CreateUserDTO {
    @ApiModelProperty(notes = "Username of user", example = "johndoe", required = true)
    private String username;

    @ApiModelProperty(notes = "Desired password of user", example = "secret_and_secure_password", required = true)
    private String password;

    @ApiModelProperty(notes = "Full name of user", example = "John Doe", required = true)
    private String fullName;

    @Singular("roleWithExpiration")
    @ApiModelProperty(
            notes = "Roles (and expiration thereof) applied to user",
            required = true,
            example = """
                    {
                        "ROLE_USER": 19238723191238,
                        "ROLE_ADMIN": 19238723191238
                    }
                    """
    )
    private Map<String, Long> rolesWithExpiration;
}
