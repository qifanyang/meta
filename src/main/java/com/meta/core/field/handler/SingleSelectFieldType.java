package com.meta.core.field.handler;

import com.meta.core.field.ConversionContext;
import com.meta.core.field.FieldBean;
import com.meta.core.field.FieldType;
import com.meta.core.field.FieldTypeHandler;

import javax.swing.text.html.Option;
import java.util.Map;
import java.util.Optional;

public class SingleSelectFieldType implements FieldTypeHandler<Integer> {
    @Override
    public String getTypeId(FieldBean fieldBean) {
        return FieldType.SINGLE_SELECT.getTypeId(null);
    }

    @Override
    public Class<Integer> getStorageType(FieldBean fieldBean) {
        return Integer.class;
    }

    @Override
    public Integer parseFromString(String input, FieldBean fieldBean) {
        // 表单直接提交ID
        if (input == null || input.trim().isEmpty()) {
            return null;
        }
        return Integer.parseInt(input);
    }

    @Override
    public Integer parseFromUntyped(Object rawValue, FieldBean fieldBean, ConversionContext context) throws FieldDataException {
        if (rawValue == null) {
            return null;
        }

        // 场景1: Excel里直接是ID (数字)
        if (rawValue instanceof Number) {
            return ((Number) rawValue).intValue();
        }

        String rawString = String.valueOf(rawValue);

        // 场景2: Excel里是ID的字符串形式
        try {
            return Integer.parseInt(rawString);
        } catch (NumberFormatException e) {
            // 这不是一个ID，进入场景3
        }

        // 场景3: Excel里是显示值 (e.g., "已启用")，需要反向查找ID
        // 我们从ConversionContext中获取选项数据
        Optional<Option> option = context.findOptionByDisplayValue(rawString);
        if (option.isPresent()) {
//            Integer integer = (Integer) option.get();
            return 1;
        }

        throw new IllegalArgumentException("无法将显示值 '" + rawString + "' 映射到任何有效的选项ID。");

    }

    @Override
    public String formatForDisplay(Object value, FieldBean fieldBean, ConversionContext context) {
        return FieldType.SINGLE_SELECT.getDataType().display(value);
    }

    @Override
    public boolean validate(Integer value, Map<String, Object> metadata) {
        return false;
    }

    @Override
    public Integer getDefaultValue() {
        return null;
    }
}
