package dk.innotech.user.mappers;

import dk.innotech.user.dtos.AuditDTO;
import dk.innotech.user.dtos.AuditDetailsDTO;
import dk.innotech.user.dtos.UserDTO;
import dk.innotech.user.entities.RoleEntity;
import dk.innotech.user.entities.UserEntity;
import dk.innotech.user.entities.UserRoleEntity;
import dk.innotech.user.entities.UserRoleKey;
import dk.innotech.user.models.Language;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserMapperImpl.class)
@DisplayName("Mocked & DI Unit test for UserMapper")
public class UserMapperTest {
    @Autowired
    private UserMapper mapper;

    private UserEntity userEntity;
    private RoleEntity roleEntity;
    private List<UserRoleEntity> userRoleEntities;
    private UserDTO dto;

    @BeforeEach
    public void createObjects() {
        var updatedOn = Instant.now();
        var updatedBy = 2L;
        var createdOn = updatedOn.minus(1, ChronoUnit.DAYS);
        var createdBy = 1L;
        var accountExpiresOn = updatedOn.plus(365, ChronoUnit.DAYS);
        var credentialsExpiresOn = updatedOn.plus(31, ChronoUnit.DAYS);

        userEntity = UserEntity.builder()
                .id(42L)
                .createdBy(createdBy)
                .createdOn(createdOn)
                .updatedBy(updatedBy)
                .updatedOn(updatedOn)
                .username("johndoe")
                .encodedPassword("encoded password")
                .fullName("John Doe, Jr.")
                .accountExpiresOn(accountExpiresOn)
                .locked(true)
                .lockedReason("Banned due to test purposes")
                .credentialsExpiresOn(credentialsExpiresOn)
                .build();

        roleEntity = RoleEntity.builder()
                .createdBy(createdBy)
                .createdOn(createdOn)
                .updatedBy(updatedBy)
                .updatedOn(updatedOn)
                .name("ROLE_TEST")
                .defaultRole(true)
                .title(Language.DANISH, "danish")
                .title(Language.ENGLISH, "english")
                .build();

        userRoleEntities = new ArrayList<>();

        UserRoleKey key = new UserRoleKey();
        key.setUserId(userEntity.getId());
        key.setRoleName("ROLE_TEST");
        userRoleEntities.add(UserRoleEntity.builder()
                .id(key)
                .role(roleEntity)
                .user(userEntity)
                .expiresAt(Instant.now().plus(31, ChronoUnit.DAYS))
                .build());

        dto = UserDTO.builder()
                .audit(AuditDTO.builder()
                        .created(AuditDetailsDTO.builder()
                                .timestamp(createdOn.toEpochMilli())
                                .userId(createdBy)
                                .build())
                        .updated(AuditDetailsDTO.builder()
                                .timestamp(updatedOn.toEpochMilli())
                                .userId(updatedBy)
                                .build())
                        .build()
                )
                .username("johndoe")
                .fullName("John Doe, Jr.")
                .roleWithExpiration("ROLE_TEST", userRoleEntities.get(0).getExpiresAt().toEpochMilli())
                .build();
    }

    @Nested
    @DisplayName("mapping from UserEntity to UserDTO")
    class ToDTO {
        @Test
        @DisplayName("should be able to map a fully populated UserEntity")
        public void fullyPopulatedEntity() {
            var mapped = mapper.toUserDto(userEntity, userRoleEntities);

            var created = mapped.getAudit().getCreated();
            var updated = mapped.getAudit().getUpdated();
            assertThat(created.getTimestamp()).isEqualTo(userEntity.getCreatedOn().toEpochMilli());
            assertThat(created.getUserId()).isEqualTo(userEntity.getCreatedBy());
            assertThat(updated.getTimestamp()).isEqualTo(userEntity.getUpdatedOn().toEpochMilli());
            assertThat(updated.getUserId()).isEqualTo(userEntity.getUpdatedBy());

            assertThat(mapped.getId()).isEqualTo(userEntity.getId());
            assertThat(mapped.getUsername()).isEqualTo(userEntity.getUsername());
            assertThat(mapped.getFullName()).isEqualTo(userEntity.getFullName());
            assertThat(mapped.getRolesWithExpiration().size()).isEqualTo(1);
            assertThat(mapped.getRolesWithExpiration()).extracting("ROLE_TEST")
                    .isEqualTo(userRoleEntities.get(0).getExpiresAt().toEpochMilli());
            assertThat(mapped.getAccountExpiresOn()).isEqualTo(userEntity.getAccountExpiresOn().toEpochMilli());
            assertThat(mapped.isLocked()).isEqualTo(userEntity.isLocked());
            assertThat(mapped.getLockedReason()).isEqualTo(userEntity.getLockedReason());
            assertThat(mapped.getCredentialsExpiresOn()).isEqualTo(userEntity.getCredentialsExpiresOn().toEpochMilli());
        }
    }

    @Nested
    @DisplayName("mapping from UserEntity to UserListingDTO")
    class ToListingDTO {
        @Test
        @DisplayName("should be able to map a UserEntity")
        public void fullyPopulatedEntity() {
            var mapped = mapper.toUserListingDto(userEntity);

            assertThat(mapped.getId()).isEqualTo(userEntity.getId());
            assertThat(mapped.getUsername()).isEqualTo(userEntity.getUsername());
            assertThat(mapped.getFullName()).isEqualTo(userEntity.getFullName());
        }
    }
}