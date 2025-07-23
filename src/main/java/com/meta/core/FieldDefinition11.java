package com.meta.core;

import com.meta.core.field.FieldType;

import java.util.Map;

public class FieldDefinition11 {

    private String fieldName;                 // 字段名称
    private Map<String, String> label;       // 标签（多语言支持）
    private FieldType fieldType;             // 字段类型
    private boolean required;                // 是否必填
    private Object defaultValue;             // 默认值
    private Map<String, Object> options;     // 静态选项（针对 ENUM 和 MULTI_SELECT）
    private String optionsUrl;               // 动态选项接口
    private Map<String, Object> validation;  // 校验规则
    private Map<String, Object> layout;      // 布局配置
    private Map<String, Object> interactivity;// 动态行为
    private Map<String, Object> styles;      // 样式配置

    // 构造方法
    public FieldDefinition11(String fieldName, Map<String, String> label, FieldType fieldType, boolean required,
                             Object defaultValue, Map<String, Object> options, String optionsUrl,
                             Map<String, Object> validation, Map<String, Object> layout,
                             Map<String, Object> interactivity, Map<String, Object> styles) {
        this.fieldName = fieldName;
        this.label = label;
        this.fieldType = fieldType;
        this.required = required;
        this.defaultValue = defaultValue;
        this.options = options;
        this.optionsUrl = optionsUrl;
        this.validation = validation;
        this.layout = layout;
        this.interactivity = interactivity;
        this.styles = styles;
    }

    // Getters
    public String getFieldName() {
        return fieldName;
    }

    public Map<String, String> getLabel() {
        return label;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public boolean isRequired() {
        return required;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public Map<String, Object> getOptions() {
        return options;
    }

    public String getOptionsUrl() {
        return optionsUrl;
    }

    public Map<String, Object> getValidation() {
        return validation;
    }

    public Map<String, Object> getLayout() {
        return layout;
    }

    public Map<String, Object> getInteractivity() {
        return interactivity;
    }

    public Map<String, Object> getStyles() {
        return styles;
    }
}
