package dk.innotech.user.controllers;

import dk.innotech.user.dtos.RoleDTO;
import dk.innotech.user.services.RoleService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

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
    @ApiResponses({
            @ApiResponse(code = 409, message = "Unable to create role, due to naming conflict")
    })
    @RequestMapping(value = {"/"}, method = RequestMethod.POST)
    @Secured("ROLE_ADMIN")
    public RoleDTO createRole(@ApiParam("Request to create role from")
                              @RequestBody
                              @Valid RoleDTO role) {
        return roleService.createRole(role);
    }

    @ApiOperation(
            value = "Updates an existing role",
            notes = """
                    Updates an existing role.
                                        
                    This endpoint is meant to be used as a means for administrators to update (alter) an already existing 
                    role, that can be applied to users.
                    """
    )
    @ApiResponses({
            @ApiResponse(code = 404, message = "Unable to find role to update")
    })
    @RequestMapping(value = {""}, method = RequestMethod.PUT)
    @Secured("ROLE_ADMIN")
    public RoleDTO updateRole(@ApiParam("Request to update role from") @RequestBody @Valid RoleDTO role) {
        return roleService.updateRole(role);
    }

    @ApiOperation(
            value = "Removes an existing role",
            notes = """
                    Removes an existing role.
                                        
                    This endpoint is meant to be used as a means for administrators to remove an already existing 
                    role, that can be applied to users.
                    """
    )
    @ApiResponses({
            @ApiResponse(code = 404, message = "Unable to find role to remove")
    })
    @RequestMapping(value = {"/{name}"}, method = RequestMethod.DELETE)
    @Secured("ROLE_ADMIN")
    public void deleteRole(@ApiParam("Name (id) of role to remove") @PathVariable("name") String name) {
        roleService.deleteRole(name);
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
