package com.meta.core;

import java.time.LocalDateTime;
import java.util.Map;

public class MetaDefinitionWrapper<T> implements MetaDefinition<T> {

    private MetaDefinition definition;

    public MetaDefinitionWrapper(MetaDefinition definition) {
        this.definition = definition;
    }

    @Override
    public T meta() {
        return (T) definition;
    }

    @Override
    public String getCode() {
        return definition.getCode();
    }

    @Override
    public void setCode(String code) {
        definition.setCode(code);
    }

    @Override
    public String getName() {
        return definition.getName();
    }

    @Override
    public void setName(String name) {
        definition.setName(name);
    }

    @Override
    public String getTag() {
        return definition.getTag();
    }

    @Override
    public void setTag(String tag) {
        definition.setTag(tag);
    }

    @Override
    public Map getMetaAttr() {
        return definition.getMetaAttr();
    }

    @Override
    public void setMetaAttr(Map metaAttr) {
        definition.setMetaAttr(metaAttr);
    }

    @Override
    public void setId(String id) {
        definition.setId(id);
    }

    @Override
    public String getId() {
        return definition.getId();
    }

    @Override
    public String getTenantId() {
        return definition.getTenantId();
    }

    @Override
    public void setTenantId(String tenantId) {
        definition.setTenantId(tenantId);
    }

    @Override
    public String getCreatedBy() {
        return definition.getCreatedBy();
    }

    @Override
    public void setCreatedBy(String createdBy) {
        definition.setCreatedBy(createdBy);
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return definition.getCreatedAt();
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return definition.getUpdatedAt();
    }

    @Override
    public String getUpdatedBy() {
        return definition.getUpdatedBy();
    }

    @Override
    public void setUpdatedBy(String updatedBy) {
        definition.setUpdatedBy(updatedBy);
    }

    @Override
    public Boolean getDeleted() {
        return definition.getDeleted();
    }

    @Override
    public void setDeleted(Boolean deleted) {
        definition.setDeleted(deleted);
    }
}
