package com.meta.core.entity;

import java.time.LocalDateTime;

public interface BaseEntityDefinition {
    void setId(String id);

    String getId();

    String getTenantId();

    void setTenantId(String tenantId);

    String getCreatedBy();

    void setCreatedBy(String createdBy);

    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();

    String getUpdatedBy();

    void setUpdatedBy(String updatedBy);

    Boolean getDeleted();

    void setDeleted(Boolean deleted);
}
