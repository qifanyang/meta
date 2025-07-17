package com.meta.core;

import java.util.Map;

public class FieldDefinitionWrapper implements FieldDefinition{

    private FieldDefinition definition;

    public FieldDefinitionWrapper(FieldDefinition definition) {
        if (definition == null){
            throw new IllegalArgumentException("FieldDefinition不能为空");
        }
        this.definition = definition;
    }

    @Override
    public String getModelId() {
        return definition.getModelId();
    }

    @Override
    public void setModelId(String modelId) {
        definition.setModelId(modelId);
    }

    @Override
    public String getType() {
        return definition.getType();
    }

    @Override
    public void setType(String type) {
        definition.setType(type);
    }

    @Override
    public Boolean getRequired() {
        return null;
    }

    @Override
    public void setRequired(Boolean required) {

    }

    @Override
    public String getDefaultValue() {
        return null;
    }

    @Override
    public void setDefaultValue(String defaultValue) {

    }

    @Override
    public String getExpression() {
        return null;
    }

    @Override
    public void setExpression(String expression) {

    }

    @Override
    public Map<String, Object> getOptions() {
        return null;
    }

    @Override
    public void setOptions(Map<String, Object> options) {

    }

    @Override
    public String getOptionsUrl() {
        return null;
    }

    @Override
    public void setOptionsUrl(String optionsUrl) {

    }

    @Override
    public Map<String, Object> getValidation() {
        return null;
    }

    @Override
    public void setValidation(Map<String, Object> validation) {

    }

    @Override
    public Map<String, Object> getInteractivity() {
        return null;
    }

    @Override
    public void setInteractivity(Map<String, Object> interactivity) {

    }

    @Override
    public Map<String, Object> getLayout() {
        return null;
    }

    @Override
    public void setLayout(Map<String, Object> layout) {

    }

    @Override
    public Map<String, Object> getStyles() {
        return null;
    }

    @Override
    public void setStyles(Map<String, Object> styles) {

    }

    @Override
    public Object meta() {
        return null;
    }

    @Override
    public String getCode() {
        return null;
    }

    @Override
    public void setCode(String code) {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public String getTag() {
        return definition.getTag();
    }

    @Override
    public void setTag(String tag) {

    }
}
