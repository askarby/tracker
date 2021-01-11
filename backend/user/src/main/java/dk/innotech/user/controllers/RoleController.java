package dk.innotech.user.controllers;

import dk.innotech.user.dtos.RoleDTO;
import dk.innotech.user.services.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("role")
@AllArgsConstructor
@Api(value = "Endpoint for role related operations", tags = "Role")
public class RoleController {
    private final RoleService roleService;

    @ApiOperation(
            value = "Creates a new role",
            notes = """
                    Creates a new role.
                                        
                    This endpoint is meant to be used as a means for administrators to create new roles, that
                    can be applied to users.
                    """
    )
    @RequestMapping(value = {"/"}, method = RequestMethod.POST)
    @Secured("ROLE_ADMIN")
    public RoleDTO createRole(@ApiParam("Request to create role from")
                              @RequestBody
                              @Valid RoleDTO role) {
        return roleService.createRole(role);
    }

    @ApiOperation(
            value = "Retrieves all roles",
            notes = """
                    Retrieves all roles.
                                        
                    This endpoint is meant to be used as a means for administrators to list all available roles, that
                    can be applied to users.
                    """
    )
    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    @Secured("ROLE_ADMIN")
    public RoleDTO[] getRoles() {
        return roleService.getRoles().toArray(new RoleDTO[0]);
    }

}
