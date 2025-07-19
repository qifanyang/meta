package com.meta.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class JSONUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 将 Map 转换为指定类型的对象。
     *
     * @param map 包含对象属性的 Map。
     * @param c 要转换成的对象的 Class 类型。
     * @param <T> 对象的泛型类型。
     * @return 转换后的对象。
     * @throws IllegalArgumentException 如果转换失败。
     */
    public static <T> T mapToBean(Map<String, Object> map, Class<T> c) {
        // 使用 ObjectMapper 的 convertValue 方法进行转换
        return objectMapper.convertValue(map, c);
    }

    public static <T> T objToBean(Object obj, Class<T> c) {
        // 使用 ObjectMapper 的 convertValue 方法进行转换
        return objectMapper.convertValue(obj, c);
    }

    public static String toJson(Object obj){
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("JSON.toString异常", e);
        }
    }

    public static <T> T parseJson(String json, Class<T> c){
        try {
            return objectMapper.readValue(json, c);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("JSON.parseJson异常", e);
        }
    }
}
