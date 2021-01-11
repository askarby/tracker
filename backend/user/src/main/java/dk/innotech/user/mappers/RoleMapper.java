package dk.innotech.user.mappers;

import dk.innotech.user.dtos.RoleDTO;
import dk.innotech.user.entities.RoleEntity;
import dk.innotech.user.mappers.annotations.DanishTextFromMap;
import dk.innotech.user.mappers.annotations.EnglishTextFromMap;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {
                GeneralMapper.class
        }
)
public interface RoleMapper {

    @Mapping(source = "titles", target = "daTitle", qualifiedBy = DanishTextFromMap.class)
    @Mapping(source = "titles", target = "enTitle", qualifiedBy = EnglishTextFromMap.class)
    RoleDTO toRoleDto(RoleEntity entity);
}
