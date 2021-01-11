package dk.innotech.user.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@ApiModel(description = "User model")
public class UserDTO {
    @ApiModelProperty(notes = "Unique identifier of user", example = "1L", required = true)
    private Long id;

    @ApiModelProperty(notes = "Auditing information", required = true)
    private AuditDTO audit;

    @ApiModelProperty(notes = "Username of user", example = "johndoe", required = true)
    private String username;

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
}
