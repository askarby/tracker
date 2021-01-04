package dk.innotech.user.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class UserRoleKey implements Serializable {
    @Column(name = "user_id", nullable = false)
    Long userId;

    @Column(name = "role_name", nullable = false)
    String roleName;
}
