package dk.innotech.user.mappers;

import dk.innotech.user.entities.auditing.AuditedEntity;
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
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = GeneralMapperImpl.class)
@DisplayName("Mocked & DI Unit test for GeneralMapper")
public class GeneralMapperTest {
    @Autowired
    private GeneralMapper mapper;

    @Nested
    @DisplayName("mapping from AuditedEntity to AuditDTO")
    class AuditedEntityToDTO {
        private AuditedEntity entity;

        @BeforeEach
        public void createObjects() {
            var updatedOn = Instant.now();
            var updatedBy = 2L;
            var createdOn = updatedOn.minus(1, ChronoUnit.DAYS);
            var createdBy = 1L;

            entity = new AuditedEntity();
            entity.setCreatedBy(createdBy);
            entity.setCreatedOn(createdOn);
            entity.setUpdatedBy(updatedBy);
            entity.setUpdatedOn(updatedOn);
        }

        @Test
        @DisplayName("should be able to map a fully populated AuditedEntity")
        public void fullyPopulatedEntity() {
            var mapped = GeneralMapper.auditToDTO(entity);

            var created = mapped.getCreated();
            assertThat(created.getTimestamp()).isEqualTo(entity.getCreatedOn().toEpochMilli());
            assertThat(created.getUserId()).isEqualTo(entity.getCreatedBy());

            var updated = mapped.getUpdated();
            assertThat(updated.getTimestamp()).isEqualTo(entity.getUpdatedOn().toEpochMilli());
            assertThat(updated.getUserId()).isEqualTo(entity.getUpdatedBy());
        }

        @Test
        @DisplayName("should not map a created object, when without 'created on'")
        public void withoutCreatedOn() {
            entity.setCreatedOn(null);
            var mapped = GeneralMapper.auditToDTO(entity);
            assertThat(mapped.getCreated()).isNull();
        }

        @Test
        @DisplayName("should not map a created object, when without 'created by'")
        public void withoutCreatedBy() {
            entity.setCreatedBy(null);
            var mapped = GeneralMapper.auditToDTO(entity);
            assertThat(mapped.getCreated()).isNull();
        }

        @Test
        @DisplayName("should not map a updated object, when without 'updated on'")
        public void withoutUpdatedOn() {
            entity.setUpdatedOn(null);
            var mapped = GeneralMapper.auditToDTO(entity);
            assertThat(mapped.getUpdated()).isNull();
        }

        @Test
        @DisplayName("should not map a updated object, when without 'updated by'")
        public void withoutUpdatedBy() {
            entity.setUpdatedBy(null);
            var mapped = GeneralMapper.auditToDTO(entity);
            assertThat(mapped.getUpdated()).isNull();
        }
    }

    @Nested
    @DisplayName("extract danish text from map")
    class DanishTextRetrieval {
        private Map<Language, String> translations;

        @BeforeEach
        public void createMap() {
            translations = new HashMap<>();
            translations.put(Language.DANISH, "danish");
        }

        @Test
        @DisplayName("should retrieve translation when present")
        public void retrievePresent() {
            var translation = GeneralMapper.danishTextFromMap(translations);
            assertThat(translation).isEqualTo(translations.get(Language.DANISH));
        }

        @Test
        @DisplayName("should retrieve null when absent")
        public void retrieveAbsent() {
            translations.clear();
            var translation = GeneralMapper.danishTextFromMap(translations);
            assertThat(translation).isNull();
        }
    }


    @Nested
    @DisplayName("extract english text from map")
    class EnglishTextRetrieval {
        private Map<Language, String> translations;

        @BeforeEach
        public void createMap() {
            translations = new HashMap<>();
            translations.put(Language.ENGLISH, "english");
        }

        @Test
        @DisplayName("should retrieve translation when present")
        public void retrievePresent() {
            var translation = GeneralMapper.englishTextFromMap(translations);
            assertThat(translation).isEqualTo(translations.get(Language.ENGLISH));
        }

        @Test
        @DisplayName("should retrieve null when absent")
        public void retrieveAbsent() {
            translations.clear();
            var translation = GeneralMapper.englishTextFromMap(translations);
            assertThat(translation).isNull();
        }
    }

    @Nested
    @DisplayName("conversion from Instant to milliseconds (long)")
    class MillisecondsFromInstant {
        @Test
        @DisplayName("should convert when present")
        public void present() {
            var now = Instant.now();
            var converted = GeneralMapper.millisecondsFromInstant(now);
            assertThat(converted).isEqualTo(now.toEpochMilli());
        }

        @Test
        @DisplayName("should pass through null value")
        public void absent() {
            var converted = GeneralMapper.millisecondsFromInstant(null);
            assertThat(converted).isNull();
        }
    }

    @Nested
    @DisplayName("conversion from milliseconds (long) to Instant")
    class InstantFromMilliseconds {
        @Test
        @DisplayName("should convert from epoch milliseconds")
        public void present() {
            // We need to allow for some leeway, since conversion back and forth is not nano-second precise!
            var expected = Instant.now().truncatedTo(ChronoUnit.SECONDS);
            var converted = GeneralMapper.instantFromMilliseconds(expected.toEpochMilli());
            assertThat(converted.truncatedTo(ChronoUnit.SECONDS)).isEqualTo(expected);
        }
    }
}
