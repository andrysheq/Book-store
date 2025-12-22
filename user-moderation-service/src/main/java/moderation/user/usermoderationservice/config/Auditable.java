package moderation.user.usermoderationservice.config;

import moderation.user.usermoderationservice.model.dao.AuditEntity;

public interface Auditable {
    AuditEntity getAudit();

    void setAudit(AuditEntity audit);
}
