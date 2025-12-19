package com.example.library.config;

import com.example.library.model.dao.AuditEntity;

public interface Auditable {
    AuditEntity getAudit();

    void setAudit(AuditEntity audit);
}
