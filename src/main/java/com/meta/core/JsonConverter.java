package com.meta.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JsonConverter implements AttributeConverter<Map, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map convertToEntityAttribute(String dbData) {
        try {
            if (dbData == null){
                return new HashMap();
            }
            return objectMapper.readValue(dbData, Map.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
