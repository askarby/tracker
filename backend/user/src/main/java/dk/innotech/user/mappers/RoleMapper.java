package dk.innotech.user.mappers;

import dk.innotech.user.dtos.RoleDTO;
import dk.innotech.user.entities.RoleEntity;
import dk.innotech.user.mappers.annotations.AuditingFromEntity;
import dk.innotech.user.mappers.annotations.DanishTextFromMap;
import dk.innotech.user.mappers.annotations.EnglishTextFromMap;
import dk.innotech.user.mappers.annotations.InstantFromEpochMilliseconds;
import dk.innotech.user.models.Language;
import org.mapstruct.*;
import org.springframework.util.StringUtils;

@Mapper(
        componentModel = "spring",
        uses = {
                GeneralMapper.class
        }
)
public interface RoleMapper {

    @Mapping(source = "titles", target = "daTitle", qualifiedBy = DanishTextFromMap.class)
    @Mapping(source = "titles", target = "enTitle", qualifiedBy = EnglishTextFromMap.class)
    @Mapping(source = "entity", target = "audit", qualifiedBy = AuditingFromEntity.class)
    RoleDTO toRoleDto(RoleEntity entity);

    @Mapping(target = "titles", ignore = true)
    @Mapping(source = "audit.created.userId", target = "createdBy")
    @Mapping(source = "audit.created.timestamp", target = "createdOn", qualifiedBy = InstantFromEpochMilliseconds.class)
    @Mapping(source = "audit.updated.userId", target = "updatedBy")
    @Mapping(source = "audit.updated.timestamp", target = "updatedOn", qualifiedBy = InstantFromEpochMilliseconds.class)
    RoleEntity toRoleEntity(RoleDTO dto);

    @AfterMapping
    default void addTitles(@MappingTarget RoleEntity.RoleEntityBuilder<?, ?> builder, RoleDTO dto) {
        if (StringUtils.hasText(dto.getDaTitle())) {
            builder.title(Language.DANISH, dto.getDaTitle());
        }
        if (StringUtils.hasText(dto.getEnTitle())) {
            builder.title(Language.ENGLISH, dto.getEnTitle());
        }
    }
}
