package com.meta.form;


/**
 * 字段公开接口, 暴露方法, 方便字段定义采用枚举可以继承接口
 */
public interface FormFieldDefinition {

    FormFieldType formFieldType();

    default Object toDb(Object value){
        return formFieldType().toDb(value);
    }

    default String toDisplay(Object value){
        return formFieldType().toDisplay(value);
    };
}
