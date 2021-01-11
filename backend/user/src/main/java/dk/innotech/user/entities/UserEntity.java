package dk.innotech.user.entities;

import dk.innotech.user.entities.auditing.Audit;
import dk.innotech.user.entities.auditing.AuditListener;
import dk.innotech.user.entities.auditing.Auditable;
import lombok.*;

import javax.persistence.*;

@Entity(name = "user")
@EntityListeners(AuditListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity implements Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Audit audit;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "encoded_password", nullable = false)
    private String encodedPassword;

    @Column(name = "full_name", nullable = false)
    private String fullName;
}
