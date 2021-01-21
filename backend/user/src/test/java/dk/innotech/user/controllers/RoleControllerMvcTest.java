package dk.innotech.user.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dk.innotech.user.dtos.AuditDTO;
import dk.innotech.user.dtos.AuditDetailsDTO;
import dk.innotech.user.dtos.RoleDTO;
import dk.innotech.user.services.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Collections;

import static java.util.Collections.emptyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Nested
    @DisplayName("creating role")
    class Create {
        private RoleDTO toCreate;

        @BeforeEach
        public void createDTO() {
            toCreate = RoleDTO.builder()
                    .name("ROLE_TEST")
                    .daTitle("Test-rolle")
                    .enTitle("Role for test")
                    .build();
        }

        @Test
        @WithMockUser(username = "john", roles = {"ADMIN"})
        @DisplayName("should be possible with ROLE_ADMIN")
        public void createRole() throws Exception {
            // Given
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

            mockMvc.perform(request)
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(response)));
        }

        @Test
        @WithMockUser(username = "john", roles = {"USER"})
        @DisplayName("should not be possible with role different from ROLE_ADMIN")
        public void createRoleInvalidRole() throws Exception {
            var request = post("/role/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(toCreate));

            mockMvc.perform(request)
                    .andExpect(status().isForbidden());
        }

        @Test
        @WithAnonymousUser
        @DisplayName("should not be possible with anonymous user")
        public void createRoleUnauthorized() throws Exception {
            var request = post("/role/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(toCreate));

            mockMvc.perform(request)
                    .andExpect(status().isUnauthorized());
        }
    }

    @Nested
    @DisplayName("updating role")
    class Update {
        private RoleDTO toUpdate;

        @BeforeEach
        public void createDTO() {
            toUpdate = RoleDTO.builder()
                    .name("ROLE_TEST")
                    .daTitle("Test-rolle")
                    .enTitle("Role for test")
                    .build();
        }

        @Test
        @WithMockUser(username = "john", roles = {"ADMIN"})
        @DisplayName("should be possible with ROLE_ADMIN")
        public void updateRole() throws Exception {
            // Given
            var audit = AuditDTO.builder()
                    .created(AuditDetailsDTO.builder().userId(1L).timestamp(Instant.now().toEpochMilli()).build())
                    .build();
            var response = toUpdate.toBuilder().audit(audit).build();

            // When
            when(roleService.updateRole(toUpdate)).thenReturn(response);

            // Then
            var request = put("/role/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(toUpdate));

            mockMvc.perform(request)
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(response)));
        }

        @Test
        @WithMockUser(username = "john", roles = {"USER"})
        @DisplayName("should not be possible with role different from ROLE_ADMIN")
        public void updateRoleInvalidRole() throws Exception {
            var request = put("/role/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(toUpdate));

            mockMvc.perform(request)
                    .andExpect(status().isForbidden());
        }

        @Test
        @WithAnonymousUser
        @DisplayName("should not be possible with anonymous user")
        public void updateRoleUnauthorized() throws Exception {
            var request = put("/role/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(toUpdate));

            mockMvc.perform(request)
                    .andExpect(status().isUnauthorized());
        }
    }

    @Nested
    @DisplayName("deleting role")
    class Delete {
        private String toDelete;

        @BeforeEach
        public void createDTO() {
            toDelete = "ROLE_TEST";
        }

        @Test
        @WithMockUser(username = "john", roles = {"ADMIN"})
        @DisplayName("should be possible with ROLE_ADMIN")
        public void deleteRole() throws Exception {
            var request = delete("/role/{name}", toDelete)
                    .contentType(MediaType.APPLICATION_JSON);

            mockMvc.perform(request)
                    .andExpect(status().isOk());
        }

        @Test
        @WithMockUser(username = "john", roles = {"USER"})
        @DisplayName("should not be possible with role different from ROLE_ADMIN")
        public void createRoleInvalidRole() throws Exception {
            var request = delete("/role/{name}", toDelete)
                    .contentType(MediaType.APPLICATION_JSON);

            mockMvc.perform(request)
                    .andExpect(status().isForbidden());
        }

        @Test
        @WithAnonymousUser
        @DisplayName("should not be possible with anonymous user")
        public void createRoleUnauthorized() throws Exception {
            var request = delete("/role/{name}", toDelete)
                    .contentType(MediaType.APPLICATION_JSON);

            mockMvc.perform(request)
                    .andExpect(status().isUnauthorized());
        }
    }

    @Nested
    @DisplayName("retrieve all roles")
    class RetrievesAll {

        @Test
        @WithMockUser(username = "john", roles = {"ADMIN"})
        @DisplayName("should be possible with ROLE_ADMIN")
        public void deleteRole() throws Exception {
            var request = get("/role/")
                    .contentType(MediaType.APPLICATION_JSON);

            when(roleService.getRoles()).thenReturn(emptyList());

            mockMvc.perform(request)
                    .andExpect(status().isOk());
        }

        @Test
        @WithMockUser(username = "john", roles = {"USER"})
        @DisplayName("should not be possible with role different from ROLE_ADMIN")
        public void createRoleInvalidRole() throws Exception {
            var request = get("/role/")
                    .contentType(MediaType.APPLICATION_JSON);

            mockMvc.perform(request)
                    .andExpect(status().isForbidden());
        }

        @Test
        @WithAnonymousUser
        @DisplayName("should not be possible with anonymous user")
        public void createRoleUnauthorized() throws Exception {
            var request = get("/role/")
                    .contentType(MediaType.APPLICATION_JSON);

            mockMvc.perform(request)
                    .andExpect(status().isUnauthorized());
        }
    }
}
