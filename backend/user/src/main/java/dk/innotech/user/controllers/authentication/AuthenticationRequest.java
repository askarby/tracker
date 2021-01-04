package dk.innotech.user.controllers.authentication;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@ApiModel(description = "Request for authenticating a user")
public class AuthenticationRequest {
    @NotBlank(message = "{error.authenticationRequest.username.required}")
    @ApiModelProperty(notes = "Username to authenticate with", example = "johndoe", required = true)
    private String username;

    @NotBlank(message = "{error.authenticationRequest.password.required}")
    @ApiModelProperty(notes = "Password to authenticate with", example = "secretpassword", required = true)
    private String password;
}
