package dk.innotech.user.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
public class SwaggerUiController {
    @RequestMapping("/swagger")
    public String greeting() {
        return "redirect:/swagger-ui/index.html";
    }
}
