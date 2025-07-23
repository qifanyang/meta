package com.meta.core.field.handler;

import com.meta.core.ConversionContext;
import com.meta.core.field.FieldType;
import com.meta.core.field.FieldTypeHandler;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;

public class DateFieldType implements FieldTypeHandler<LocalDate> {
    private static final DateTimeFormatter ISO_DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public String getTypeId() {
        return FieldType.DATE.getTypeId();
    }

    @Override
    public Class<LocalDate> getStorageType() {
        return LocalDate.class;
    }

    @Override
    public LocalDate parseFromString(String input) {
        if (input == null || input.trim().isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(input, ISO_DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("无效的日期格式，期望格式: yyyy-MM-dd", e);
        }
    }

    @Override
    public LocalDate parseFromUntyped(Object rawValue, ConversionContext context) throws FieldDataException {
        if (rawValue == null) {
            return null;
        }
        if (rawValue instanceof LocalDate) {
            return (LocalDate) rawValue;
        }
        return parseFromString(String.valueOf(rawValue));
    }

    @Override
    public String formatForDisplay(Object value, ConversionContext context) {
        if (value == null){
            return "";
        }
        LocalDate date = FieldType.DATE.getDataType().convert(value);
        return ISO_DATE_FORMATTER.format(date);
    }

    @Override
    public boolean validate(LocalDate value, Map<String, Object> metadata) {
        return false;
    }

    @Override
    public LocalDate getDefaultValue() {
        return null;
    }
}
