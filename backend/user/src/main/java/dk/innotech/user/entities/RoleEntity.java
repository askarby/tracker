package dk.innotech.user.entities;

import dk.innotech.user.entities.auditing.Audit;
import dk.innotech.user.entities.auditing.AuditListener;
import dk.innotech.user.models.Language;
import lombok.*;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity(name = "role")
@EntityListeners(AuditListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleEntity {
    @Id
    private String name;

    @Embedded
    private Audit audit;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "role_title")
    @MapKeyColumn(name = "language", length = 10)
    @MapKeyClass(Language.class)
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "text", nullable = false)
    protected final Map<Language, String> titles = new HashMap<>();

    public void setTitle(Language language, String title) {
        titles.put(language, title);
    }

}
