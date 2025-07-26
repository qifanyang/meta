package com.meta.core.field;

import com.meta.core.MetaDefinition;

import java.util.List;
import java.util.Map;

public interface FieldDefinition<T> extends MetaDefinition<T> {

    String getModelId();

    void setModelId(String modelId);

    String getModelCode();

    void setModelCode(String modelCode);

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

    /**
     * 当字段fieldType为model时, 以下code组合表示唯一查询
     * 为什么不用id值, id值对业务是不可提前知道的, 但是根据年月可以查询是否有数据
     * @return
     */
    List<String> getUniqueCodes();

    void setUniqueCodes(List<String> uniqueCodes);

    List<String> getDependentVariables();

    void setDependentVariables(List<String> dependentVariables);
}
