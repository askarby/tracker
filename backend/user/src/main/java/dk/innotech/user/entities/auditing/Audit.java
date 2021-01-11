package dk.innotech.user.entities.auditing;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.Instant;

@Embeddable
@Getter
@Setter
public class Audit {

    @Column(name = "created_on", nullable = false)
    private Instant createdOn;

    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    @Column(name = "updated_on")
    private Instant updatedOn;

    @Column(name = "updated_by")
    private Long updatedBy;
}