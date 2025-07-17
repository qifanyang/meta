package com.meta.core.entity;

import com.meta.core.JsonConverter;
import com.meta.core.MetaDefinition;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

import java.util.HashMap;
import java.util.Map;

@MappedSuperclass
public abstract class MetaEntity<T> extends BaseEntity implements MetaDefinition {

    @Comment("英文编码,模型应用内唯一,字段模型内唯一(不强制全局唯一)")
    @Column
    private String code;

    @Comment("中文名字")
    @Column
    private String name;

    /**
     * 多个便签使用冒号:分割, 单个标签内可使用小数点.隔开表示层级
     * tab.basic.home:xx.yy.xx
     */
    @Column
    private String tag;

    @Column
    private String description;

    @Convert(converter = JsonConverter.class)
    @Column(columnDefinition = "json")
    private Map attr = new HashMap();

    @PrePersist
    @PreUpdate
    public void pre(){
        System.out.println("PrePersist");
    }

    @PostLoad
    public void post(){
        System.out.println("PostLoad");
    }

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

    @Override
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map getAttr() {
        return attr;
    }

    public void setAttr(Map attr) {
        this.attr = attr;
    }

}
