package dk.innotech.user.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Column;
import java.time.Instant;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ApiModel(description = "User model")
public class UserDTO {
    @ApiModelProperty(notes = "Unique identifier of user", example = "1L", required = false)
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
    @Singular("roleWithExpiration")
    private Map<String, Long> rolesWithExpiration;

    @ApiModelProperty(notes = "Expiration time of account", example = "19238723191238")
    private Long accountExpiresOn;

    @ApiModelProperty(notes = "Is account locked", required = true)
    private boolean locked;

    @ApiModelProperty(notes = "The reason behind the account being locked")
    private String lockedReason;

    @ApiModelProperty(notes = "Expiration time of credentials", example = "19238723191238")
    private Long credentialsExpiresOn;
}
