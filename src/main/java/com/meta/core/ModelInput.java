package com.meta.core;

import java.util.List;
import java.util.Map;

public class ModelInput {

    /**
     * 模型code
     */
    private String modelCode;

    /**
     * 是否是迭代输入, 一个模型输入只能有一个迭代输入器, 其它的输入为数据选择
     */
    private boolean inputIterator;

    /**
     * 关联的列
     */
    private String joinKey;

    /**
     * 关联字段code, 可以是多个
     */
    private List<String> fieldCodes;

    /**
     * 必填数据
     */
    private boolean required;

    /**
     * 过滤模型数据
     */
    private Map<String, Object> conditions;

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public String getJoinKey() {
        return joinKey;
    }

    public void setJoinKey(String joinKey) {
        this.joinKey = joinKey;
    }

    public List<String> getFieldCodes() {
        return fieldCodes;
    }

    public void setFieldCodes(List<String> fieldCodes) {
        this.fieldCodes = fieldCodes;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public Map<String, Object> getConditions() {
        return conditions;
    }

    public void setConditions(Map<String, Object> conditions) {
        this.conditions = conditions;
    }
}
