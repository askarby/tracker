package dk.innotech.user.controllers.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
@Api(value = "Endpoint for user related operations", tags = "User")
public class UserController {
    @RequestMapping(value = {"/hellouser"}, method = RequestMethod.GET)
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @ApiOperation(value = "Say hello (as user).")
    public String helloUser() {
        return "Hello User";
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = {"/helloadmin"}, method = RequestMethod.GET)
    @ApiOperation(value = "Say hello (as admin).")
    public String helloAdmin() {
        return "Hello Admin";
    }
}
