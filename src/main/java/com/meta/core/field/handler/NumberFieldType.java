package com.meta.core.field.handler;

import com.meta.core.field.ConversionContext;
import com.meta.core.field.FieldBean;
import com.meta.core.field.FieldType;
import com.meta.core.field.FieldTypeHandler;

import java.math.BigDecimal;
import java.util.Map;

public class NumberFieldType implements FieldTypeHandler<BigDecimal> {
    @Override
    public String getTypeId(FieldBean fieldBean) {
        return FieldType.NUMBER_DECIMAL.getTypeId(null);
    }

    @Override
    public Class<BigDecimal> getStorageType(FieldBean fieldBean) {
        return BigDecimal.class;
    }

    @Override
    public BigDecimal parseFromString(String input, FieldBean fieldBean) {
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
    public BigDecimal parseFromUntyped(Object rawValue, FieldBean fieldBean, ConversionContext context) throws FieldDataException {
        if (rawValue == null) {
            return null;
        }
        if (rawValue instanceof BigDecimal) {
            return (BigDecimal) rawValue;
        }
        if (rawValue instanceof Number) {
            return new BigDecimal(rawValue.toString());
        }
        return parseFromString(rawValue.toString(), null);
    }

    @Override
    public String formatForDisplay(Object value, FieldBean fieldBean, ConversionContext context) {
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
