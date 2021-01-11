package dk.innotech.user.mappers;

import dk.innotech.user.dtos.AuditDTO;
import dk.innotech.user.entities.auditing.Audit;
import dk.innotech.user.mappers.annotations.DanishTextFromMap;
import dk.innotech.user.mappers.annotations.EnglishTextFromMap;
import dk.innotech.user.mappers.annotations.EpochMillisecondsFromInstant;
import dk.innotech.user.models.Language;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.util.Map;

import static java.util.Optional.ofNullable;

@Mapper(componentModel = "spring")
public interface GeneralMapper {

    @Mapping(source = "createdOn", target="created.timestamp", qualifiedBy = EpochMillisecondsFromInstant.class)
    @Mapping(source = "createdBy", target="created.userId")
    @Mapping(source = "updatedOn", target="updated.timestamp", qualifiedBy = EpochMillisecondsFromInstant.class)
    @Mapping(source = "updatedBy", target="updated.userId")
    AuditDTO auditToDTO(Audit audit);

    @DanishTextFromMap
    static String danishTextFromMap(Map<Language, String> source) {
        return source.get(Language.DANISH);
    }

    @EnglishTextFromMap
    static String englishTextFromMap(Map<Language, String> source) {
        return source.get(Language.ENGLISH);
    }

    @EpochMillisecondsFromInstant
    static Long millisecondsFromInstant(Instant instant) {
        return ofNullable(instant).map(Instant::toEpochMilli).orElse(null);
    }
}
