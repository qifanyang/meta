package com.meta.core.field;

import com.meta.core.MetaDefinition;

import java.util.Map;

public interface FieldDefinition<T> extends MetaDefinition<T> {

    String getModelId();

    void setModelId(String modelId);

    String getFieldType();

    void setFieldType(String fieldType);

    Boolean getRequired();

    void setRequired(Boolean required);

    String getDefaultValue();

    void setDefaultValue(String defaultValue);

    String getExpression();

    void setExpression(String expression);

    Map<String, Object> getOptions();

    void setOptions(Map<String, Object> options) ;

    String getOptionsUrl();

    void setOptionsUrl(String optionsUrl) ;

    Map<String, Object> getValidation();

    void setValidation(Map<String, Object> validation);

    Map<String, Object> getInteractivity();

    void setInteractivity(Map<String, Object> interactivity);

    Map<String, Object> getLayout();

    void setLayout(Map<String, Object> layout);

    Map<String, Object> getStyles();

    void setStyles(Map<String, Object> styles) ;
}
