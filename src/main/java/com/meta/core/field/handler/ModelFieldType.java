package com.meta.core.field.handler;

import com.meta.core.field.ConversionContext;
import com.meta.core.field.FieldBean;
import com.meta.core.field.FieldType;
import com.meta.core.field.FieldTypeHandler;

import java.util.Map;
import java.util.Objects;

public class ModelFieldType implements FieldTypeHandler<Object> {
    @Override
    public String getTypeId(FieldBean fieldBean) {
        return FieldType.MODEL.name();
    }

    @Override
    public Class<?> getStorageType(FieldBean fieldBean) {
        return FieldType.MODEL.getDataType().getClass();
    }

    @Override
    public Object parseFromString(String input, FieldBean fieldBean) {
        return input;
    }

    @Override
    public Object parseFromUntyped(Object rawValue, FieldBean fieldBean, ConversionContext context) throws FieldDataException {
        return rawValue;
    }

    @Override
    public String formatForDisplay(Object value, FieldBean fieldBean, ConversionContext context) {
        return Objects.toString(value, "");
    }

    @Override
    public boolean validate(Object value, Map<String, Object> metadata) {
        return false;
    }

    @Override
    public Object getDefaultValue() {
        return null;
    }
}
