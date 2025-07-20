package com.meta.core.entity;


import com.meta.core.field.FieldDefinition;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

import java.util.Map;

@Entity
@Table(name = "meta_field")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "field_class")
@DiscriminatorValue("FieldEntity") //不指定使用类名
public class FieldEntity extends MetaEntity implements FieldDefinition {

    @Column(name = "model_id")
    private String modelId;

    @Comment("字段类型")
    @Column(name = "field_type")
    private String fieldType;

    @Column
    private Boolean required;

    @Column(name = "default_value")
    private String defaultValue;

    /*** 表达式: 变量, 函数调用, 数学计算, 逻辑运算等*/
    @Column
    private String expression;

    @Transient
    private Map<String, Object> options;// 静态选项（针对 ENUM 和 MULTI_SELECT）

    @Transient
    private String optionsUrl;// 动态选项接口

    @Transient
    private Map<String, Object> validation;// 校验规则

    @Transient
    private Map<String, Object> interactivity;// 动态行为

    @Transient
    private Map<String, Object> layout;// 布局配置

    @Transient
    private Map<String, Object> styles;// 样式配置

    @Transient
    private Map<String, String> label; //标签（多语言支持）

    @Override
    public String getModelId() {
        return modelId;
    }

    @Override
    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    @Override
    public String getFieldType() {
        return fieldType;
    }

    @Override
    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    @Override
    public Boolean getRequired() {
        return required;
    }

    @Override
    public void setRequired(Boolean required) {
        this.required = required;
    }

    @Override
    public String getDefaultValue() {
        return defaultValue;
    }

    @Override
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public String getExpression() {
        return expression;
    }

    @Override
    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public Map<String, Object> getOptions() {
        return options;
    }

    @Override
    public void setOptions(Map<String, Object> options) {
        this.options = options;
    }

    @Override
    public String getOptionsUrl() {
        return optionsUrl;
    }

    @Override
    public void setOptionsUrl(String optionsUrl) {
        this.optionsUrl = optionsUrl;
    }

    @Override
    public Map<String, Object> getValidation() {
        return validation;
    }

    @Override
    public void setValidation(Map<String, Object> validation) {
        this.validation = validation;
    }

    @Override
    public Map<String, Object> getInteractivity() {
        return interactivity;
    }

    @Override
    public void setInteractivity(Map<String, Object> interactivity) {
        this.interactivity = interactivity;
    }

    @Override
    public Map<String, Object> getLayout() {
        return layout;
    }

    @Override
    public void setLayout(Map<String, Object> layout) {
        this.layout = layout;
    }

    @Override
    public Map<String, Object> getStyles() {
        return styles;
    }

    @Override
    public void setStyles(Map<String, Object> styles) {
        this.styles = styles;
    }

    @Override
    public FieldEntity meta() {
        return this;
    }
}
