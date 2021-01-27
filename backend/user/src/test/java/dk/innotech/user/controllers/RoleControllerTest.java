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
import static java.util.Collections.emptyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

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
            var toCreate = mock(RoleDTO.class);

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

            assertThat(RoleController.class)
                    .withFailMessage("Needs to have documentation for conflict code")
                    .hasApiResponseAnnotation("createRole")
                    .withCode(409);
        }
    }

    @Nested
    @DisplayName("update role")
    class Update {
        @Test
        @DisplayName("should invoke RoleService")
        public void invokeService() {
            var toUpdate = mock(RoleDTO.class);

            controller.updateRole(toUpdate);

            verify(service).updateRole(eq(toUpdate));
        }

        @Test
        @DisplayName("should be documented with Swagger")
        public void documented() {
            assertThat(RoleController.class)
                    .hasApiOperationAnnotation("updateRole")
                    .withValue()
                    .withNotes();

            assertThat(RoleController.class)
                    .withFailMessage("Needs to have documentation for 'not found' code")
                    .hasApiResponseAnnotation("updateRole")
                    .withCode(404);

            assertThat(RoleController.class)
                    .withFailMessage("Needs to have documentation for 'bad request' code")
                    .hasApiResponseAnnotation("updateRole")
                    .withCode(400);
        }
    }

    @Nested
    @DisplayName("delete role")
    class Delete {
        @Test
        @DisplayName("should invoke RoleService")
        public void invokeService() {
            var toDelete = "ROLE_TEST";

            controller.deleteRole(toDelete);

            verify(service).deleteRole(eq(toDelete));
        }

        @Test
        @DisplayName("should be documented with Swagger")
        public void documented() {
            assertThat(RoleController.class)
                    .hasApiOperationAnnotation("deleteRole")
                    .withValue()
                    .withNotes();

            assertThat(RoleController.class)
                    .withFailMessage("Needs to have documentation for 'not found' code")
                    .hasApiResponseAnnotation("deleteRole")
                    .withCode(404);

            assertThat(RoleController.class)
                    .withFailMessage("Needs to have documentation for 'bad request' code")
                    .hasApiResponseAnnotation("deleteRole")
                    .withCode(400);
        }
    }

    @Nested
    @DisplayName("retrieve all roles")
    class RetrievesAll {
        @Test
        @DisplayName("should invoke RoleService")
        public void invokeService() {
            when(service.getRoles()).thenReturn(emptyList());

            controller.getRoles();
            verify(service).getRoles();
        }

        @Test
        @DisplayName("should be documented with Swagger")
        public void documented() {
            assertThat(RoleController.class)
                    .hasApiOperationAnnotation("getRoles")
                    .withValue()
                    .withNotes();
        }
    }
}
