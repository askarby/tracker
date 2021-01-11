package dk.innotech.user.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRoleKey implements Serializable {
    @Column(name = "user_id", nullable = false)
    Long userId;

    @Column(name = "role_name", nullable = false)
    String roleName;
}
