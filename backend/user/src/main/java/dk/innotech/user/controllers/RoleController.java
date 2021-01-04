package dk.innotech.user.controllers;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("role")
@Api(value = "Endpoint for role related operations", tags = "Role")
public class RoleController {
}
