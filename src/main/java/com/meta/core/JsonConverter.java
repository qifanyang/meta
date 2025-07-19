package com.meta.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meta.util.JSONUtil;
import jakarta.persistence.AttributeConverter;

import java.util.HashMap;
import java.util.Map;

public class JsonConverter implements AttributeConverter<Map, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

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
