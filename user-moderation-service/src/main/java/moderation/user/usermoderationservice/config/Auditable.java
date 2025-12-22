package moderation.user.usermoderationservice.config;

import com.example.library.model.dao.AuditEntity;

public interface Auditable {
    AuditEntity getAudit();

    void setAudit(AuditEntity audit);
}
