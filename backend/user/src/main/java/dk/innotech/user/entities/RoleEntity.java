package dk.innotech.user.entities;

import dk.innotech.user.entities.auditing.AuditedEntity;
import dk.innotech.user.models.Language;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Map;

@Entity(name = "role")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class RoleEntity extends AuditedEntity {
    @Id
    private String name;

    @Column(name = "is_default_role", updatable = false)
    private boolean defaultRole;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "role_title")
    @MapKeyColumn(name = "language", length = 10)
    @MapKeyClass(Language.class)
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "text", nullable = false)
    @Singular
    private Map<Language, String> titles;
}
