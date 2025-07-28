package com.meta.core.field.handler;

import com.meta.core.field.ConversionContext;
import com.meta.core.field.FieldBean;
import com.meta.core.field.FieldType;
import com.meta.core.field.FieldTypeHandler;
import com.meta.core.model.ModelBean;
import com.meta.util.AppContext;

import java.util.Map;

/**
 * 引用字段需要根据
 */
public class RelationFieldType implements FieldTypeHandler {
    @Override
    public String getTypeId(FieldBean fieldBean) {
        return FieldType.RELATION.getTypeId(null);
    }

    @Override
    public Class getStorageType(FieldBean fieldBean) {
        return FieldType.RELATION.getStorageType(null);
    }

    @Override
    public Object parseFromString(String input, FieldBean fieldBean) {
        return null;
    }

    @Override
    public Object parseFromUntyped(Object rawValue, FieldBean fieldBean, ConversionContext context) throws FieldDataException {
        //关联字段找到对应模型 由模型决定如何转换
        ModelBean modelBean = AppContext.getBean(fieldBean.getModelCode(), ModelBean.class);
        return modelBean.parseFromUntyped(rawValue, fieldBean, null);
    }

    @Override
    public String formatForDisplay(Object value, FieldBean fieldBean, ConversionContext context) {
        return null;
    }

    @Override
    public Object getDefaultValue() {
        return null;
    }

    @Override
    public boolean validate(Object value, Map metadata) {
        return false;
    }
}
