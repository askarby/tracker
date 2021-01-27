package dk.innotech.user.controllers;

import dk.innotech.user.dtos.CreateUserDTO;
import dk.innotech.user.dtos.RegisterUserDTO;
import dk.innotech.user.dtos.UserDTO;
import dk.innotech.user.services.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static dk.innotech.user.assertions.Assertions.assertThat;
import static java.util.Collections.emptyList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit test for UserController")
public class UserControllerTest {

    @InjectMocks
    private UserController controller;

    @Mock
    private UserService service;

    @Test
    @DisplayName("should be documented with Swagger")
    public void documented() {
        assertThat(UserController.class).hasApiAnnotation().withTags("User");
    }

    @Nested
    @DisplayName("create user")
    class Create {
        @Test
        @DisplayName("should invoke UserService")
        public void invokeService() {
            var toCreate = mock(CreateUserDTO.class);

            controller.createUser(toCreate);

            verify(service).createUser(eq(toCreate));
        }

        @Test
        @DisplayName("should be documented with Swagger")
        public void documented() {
            assertThat(UserController.class)
                    .hasApiOperationAnnotation("createUser")
                    .withValue()
                    .withNotes();

            assertThat(UserController.class)
                    .withFailMessage("Needs to have documentation for conflict code")
                    .hasApiResponseAnnotation("createUser")
                    .withCode(409);
        }
    }

    @Nested
    @DisplayName("update user")
    class Update {
        @Test
        @DisplayName("should invoke UserService")
        public void invokeService() {
            var userId = 42L;
            var toUpdate = mock(UserDTO.class);

            controller.updateUser(userId, toUpdate);

            verify(service).updateUser(eq(userId), eq(toUpdate));
        }

        @Test
        @DisplayName("should be documented with Swagger")
        public void documented() {
            assertThat(UserController.class)
                    .hasApiOperationAnnotation("updateUser")
                    .withValue()
                    .withNotes();

            assertThat(UserController.class)
                    .withFailMessage("Needs to have documentation for 'not found' code")
                    .hasApiResponseAnnotation("updateUser")
                    .withCode(404);
        }
    }

    @Nested
    @DisplayName("delete user")
    class Delete {
        @Test
        @DisplayName("should invoke UserService")
        public void invokeService() {
            var toDelete = 42L;

            controller.deleteUser(toDelete);

            verify(service).deleteUser(eq(toDelete));
        }

        @Test
        @DisplayName("should be documented with Swagger")
        public void documented() {
            assertThat(UserController.class)
                    .hasApiOperationAnnotation("deleteUser")
                    .withValue()
                    .withNotes();

            assertThat(UserController.class)
                    .withFailMessage("Needs to have documentation for 'not found' code")
                    .hasApiResponseAnnotation("deleteUser")
                    .withCode(404);
        }
    }

    @Nested
    @DisplayName("register user")
    class Register {
        @Test
        @DisplayName("should invoke UserService")
        public void invokeService() {
            var toRegister = mock(RegisterUserDTO.class);

            controller.registerUser(toRegister);

            verify(service).registerUser(eq(toRegister));
        }

        @Test
        @DisplayName("should be documented with Swagger")
        public void documented() {
            assertThat(UserController.class)
                    .hasApiOperationAnnotation("registerUser")
                    .withValue()
                    .withNotes();

            assertThat(UserController.class)
                    .withFailMessage("Needs to have documentation for 'conflict' code")
                    .hasApiResponseAnnotation("registerUser")
                    .withCode(409);
        }
    }

    @Nested
    @DisplayName("retrieve user listings")
    class RetrievesListings {
        @Test
        @DisplayName("should invoke UserService")
        public void invokeService() {
            when(service.getUserListings()).thenReturn(emptyList());

            controller.getUserListings();
            verify(service).getUserListings();
        }

        @Test
        @DisplayName("should be documented with Swagger")
        public void documented() {
            assertThat(UserController.class)
                    .hasApiOperationAnnotation("getUserListings")
                    .withValue()
                    .withNotes();
        }
    }

    @Nested
    @DisplayName("retrieve user by id")
    class RetrieveById {
        @Test
        @DisplayName("should invoke UserService to retrieve user with an ok response")
        public void invokeService() {
            var toRetrieve = 42L;
            var serviceResponse = mock(UserDTO.class);

            when(service.getUserById(toRetrieve)).thenReturn(of(serviceResponse));

            var retrieved = controller.getUser(toRetrieve);

            verify(service).getUserById(eq(toRetrieve));
            org.assertj.core.api.Assertions.assertThat(retrieved.getStatusCode().value()).isEqualTo(200);
            org.assertj.core.api.Assertions.assertThat(retrieved.getBody()).isEqualTo(serviceResponse);
        }

        @Test
        @DisplayName("should be unable to retrieve user and with a 'not found' response")
        public void invokeServiceNotFound() {
            var toRetrieve = 42L;

            when(service.getUserById(toRetrieve)).thenReturn(empty());

            var retrieved = controller.getUser(toRetrieve);

            verify(service).getUserById(eq(toRetrieve));
            org.assertj.core.api.Assertions.assertThat(retrieved.getStatusCode().value()).isEqualTo(404);
            org.assertj.core.api.Assertions.assertThat(retrieved.getBody()).isNull();
        }

        @Test
        @DisplayName("should be documented with Swagger")
        public void documented() {
            assertThat(UserController.class)
                    .hasApiOperationAnnotation("getUser")
                    .withValue()
                    .withNotes();

            assertThat(UserController.class)
                    .withFailMessage("Needs to have documentation for 'not found' code")
                    .hasApiResponseAnnotation("getUser")
                    .withCode(404);
        }
    }

}
