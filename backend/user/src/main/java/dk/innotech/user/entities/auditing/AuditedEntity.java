package dk.innotech.user.entities.auditing;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuditedEntity {
    @Column(name = "created_on", nullable = false, updatable = false)
    @CreatedDate
    private Instant createdOn;

    @Column(name = "created_by", nullable = false, updatable = false)
    @CreatedBy
    private Long createdBy;

    @Column(name = "updated_on")
    @LastModifiedDate
    private Instant updatedOn;

    @Column(name = "updated_by")
    @LastModifiedBy
    private Long updatedBy;
}
