package com.meta.core.surpport;

import com.meta.util.JSONUtil;
import jakarta.persistence.AttributeConverter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ColumnListConverter implements AttributeConverter<List, String> {

    @Override
    public String convertToDatabaseColumn(List attribute) {
        return JSONUtil.toJson(attribute);
    }

    @Override
    public List convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return new LinkedList();
        }
        return JSONUtil.parseJson(dbData, LinkedList.class);
    }
}
