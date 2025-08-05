package com.meta.core.model;

import java.util.List;
import java.util.Map;

public class ModelInput {

    /**
     * 模型code
     */
    private String modelCode;

    /**
     * 是否是主模型, 一个模型输入只能有一个主输入器, 其它的输入为可选数据
     */
    private boolean mainInput;

    /**
     * 关联的列, 不等于空则需要关联主模型
     */
    private String joinKey;

    /**
     * 关联字段code, 可以是多个
     */
    private List<String> fieldCodes;

    /**
     * 必填输入
     */
    private boolean required;

    /**
     * 过滤模型数据
     */
    private Map<String, Object> conditions;

    /**
     * 如果model code为空, 简单参数, 如果有模型输入, 叠加到模型数据之上的用户参数
     */
    private Map<String, Object> params;

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

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public boolean isMainInput() {
        return mainInput;
    }

    public void setMainInput(boolean mainInput) {
        this.mainInput = mainInput;
    }
}
