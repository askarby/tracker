package dk.innotech.user.entities;

import dk.innotech.user.models.Language;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity(name = "role")
@Getter
@Setter
@NoArgsConstructor
public class RoleEntity {
    @Id
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "role_title")
    @MapKeyColumn(name = "language", length = 40, nullable = false)
    @MapKeyClass(Language.class)
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "text", nullable = false)
    protected final Map<Language, String> titles = new HashMap<>();

}
