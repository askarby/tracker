package dk.innotech.user.controllers;

import dk.innotech.user.dtos.CreateUserDTO;
import dk.innotech.user.dtos.RegisterUserDTO;
import dk.innotech.user.dtos.UserDTO;
import dk.innotech.user.dtos.UserListingDTO;
import dk.innotech.user.services.UserService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("user")
@AllArgsConstructor
@Api(value = "Endpoint for user related operations", tags = "User")
public class UserController {
    private final UserService userService;

    @ApiOperation(
            value = "Creates a new user, this is not the same as registering",
            notes = """
                    Creates a new user.
                                        
                    This endpoint is meant to be used as a means for administrators to create users.
                                        
                    ℹ️ This endpoint is not the same as registering a user. 
                    """
    )
    @ApiResponses({
            @ApiResponse(code = 404, message = "Unable to create user, when unable to find roles"),
            @ApiResponse(code = 409, message = "Unable to create user, due to naming conflict")
    })
    @RequestMapping(value = {"/"}, method = RequestMethod.POST)
    @Secured("ROLE_ADMIN")
    public UserDTO createUser(@ApiParam("Request to create user from")
                              @RequestBody
                              @Valid CreateUserDTO request) {
        return userService.createUser(request);
    }

    @ApiOperation(
            value = "Updates an existing user",
            notes = """
                    Updates an existing user.
                                        
                    This endpoint is meant to be used as a means for administrators to update (alter) an already existing user.
                    
                    ℹ️ Any id provided in request body is overridden by that in path.
                    """
    )
    @ApiResponses({
            @ApiResponse(code = 404, message = "Unable to find user to update")
    })
    @RequestMapping(value = {"/{id}"}, method = RequestMethod.PUT)
    @Secured("ROLE_ADMIN")
    public UserDTO updateUser(@ApiParam("Request to create user from")
                              @PathVariable("id") Long id,
                              @RequestBody
                              @Valid UserDTO request) {
        return userService.updateUser(id, request);
    }

    @ApiOperation(
            value = "Removes an existing user",
            notes = """
                    Removes an existing user.
                                        
                    This endpoint is meant to be used as a means for administrators to remove an already existing user.
                    """
    )
    @ApiResponses({
            @ApiResponse(code = 404, message = "Unable to find user to remove")
    })
    @RequestMapping(value = {"/{id}"}, method = RequestMethod.DELETE)
    @Secured("ROLE_ADMIN")
    public void deleteUser(@ApiParam("Id of user to remove") @PathVariable("id") Long id) {
        userService.deleteUser(id);
    }

    @ApiOperation(
            value = "Registration of a new user",
            notes = """
                    Allows people to register a new user.
                                        
                    This endpoint is for people to register their own users (limiting certain possibilities, eg. setting roles etc.).
                                        
                    ℹ️ This endpoint is not meant to be used as a means for administrators to create users.
                    """
    )
    @ApiResponses({
            @ApiResponse(code = 409, message = "Unable to register user, due to naming conflict")
    })
    @RequestMapping(value = {"/register"}, method = RequestMethod.POST)
    public UserDTO registerUser(@ApiParam("Request to register user from")
                                @RequestBody
                                @Valid RegisterUserDTO request) {
        return userService.registerUser(request);
    }

    @ApiOperation(
            value = "Retrieves a list of users",
            notes = """
                    Retrieves a listing of users.
                                        
                    This endpoint is meant to be used as a means for administrators to retrieve a complete (abbreviated) list of users.
                    """
    )
    @RequestMapping(method = RequestMethod.GET)
    @Secured("ROLE_ADMIN")
    public List<UserListingDTO> getUserListings() {
        return userService.getUserListings();
    }

    @ApiOperation(
            value = "Retrieves a user by id",
            notes = """
                    Retrieves a user by id.
                                        
                    This endpoint is meant to be used as a means for administrators to retrieve a user (with full information).
                    """,
            response = UserDTO.class
    )
    @ApiResponses({
            @ApiResponse(code = 404, message = "Unable to retrieve user by id")
    })
    @RequestMapping(value = {"{id}"}, method = RequestMethod.GET)
    @Secured("ROLE_ADMIN")
    public ResponseEntity getUser(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
