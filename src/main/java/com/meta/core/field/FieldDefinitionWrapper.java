package com.meta.core.field;

import com.meta.core.MetaDefinitionWrapper;

import java.util.Map;

public class FieldDefinitionWrapper<T> extends MetaDefinitionWrapper<T> implements FieldDefinition<T> {

    private FieldDefinition definition;

    public FieldDefinitionWrapper(FieldDefinition definition) {
        super(definition);
        if (definition == null){
            throw new IllegalArgumentException("FieldDefinition must not be null");
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
    public String getModelCode() {
        return definition.getModelCode();
    }

    @Override
    public void setModelCode(String modelCode) {
        definition.setModelCode(modelCode);
    }

    @Override
    public String getFieldType() {
        return definition.getFieldType();
    }

    @Override
    public void setFieldType(String fieldType) {
        definition.setFieldType(fieldType);
    }

    @Override
    public Boolean getRequired() {
        return definition.getRequired();
    }

    @Override
    public void setRequired(Boolean required) {
        definition.setRequired(required);
    }

    @Override
    public String getDefaultValue() {
        return definition.getDefaultValue();
    }

    @Override
    public void setDefaultValue(String defaultValue) {
        definition.setDefaultValue(defaultValue);
    }

    @Override
    public String getExpression() {
        return definition.getExpression();
    }

    @Override
    public void setExpression(String expression) {
        definition.setExpression(expression);
    }

    @Override
    public Map<String, Object> getOptions() {
        return definition.getOptions();
    }

    @Override
    public void setOptions(Map<String, Object> options) {
        definition.setOptions(options);
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
    public T meta() {
        return (T) definition;
    }
}
