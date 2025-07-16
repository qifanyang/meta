package com.meta.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.Comment;

@MappedSuperclass
public class MetaEntity extends BaseEntity{

    @Comment("英文编码,模型应用内唯一,字段模型内唯一(不强制全局唯一)")
    @Column
    private String code;

    @Comment("中文名字")
    @Column
    private String name;

    @Column
    private String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
