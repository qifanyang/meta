package com.meta.core;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 字段类型, 对应前端控件类型, 不是底层数据库类型
 */
public enum FieldType {

    TEXT(String.class, "TextInput", false, new FieldTypeStrategy<String>() {
        @Override
        public String getTypeId() {
            return TEXT.name();
        }

        @Override
        public Class<String> getStorageType() {
            return null;
        }

        @Override
        public String parseFromString(String input) {
            return null;
        }

        @Override
        public String parseFromUntyped(Object rawValue, ConversionContext context) throws FieldDataException {
            return null;
        }

        @Override
        public String formatForDisplay(String value, ConversionContext context) {
            return null;
        }

        @Override
        public boolean validate(String value, Map<String, Object> metadata) {
            return false;
        }

        @Override
        public String getDefaultValue() {
            return null;
        }
    }),
    TEXTAREA(String.class, "Textarea", false),
    NUMBER(BigDecimal.class, "NumberInput", false),
    BOOLEAN(Boolean.class, "Checkbox", false),
    SELECT(String.class, "Select", true),
    MULTI_SELECT(List.class, "MultiSelect", true),
    RADIO(String.class, "RadioGroup", true),
    DATE(LocalDate.class, "DatePicker", false),
    DATETIME(LocalDateTime.class, "DateTimePicker", false),
    FILE(List.class, "FileUpload", false),
    PHONE(String.class, "PhoneInput", false),
    EMAIL(String.class, "EmailInput", false),
    RICH_TEXT(String.class, "RichTextEditor", false),
    USER_PICKER(String.class, "UserPicker", true),
    DEPT_PICKER(String.class, "DeptPicker", true);

    private final Class<?> javaType;         // Java存储类型（内部值）
    private final String defaultComponent;   // 默认前端组件名（方便前后端一致）
    private final boolean optionBased;       // 是否基于选项（如下拉）
    private FieldTypeStrategy<?> strategy;

    FieldType(Class<?> javaType, String defaultComponent, boolean optionBased) {
        this.javaType = javaType;
        this.defaultComponent = defaultComponent;
        this.optionBased = optionBased;
    }

    FieldType(Class<?> javaType, String defaultComponent, boolean optionBased, FieldTypeStrategy strategy) {
        this.javaType = javaType;
        this.defaultComponent = defaultComponent;
        this.optionBased = optionBased;
    }

    public Class<?> getJavaType() {
        return javaType;
    }

    public String getDefaultComponent() {
        return defaultComponent;
    }

    public boolean isOptionBased() {
        return optionBased;
    }
}

