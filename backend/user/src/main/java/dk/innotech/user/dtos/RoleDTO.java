package dk.innotech.user.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(description = "Role model")
public class RoleDTO {
    @ApiModelProperty(notes = "Name (and unique identifier) of role", example = "ROLE_USER", required = true)
    private String name;

    @ApiModelProperty(notes = "Auditing information", required = true)
    private AuditDTO audit;

    @ApiModelProperty(notes = "Title of role (in danish)", example = "Brugerrolle", required = true)
    private String daTitle;

    @ApiModelProperty(notes = "Title of role (in english)", example = "User role", required = true)
    private String enTitle;
}
