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

    public FieldDefinition getDefinition(){
        return definition;
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
        definition.setTag(tag);
    }

    @Override
    public Map getMetaAttr() {
        return definition.getMetaAttr();
    }

    @Override
    public void setMetaAttr(Map metaAttr) {
        definition.setMetaAttr(metaAttr);
    }

    @Override
    public void writeMetaAttr() {
        definition.writeMetaAttr();
    }

    @Override
    public void readMetaAttr() {
        definition.readMetaAttr();
    }
}
