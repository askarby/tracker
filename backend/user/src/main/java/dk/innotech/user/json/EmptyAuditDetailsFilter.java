package dk.innotech.user.json;

import dk.innotech.user.dtos.AuditDetailsDTO;

public class EmptyAuditDetailsFilter {
    @Override
    public boolean equals(Object value) {
        if (!(value instanceof AuditDetailsDTO)) {
            return true;
        }

        AuditDetailsDTO audit = (AuditDetailsDTO)value;
        var isPopulated = audit.getTimestamp() != null && audit.getUserId() != null;

        return !isPopulated;
    }
}
