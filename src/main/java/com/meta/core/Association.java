package com.meta.core;


import java.util.List;

public class Association {

    /**
     * 模型code
     */
    private String modelCode;

    /**
     * 关联字段code, 可以是多个
     */
    private List<String> fieldCodes;

    /**
     * 必须引用
     */
    private boolean required;

    public static Association of(String modelCode, List<String> fieldCodes){
        Association association = new Association();
        association.setModelCode(modelCode);
        association.setFieldCodes(fieldCodes);
        return association;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public List<String> getFieldCodes() {
        return fieldCodes;
    }

    public void setFieldCodes(List<String> fieldCodes) {
        this.fieldCodes = fieldCodes;
    }
}
