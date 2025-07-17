package com.meta.core;

import java.util.Map;

public interface FieldDefinition extends MetaDefinition{

    public String getModelId();

    public void setModelId(String modelId);

    public String getType();

    public void setType(String type);

    public Boolean getRequired();

    public void setRequired(Boolean required);

    public String getDefaultValue();

    public void setDefaultValue(String defaultValue);

    public String getExpression();

    public void setExpression(String expression);

    public Map<String, Object> getOptions();

    public void setOptions(Map<String, Object> options) ;

    public String getOptionsUrl();

    public void setOptionsUrl(String optionsUrl) ;

    public Map<String, Object> getValidation();

    public void setValidation(Map<String, Object> validation);

    public Map<String, Object> getInteractivity();

    public void setInteractivity(Map<String, Object> interactivity);

    public Map<String, Object> getLayout();

    public void setLayout(Map<String, Object> layout);

    public Map<String, Object> getStyles();

    public void setStyles(Map<String, Object> styles) ;
}
