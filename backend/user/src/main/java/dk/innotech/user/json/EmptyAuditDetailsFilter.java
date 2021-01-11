package dk.innotech.user.json;

import dk.innotech.user.dtos.AuditDetailsDTO;

public class EmptyAuditDetailsFilter {
    @Override
    public boolean equals(Object value) {
        if (value == null) {
            return true;
        }

        AuditDetailsDTO audit = (AuditDetailsDTO)value;
        if (audit.getTimestamp() == null && audit.getUserId() == null) {
            return true;
        }

        return false;
    }
}
