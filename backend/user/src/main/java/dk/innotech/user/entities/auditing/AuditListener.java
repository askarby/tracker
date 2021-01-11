package dk.innotech.user.entities.auditing;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.Instant;

public class AuditListener {

    @PrePersist
    public void setCreatedOn(Auditable auditable) {
        Audit audit = auditable.getAudit();

        if(audit == null) {
            audit = new Audit();
            auditable.setAudit(audit);
        }

        audit.setCreatedOn(Instant.now());
        audit.setCreatedBy(getAuthenticatedUserId());
    }

    @PreUpdate
    public void setUpdatedOn(Auditable auditable) {
        Audit audit = auditable.getAudit();

        audit.setUpdatedOn(Instant.now());
        audit.setUpdatedBy(getAuthenticatedUserId());
    }

    private Long getAuthenticatedUserId() {
        // TODO: 2021-01-11 - We need to acquire this information from the SecurityContextHolder!
        return -1L;
    }
}
