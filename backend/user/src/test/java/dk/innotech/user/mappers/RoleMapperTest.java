package dk.innotech.user.mappers;

import dk.innotech.user.dtos.AuditDTO;
import dk.innotech.user.dtos.AuditDetailsDTO;
import dk.innotech.user.dtos.RoleDTO;
import dk.innotech.user.entities.RoleEntity;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RoleMapperImpl.class)
@DisplayName("Mocked & DI Unit test for RoleMapper")
public class RoleMapperTest {
    @Autowired
    private RoleMapper mapper;

    private RoleEntity entity;
    private RoleDTO dto;

    @BeforeEach
    public void createObjects() {
        var updatedOn = Instant.now();
        var updatedBy = 2L;
        var createdOn = updatedOn.minus(1, ChronoUnit.DAYS);
        var createdBy = 1L;

        entity = RoleEntity.builder()
                .createdBy(createdBy)
                .createdOn(createdOn)
                .updatedBy(updatedBy)
                .updatedOn(updatedOn)
                .name("name")
                .defaultRole(true)
                .title(Language.DANISH, "danish")
                .title(Language.ENGLISH, "english")
                .build();

        dto = RoleDTO.builder()
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
                .defaultRole(true)
                .name("name")
                .daTitle("danish")
                .enTitle("english")
                .build();
    }

    @Nested
    @DisplayName("mapping from RoleEntity to RoleDTO")
    class ToDTO {
        @Test
        @DisplayName("should be able to map a fully populated RoleEntity")
        public void fullyPopulatedEntity() {
            var mapped = mapper.toRoleDto(entity);

            var created = mapped.getAudit().getCreated();
            var updated = mapped.getAudit().getUpdated();
            assertThat(created.getTimestamp()).isEqualTo(entity.getCreatedOn().toEpochMilli());
            assertThat(created.getUserId()).isEqualTo(entity.getCreatedBy());
            assertThat(updated.getTimestamp()).isEqualTo(entity.getUpdatedOn().toEpochMilli());
            assertThat(updated.getUserId()).isEqualTo(entity.getUpdatedBy());

            assertThat(mapped.getName()).isEqualTo(entity.getName());
            assertThat(mapped.isDefaultRole()).isEqualTo(entity.isDefaultRole());
            assertThat(mapped.getDaTitle()).isEqualTo(entity.getTitles().get(Language.DANISH));
            assertThat(mapped.getEnTitle()).isEqualTo(entity.getTitles().get(Language.ENGLISH));
        }
    }

    @Nested
    @DisplayName("mapping from RoleEntity to RoleDTO")
    class ToEntity {
        @Test
        @DisplayName("should be able to map a fully populated RoleDTO")
        public void mapsToEntity() {
            var mapped = mapper.toRoleEntity(dto);

            // Auditing
            var created = dto.getAudit().getCreated();
            var updated = dto.getAudit().getUpdated();
            assertThat(mapped.getCreatedOn()).isEqualTo(Instant.ofEpochMilli(created.getTimestamp()));
            assertThat(mapped.getCreatedBy()).isEqualTo(created.getUserId());
            assertThat(mapped.getUpdatedOn()).isEqualTo(Instant.ofEpochMilli(updated.getTimestamp()));
            assertThat(mapped.getUpdatedBy()).isEqualTo(updated.getUserId());

            // Other properties
            assertThat(mapped.getName()).isEqualTo(entity.getName());
            assertThat(mapped.isDefaultRole()).isTrue();
            assertThat(mapped.getTitles().get(Language.DANISH)).isEqualTo(dto.getDaTitle());
            assertThat(mapped.getTitles().get(Language.ENGLISH)).isEqualTo(dto.getEnTitle());
        }

        @Test
        @DisplayName("should be able to map a RoleDTO without auditing information")
        public void withoutAuditingInformation() {
            var mapped = mapper.toRoleEntity(dto.toBuilder().audit(null).build());
            assertThat(mapped.getCreatedOn()).isNull();
            assertThat(mapped.getCreatedBy()).isNull();
            assertThat(mapped.getUpdatedOn()).isNull();
            assertThat(mapped.getUpdatedBy()).isNull();
        }
    }

}
