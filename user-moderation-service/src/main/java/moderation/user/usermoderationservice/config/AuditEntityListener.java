package moderation.user.usermoderationservice.config;

import com.example.library.config.Auditable;
import com.example.library.model.dao.AuditEntity;
import jakarta.persistence.PrePersist;

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
