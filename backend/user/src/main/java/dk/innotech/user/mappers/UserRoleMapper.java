package dk.innotech.user.mappers;

import dk.innotech.user.entities.UserRoleEntity;
import dk.innotech.user.mappers.annotations.UserRolesToMap;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {

    @UserRolesToMap
    static Map<String, Long> userRolesToMap(List<UserRoleEntity> userRoleEntities) {
        Function<UserRoleEntity, String> getRoleName = entity -> entity.getId().getRoleName();
        Function<UserRoleEntity, Long> getExpirationInMilliseconds = entity -> entity.getExpiresAt().toEpochMilli();
        return userRoleEntities.stream().collect(Collectors.toMap(getRoleName, getExpirationInMilliseconds));
    }
}
