package dk.innotech.user.mappers;

import dk.innotech.user.dtos.UserDTO;
import dk.innotech.user.dtos.UserListingDTO;
import dk.innotech.user.entities.UserEntity;
import dk.innotech.user.entities.UserRoleEntity;
import dk.innotech.user.mappers.annotations.AuditingFromEntity;
import dk.innotech.user.mappers.annotations.EpochMillisecondsFromInstant;
import dk.innotech.user.mappers.annotations.UserRolesToMap;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {
                GeneralMapper.class,
                UserRoleMapper.class
        }
)
public interface UserMapper {

    @Mapping(source = "userRoleEntities", target = "rolesWithExpiration", qualifiedBy = UserRolesToMap.class)
    @Mapping(source = "entity", target = "audit", qualifiedBy = AuditingFromEntity.class)
    @Mapping(source = "entity.accountExpiresOn", target = "accountExpiresOn", qualifiedBy = EpochMillisecondsFromInstant.class)
    @Mapping(source = "entity.credentialsExpiresOn", target = "credentialsExpiresOn", qualifiedBy = EpochMillisecondsFromInstant.class)
    UserDTO toUserDto(UserEntity entity, List<UserRoleEntity> userRoleEntities);

    UserListingDTO toUserListingDto(UserEntity entity);
}
