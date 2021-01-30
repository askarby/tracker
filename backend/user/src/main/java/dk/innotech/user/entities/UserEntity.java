package dk.innotech.user.entities;

import dk.innotech.user.entities.auditing.AuditedEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;

@Entity(name = "user")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class UserEntity extends AuditedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "encoded_password", nullable = false)
    private String encodedPassword;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "account_expires_on")
    private Instant accountExpiresOn;

    @Column(name = "is_locked", nullable = false)
    private boolean locked;

    @Column(name = "locked_reason")
    private String lockedReason;

    @Column(name = "credentials_expires_on")
    private Instant credentialsExpiresOn;
}
