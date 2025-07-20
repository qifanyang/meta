package com.meta.core.entity;

import com.meta.core.ColumnMapConverter;
import com.meta.core.MetaDefinition;
import com.meta.util.JSONUtil;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@MappedSuperclass
public abstract class MetaEntity extends BaseEntity implements MetaDefinition {

    /**
     * fieldEntity叫fieldId, modelEntity叫modelId, 统一叫法就叫code
     */
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

    @Convert(converter = ColumnMapConverter.class)
    @Column(name = "meta_attr",columnDefinition = "json")
    private Map metaAttr = new HashMap();

    @Override
    public void writeMetaAttr() {
        Class cls = getClass();
        while (cls != MetaEntity.class) {
            Field[] declaredFields = cls.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                Transient annotation = declaredField.getAnnotation(Transient.class);
                if (annotation != null) {
                    declaredField.setAccessible(true);
                    try {
                        getMetaAttr().put(declaredField.getName(), declaredField.get(this));
                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException("元数据写入异常", e);
                    }
                }
            }
            cls = cls.getSuperclass();
        }
    }

    @PostLoad
    public void readMetaAttr() {
        Class cls = getClass();
        while (cls != MetaEntity.class) {
            Field[] declaredFields = cls.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                Transient annotation = declaredField.getAnnotation(Transient.class);
                if (annotation != null) {
                    declaredField.setAccessible(true);
                    try {
                        Object value = getMetaAttr().get(declaredField.getName());
                        Object json2Value = JSONUtil.objToBean(value, declaredField.getType());
                        declaredField.set(this, json2Value);
                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException("元数据读取异常", e);
                    }
                }
            }
            cls = cls.getSuperclass();
        }
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

    public Map getMetaAttr() {
        return metaAttr;
    }

    public void setMetaAttr(Map metaAttr) {
        this.metaAttr = metaAttr;
    }

}
