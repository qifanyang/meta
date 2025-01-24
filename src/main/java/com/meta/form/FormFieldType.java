package com.meta.form;

import java.util.Objects;

/**
 * 表单字段类型, 低代码选择字段
 */
public enum FormFieldType implements FormFieldDefinition {
    TEXT,
    ENUM, //强调固定选项, 比如性别, 数据字段
    SINGLE_SELECT,//强调动态选项, 单选
    MULTI_SELECT,
    FILE,
    IMAGE,
    CUSTOM;

    @Override
    public FormFieldType formFieldType() {
        return this;
    }

    @Override
    public Object toDb(Object value) {
        return value;
    }

    @Override
    public String toDisplay(Object value) {
        return Objects.toString(value, "");
    }


}
