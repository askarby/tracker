package dk.innotech.user.controllers;

import dk.innotech.user.dtos.RoleDTO;
import dk.innotech.user.services.RoleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static dk.innotech.user.assertions.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit test for RoleController")
public class RoleControllerTest {

    @InjectMocks
    private RoleController controller;

    @Mock
    private RoleService service;

    @Test
    @DisplayName("should be documented with Swagger")
    public void documented() {
        assertThat(RoleController.class).hasApiAnnotation().withTags("Role");
    }

    @Nested
    @DisplayName("create role")
    class Create {
        @Test
        @DisplayName("should invoke RoleService")
        public void invokeService() {
            var toCreate = RoleDTO.builder()
                    .name("ROLE_TEST")
                    .daTitle("Test-rolle")
                    .enTitle("Role for test")
                    .build();

            controller.createRole(toCreate);

            verify(service).createRole(eq(toCreate));
        }

        @Test
        @DisplayName("should be documented with Swagger")
        public void documented() {
            assertThat(RoleController.class)
                    .hasApiOperationAnnotation("createRole")
                    .withValue()
                    .withNotes();
        }
    }
}
