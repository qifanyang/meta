package com.meta.app.model.hr.person;


import com.meta.core.field.FieldBean;
import com.meta.core.field.FieldType;

import java.util.ArrayList;
import java.util.List;

public enum PersonFieldEnum {

    NAME("name", "姓名", FieldType.TEXT, PersonModel.CODE, PersonModel.CODE),
    ID_NUMBER("idNumber", "身份证", FieldType.TEXT, PersonModel.CODE, PersonModel.CODE),
    PHONE("phone", "电话", FieldType.TEXT, PersonModel.CODE, PersonModel.CODE),
    ;

    String code;
    String name;
    FieldType fieldType;
    String modelCode;
    String sourceModelCode;

    PersonFieldEnum(String code, String name, FieldType fieldType, String modelCode, String sourceModelCode) {
        this.code = code;
        this.name = name;
        this.fieldType = fieldType;
        this.modelCode = modelCode;
        this.sourceModelCode = sourceModelCode;
    }

    public static List<FieldBean> getAllFields() {
        return getFields(null);
    }

    public static List<FieldBean> getFields(String sourceModelCode) {
        List<FieldBean> fieldBeanList = new ArrayList<>();
        for (PersonFieldEnum value : values()) {
            if (sourceModelCode == null) {
                FieldBean fb = ofField(value);
                fb.setModelCode(PersonModel.CODE);
                fieldBeanList.add(fb);
            }
        }
        return fieldBeanList;
    }

    public static FieldBean ofField(PersonFieldEnum pf) {
        return FieldBean.of(pf.getCode(), pf.getName(), pf.getFieldType().name());
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

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public String getSourceModelCode() {
        return sourceModelCode;
    }

    public void setSourceModelCode(String sourceModelCode) {
        this.sourceModelCode = sourceModelCode;
    }
}
