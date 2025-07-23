package com.meta.core.field.handler;

import com.meta.core.ConversionContext;
import com.meta.core.field.FieldType;
import com.meta.core.field.FieldTypeHandler;

import java.util.Map;

public class TextFieldType implements FieldTypeHandler<String> {

    @Override
    public String getTypeId() {
        return FieldType.TEXT.getTypeId();
    }

    @Override
    public Class<String> getStorageType() {
        return String.class;
    }

    @Override
    public String parseFromString(String input) {
        return input;
    }

    @Override
    public String parseFromUntyped(Object rawValue, ConversionContext context) throws FieldDataException {
        if (rawValue == null) {
            return null;
        }
        return String.valueOf(rawValue);
    }

    @Override
    public String formatForDisplay(Object value, ConversionContext context) {
        return value == null ? "" : FieldType.TEXT.getDataType().display(value);
    }

    @Override
    public boolean validate(String value, Map<String, Object> metadata) {
        return false;
    }

    @Override
    public String getDefaultValue() {
        return null;
    }
}
