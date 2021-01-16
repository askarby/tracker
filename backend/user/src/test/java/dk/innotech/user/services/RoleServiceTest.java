package dk.innotech.user.services;

import dk.innotech.user.dtos.RoleDTO;
import dk.innotech.user.entities.RoleEntity;
import dk.innotech.user.mappers.RoleMapper;
import dk.innotech.user.models.Language;
import dk.innotech.user.repositories.RoleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Mocked Unit test for RoleService")
public class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private RoleService roleService;

    @Nested
    @DisplayName("createRole")
    class CreateRole {

        @Test
        @DisplayName("should prevent default roles from being created")
        public void preventDefaultRoles() {
            // Given
            var name = "ROLE_NEW_ONE";
            var toCreate = RoleDTO.builder().name(name).defaultRole(true).build();
            var toPersist = RoleEntity.builder().name(name).defaultRole(true).build();

            // When
            when(roleMapper.toRoleEntity(toCreate)).thenReturn(toPersist);
            when(roleRepository.findById(name)).thenReturn(Optional.empty());
            when(roleRepository.save(any())).then(answer -> answer.getArgument(0));

            // Then
            roleService.createRole(toCreate);
            var captor = ArgumentCaptor.forClass(RoleEntity.class);
            verify(roleRepository).save(captor.capture());

            assertThat(captor.getValue().isDefaultRole()).isFalse();
        }

        @Test
        @DisplayName("should save role using repository")
        public void saveRole() {
            // Given
            var name = "ROLE_NEW_ONE";
            var toCreate = RoleDTO.builder().name(name).build();
            var toPersist = RoleEntity.builder().name(name).defaultRole(true).build();

            // When
            when(roleMapper.toRoleEntity(toCreate)).thenReturn(toPersist);
            when(roleRepository.findById(name)).thenReturn(Optional.empty());
            when(roleRepository.save(any())).thenReturn(toPersist);

            // Then
            roleService.createRole(toCreate);
            verify(roleRepository).save(eq(toPersist));
            verify(roleMapper).toRoleDto(any(RoleEntity.class));
        }

        @Test
        @DisplayName("should throw AlreadyExistsException if invoked with name of existing role")
        public void availableName() {
            // Given
            var name = "ROLE_EXISTING_ONE";
            var toCreate = RoleDTO.builder().name(name).build();
            var existing = RoleEntity.builder().name(name).build();

            // When
            when(roleMapper.toRoleDto(existing)).thenReturn(toCreate);
            when(roleRepository.findById(name)).thenReturn(Optional.of(existing));

            // Then
            assertThatThrownBy(() -> roleService.createRole(toCreate))
                    .isInstanceOfAny(AlreadyExistsException.class)
                    .hasMessage("Role with name '%s' already exists", name);
        }
    }

    @Nested
    @DisplayName("updateRole")
    class UpdateRole {
        @Test
        @DisplayName("should save role using repository")
        public void saveRole() {
            // Given
            var toUpdate = RoleDTO.builder()
                    .name("ROLE_EXISTING")
                    .daTitle("Dansk titel")
                    .enTitle("English title")
                    .build();
            var name = toUpdate.getName();
            var existing = RoleEntity.builder().name(name).build();

            // When
            when(roleMapper.toRoleDto(existing)).thenReturn(toUpdate);
            when(roleRepository.findById(name)).thenReturn(Optional.of((existing)));
            when(roleRepository.save(any())).thenReturn(existing);

            // Then
            roleService.updateRole(toUpdate);
            var captor = ArgumentCaptor.forClass(RoleEntity.class);
            verify(roleRepository).save(captor.capture());
            // Called twice, once when doing checks on existence, second when after saving (producing response)
            verify(roleMapper, times(2)).toRoleDto(any(RoleEntity.class));

            var captured = captor.getValue();
            assertThat(captured.getName()).isEqualTo(name);
            assertThat(captured.isDefaultRole()).isFalse();
            assertThat(captured.getTitles()).containsEntry(Language.DANISH, toUpdate.getDaTitle());
            assertThat(captured.getTitles()).containsEntry(Language.ENGLISH, toUpdate.getEnTitle());
        }

        @Test
        @DisplayName("should throw NotExistsException if invoked with name of non-existing role")
        public void nonExisting() {
            // Given
            var toUpdate = RoleDTO.builder().name("ROLE_EXISTING").build();
            var name = toUpdate.getName();

            // When
            when(roleRepository.findById(name)).thenReturn(Optional.empty());

            // Then
            assertThatThrownBy(() -> roleService.updateRole(toUpdate))
                    .isInstanceOfAny(NotExistsException.class)
                    .hasMessage("Role with name '%s' does not exist", name);
        }

        @Test
        @DisplayName("should throw UnmodifiableException if invoked with name of default role")
        public void unmodifiable() {
            // Given
            var toUpdate = RoleDTO.builder().name("ROLE_EXISTING").build();
            var name = toUpdate.getName();
            var existing = RoleEntity.builder().name(name).defaultRole(true).build();

            // When
            when(roleMapper.toRoleDto(existing)).thenReturn(toUpdate.toBuilder().defaultRole(true).build());
            when(roleRepository.findById(name)).thenReturn(Optional.of(existing));

            // Then
            assertThatThrownBy(() -> roleService.updateRole(toUpdate))
                    .isInstanceOfAny(UnmodifiableException.class)
                    .hasMessage("Role with name '%s' cannot be modified", name);
        }
    }

    @Nested
    @DisplayName("deleteRole")
    class DeleteRole {
        @Test
        @DisplayName("should delete role using repository")
        public void deleteRole() {
            // Given
            var name = "ROLE_EXISTING";
            var toDelete = RoleDTO.builder().name(name).build();
            var existing = RoleEntity.builder().name(name).build();

            // When
            when(roleMapper.toRoleDto(existing)).thenReturn(toDelete);
            when(roleRepository.findById(name)).thenReturn(Optional.of((existing)));

            // Then
            roleService.deleteRole(name);
            verify(roleRepository).deleteById(eq(name));
        }

        @Test
        @DisplayName("should throw NotExistsException if invoked with name of non-existing role")
        public void nonExisting() {
            // Given
            var name = "ROLE_EXISTING";

            // When
            when(roleRepository.findById(name)).thenReturn(Optional.empty());

            // Then
            assertThatThrownBy(() -> roleService.deleteRole(name))
                    .isInstanceOfAny(NotExistsException.class)
                    .hasMessage("Role with name '%s' does not exist", name);
        }

        @Test
        @DisplayName("should throw UnmodifiableException if invoked with name of default role")
        public void unmodifiable() {
            // Given
            var name = "ROLE_SYSTEM";
            var existing = RoleEntity.builder().name(name).defaultRole(true).build();

            // When
            when(roleMapper.toRoleDto(existing)).thenReturn(RoleDTO.builder().defaultRole(true).build());
            when(roleRepository.findById(name)).thenReturn(Optional.of(existing));

            // Then
            assertThatThrownBy(() -> roleService.deleteRole(name))
                    .isInstanceOfAny(UnmodifiableException.class)
                    .hasMessage("Role with name '%s' cannot be modified", name);
        }
    }
}
