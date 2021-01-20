package dk.innotech.user.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dk.innotech.user.dtos.AuditDTO;
import dk.innotech.user.dtos.AuditDetailsDTO;
import dk.innotech.user.dtos.RoleDTO;
import dk.innotech.user.services.RoleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Mocked MVC test for RoleController")
public class RoleControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RoleService roleService;

    @Test
    @WithMockUser(username = "john", roles={"ADMIN"})
    public void createRole() throws Exception {
        // Given
        var toCreate = RoleDTO.builder()
                .name("ROLE_TEST")
                .daTitle("Test-rolle")
                .enTitle("Role for test")
                .build();

        var audit = AuditDTO.builder()
                .created(AuditDetailsDTO.builder().userId(1L).timestamp(Instant.now().toEpochMilli()).build())
                .build();
        var response = toCreate.toBuilder().audit(audit).build();

        // When
        when(roleService.createRole(toCreate)).thenReturn(response);

        // Then
        var request = post("/role/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toCreate));

        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }
}
