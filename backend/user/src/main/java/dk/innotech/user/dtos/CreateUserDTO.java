package dk.innotech.user.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@ApiModel(description = "Model for creating a user")
public class CreateUserDTO {
    @ApiModelProperty(notes = "Username of user", example = "johndoe", required = true)
    private String username;

    @ApiModelProperty(notes = "Desired password of user", example = "secret_and_secure_password", required = true)
    private String password;

    @ApiModelProperty(notes = "Full name of user", example = "John Doe", required = true)
    private String fullName;

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

    public CreateUserDTO() {
        rolesWithExpiration = new HashMap<>();
    }
}
