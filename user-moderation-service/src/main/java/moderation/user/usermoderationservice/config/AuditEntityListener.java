package moderation.user.usermoderationservice.config;

import jakarta.persistence.PrePersist;
import moderation.user.usermoderationservice.model.dao.AuditEntity;

import java.time.LocalDateTime;

public class AuditEntityListener {

    @PrePersist
    public void prePersist(Object entity) {
        if (entity instanceof Auditable auditable) {
            AuditEntity audit = auditable.getAudit();
            if (audit == null) {
                audit = new AuditEntity();
                auditable.setAudit(audit);
            }
            audit.setCreatedAt(LocalDateTime.now());
        }
    }
}
