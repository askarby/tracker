package dk.innotech.user.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Mocked MVC test for SwaggerUiController")
public class SwaggerUiControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("should redirect to 'swagger ui' index.html page")
    public void redirect() throws Exception {
        var request = get("/swagger");

        mockMvc.perform(request)
                .andExpect(redirectedUrl("/swagger-ui/index.html"));
    }
}
