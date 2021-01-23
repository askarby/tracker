package dk.innotech.user.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ApiModel(description = "User listing model (Abbreviated version of user model)")
public class UserListingDTO {
    @ApiModelProperty(notes = "Unique identifier of user", example = "1L", required = true)
    private Long id;

    @ApiModelProperty(notes = "Username of user", example = "johndoe", required = true)
    private String username;

    @ApiModelProperty(notes = "Full name of user", example = "John Doe", required = true)
    private String fullName;
}
