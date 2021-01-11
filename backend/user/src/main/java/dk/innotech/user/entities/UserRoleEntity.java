package dk.innotech.user.entities;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Entity(name = "user_role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRoleEntity {
    @EmbeddedId
    private UserRoleKey id;

    @ManyToOne
    @MapsId("id")
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @MapsId("name")
    @JoinColumn(name = "role_name")
    private RoleEntity role;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

}
