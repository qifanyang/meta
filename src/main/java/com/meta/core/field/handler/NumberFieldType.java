package com.meta.core.field.handler;

import com.meta.core.ConversionContext;
import com.meta.core.field.FieldType;
import com.meta.core.field.FieldTypeHandler;

import java.math.BigDecimal;
import java.util.Map;

public class NumberFieldType implements FieldTypeHandler<BigDecimal> {
    @Override
    public String getTypeId() {
        return FieldType.NUMBER_DECIMAL.getTypeId();
    }

    @Override
    public Class<BigDecimal> getStorageType() {
        return BigDecimal.class;
    }

    @Override
    public BigDecimal parseFromString(String input) {
        if (input == null || input.trim().isEmpty()) {
            return null;
        }
        try {
            return new BigDecimal(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("无效的数字格式: " + input, e);
        }
    }

    @Override
    public BigDecimal parseFromUntyped(Object rawValue, ConversionContext context) throws FieldDataException {
        if (rawValue == null) {
            return null;
        }
        if (rawValue instanceof BigDecimal) {
            return (BigDecimal) rawValue;
        }
        if (rawValue instanceof Number) {
            return new BigDecimal(rawValue.toString());
        }
        return parseFromString(rawValue.toString());
    }

    @Override
    public String formatForDisplay(Object value, ConversionContext context) {
        if (value == null){
            return "";
        }
        return FieldType.NUMBER_DECIMAL.getDataType().display(value);
    }

    @Override
    public boolean validate(BigDecimal value, Map<String, Object> metadata) {
        return false;
    }

    @Override
    public BigDecimal getDefaultValue() {
        return BigDecimal.ZERO;
    }
}
