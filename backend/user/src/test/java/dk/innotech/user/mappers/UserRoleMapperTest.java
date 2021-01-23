package dk.innotech.user.mappers;

import dk.innotech.user.entities.UserRoleEntity;
import dk.innotech.user.entities.UserRoleKey;
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

import static java.util.Arrays.asList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserRoleMapperImpl.class)
@DisplayName("Mocked & DI Unit test for UserRoleMapper")
public class UserRoleMapperTest {
    @Autowired
    private UserRoleMapper mapper;

    @Nested
    @DisplayName("mapping from list of UserRoleEntity to Map")
    class UserRoleEntityListToDTOMap {
        private UserRoleEntity userRoleEntity;

        @BeforeEach
        public void createObjects() {
            UserRoleKey key = new UserRoleKey();
            key.setUserId(42L);
            key.setRoleName("ROLE_TEST");
            userRoleEntity = (UserRoleEntity.builder()
                    .id(key)
                    .expiresAt(Instant.now().plus(31, ChronoUnit.DAYS))
                    .build());
        }

        @Test
        @DisplayName("should be able to map")
        public void mapEntityToMap() {
            var mapped = UserRoleMapper.userRolesToMap(asList(userRoleEntity));

            assertThat(mapped.size()).isEqualTo(1);

            var mappedRoleName = mapped.keySet().iterator().next();
            var mappedExpiration = mapped.values().iterator().next();

            assertThat(mappedRoleName).isEqualTo(userRoleEntity.getId().getRoleName());
            assertThat(mappedExpiration).isEqualTo(userRoleEntity.getExpiresAt().toEpochMilli());
        }
    }
}
