package dk.innotech.user.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity(name = "user_role")
@Getter
@Setter
@NoArgsConstructor
public class UserRoleEntity {
    @EmbeddedId
    private UserRoleKey id;

    @ManyToOne
    @MapsId("id")
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @MapsId("name")
    @JoinColumn(name = "name")
    private RoleEntity role;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

}
