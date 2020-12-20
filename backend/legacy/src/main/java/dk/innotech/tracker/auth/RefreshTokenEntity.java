package dk.innotech.tracker.auth;

import edu.umd.cs.findbugs.annotations.NonNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity(name = "refresh_token")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RefreshTokenEntity {
    @Id
    @GeneratedValue
    private Long id;

    @CreationTimestamp
    private Instant dateCreated;

    @NonNull
    @NotBlank
    private String username;

    @NonNull
    @NotBlank
    private String refreshToken;

    @NonNull
    @NotNull
    private Boolean revoked;
}