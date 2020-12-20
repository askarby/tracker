package dk.innotech.tracker.user.mapping;

import dk.innotech.tracker.user.persistence.UserEntity;
import dk.innotech.tracker.user.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jsr330")
public interface UserMapper {

    UserDTO userEntityToUserDto(UserEntity entity);
}
