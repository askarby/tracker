package dk.innotech.user.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
@ApiModel(description = "Role model")
public class RoleDTO {
    @ApiModelProperty(notes = "Name (and unique identifier) of role", example = "ROLE_USER", required = true)
    private String name;

    @ApiModelProperty(notes = "Determines if the role in question is default or not (default roles cannot be altered, nor deleted)")
    private boolean defaultRole;

    @ApiModelProperty(notes = "Auditing information", required = true)
    private AuditDTO audit;

    @ApiModelProperty(notes = "Title of role (in danish)", example = "Brugerrolle", required = true)
    private String daTitle;

    @ApiModelProperty(notes = "Title of role (in english)", example = "User role", required = true)
    private String enTitle;
}
