package dk.innotech.user.entities.auditing;

public interface Auditable {
    void setAudit(Audit audit);
    Audit getAudit();
}
