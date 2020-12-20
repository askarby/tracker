package dk.innotech.tracker.user.persistence;

import edu.umd.cs.findbugs.annotations.NonNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Entity(name = "user")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserEntity {
    @Id
    @GeneratedValue
    private Long id;

    @CreationTimestamp
    private Instant dateCreated;

    @UpdateTimestamp
    private Instant dateUpdated;

    @NonNull
    @NotBlank
    private String username;

    @NonNull
    @NotBlank
    private String encodedPassword;

    @NonNull
    @NotBlank
    private String fullName;
}