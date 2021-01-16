package dk.innotech.user.mappers;

import dk.innotech.user.dtos.AuditDTO;
import dk.innotech.user.dtos.AuditDetailsDTO;
import dk.innotech.user.entities.auditing.AuditedEntity;
import dk.innotech.user.mappers.annotations.AuditingFromEntity;
import dk.innotech.user.mappers.annotations.DanishTextFromMap;
import dk.innotech.user.mappers.annotations.EnglishTextFromMap;
import dk.innotech.user.mappers.annotations.EpochMillisecondsFromInstant;
import dk.innotech.user.models.Language;
import org.mapstruct.Mapper;

import java.time.Instant;
import java.util.Map;

import static java.util.Optional.ofNullable;

@Mapper(componentModel = "spring")
public interface GeneralMapper {

    @AuditingFromEntity
    static AuditDTO auditToDTO(AuditedEntity audit) {
        var builder = AuditDTO.builder()
                .created(AuditDetailsDTO.builder().timestamp(audit.getCreatedOn().toEpochMilli()).userId(audit.getCreatedBy()).build());

        if (audit.getUpdatedOn() != null && audit.getUpdatedBy() != null) {
            builder.updated(AuditDetailsDTO.builder().timestamp(audit.getUpdatedOn().toEpochMilli()).userId(audit.getUpdatedBy()).build());
        }

        return builder.build();
    }

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
