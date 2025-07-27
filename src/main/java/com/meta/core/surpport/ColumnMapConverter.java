package com.meta.core.surpport;

import com.meta.util.JSONUtil;
import jakarta.persistence.AttributeConverter;

import java.util.HashMap;
import java.util.Map;

public class ColumnMapConverter implements AttributeConverter<Map, String> {

    @Override
    public String convertToDatabaseColumn(Map attribute) {
        return JSONUtil.toJson(attribute);
    }

    @Override
    public Map convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return new HashMap();
        }
        return JSONUtil.parseJson(dbData, Map.class);
    }
}
