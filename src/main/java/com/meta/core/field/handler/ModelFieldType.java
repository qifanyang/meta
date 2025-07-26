package com.meta.core.field.handler;

import com.meta.core.ConversionContext;
import com.meta.core.field.FieldType;
import com.meta.core.field.FieldTypeHandler;

import java.util.Map;
import java.util.Objects;

public class ModelFieldType implements FieldTypeHandler<Object> {
    @Override
    public String getTypeId() {
        return FieldType.MODEL.getTypeId();
    }

    @Override
    public Class<Object> getStorageType() {
        return FieldType.MODEL.getStorageType();
    }

    @Override
    public Object parseFromString(String input) {
        return input;
    }

    @Override
    public Object parseFromUntyped(Object rawValue, ConversionContext context) throws FieldDataException {
        return rawValue;
    }

    @Override
    public String formatForDisplay(Object value, ConversionContext context) {
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
