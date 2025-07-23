package com.meta.core.field;

import com.meta.core.ConversionContext;
import com.meta.core.field.handler.FieldDataException;

import java.util.Map;

public interface FieldTypeHandler<T> {


    /**
     * 获取字段类型的唯一标识符, e.g., "TEXT", "NUMBER", "SINGLE_SELECT".
     */
    String getTypeId();

    /**
     * 获取此字段类型底层存储的Java数据类型.
     * 用于数据建模和ORM映射.
     */
    Class<T> getStorageType();

    /**
     * 将来自HTTP请求的原始字符串输入解析为Java类型 T.
     * @param input 来自表单提交的字符串.
     * @return 解析后的值.
     * @throws FieldDataException 如果解析失败.
     */
//    T parseFromString(String input) throws FieldDataException;
    T parseFromString(String input);

    /**
     * 将非类型化的原始值 (例如来自Excel单元格) 解析为Java类型 T.
     * 这是数据导入的核心方法.
     * @param rawValue 原始值 (可以是 String, Double, Boolean 等).
     * @param context 转换上下文，用于需要额外信息（如选项列表）的转换.
     * @return 解析后的值.
     * @throws FieldDataException 如果解析失败.
     */
    T parseFromUntyped(Object rawValue, ConversionContext context) throws FieldDataException;

    /**
     * 将Java类型value的值格式化为用于UI显示的字符串.
     * value使用Object, 数据转换使用FieldType的DataType来转换数据
     * DataType扮演convert<Object, T>角色
     * @param value 内部存储的值.
     * @return 用于显示的字符串.
     */
    String formatForDisplay(Object value, ConversionContext context);

    /**
     * 根据字段元数据验证值的有效性.
     * @param value 要验证的值.
     * @param metadata 包含验证规则的元数据 (e.g., required, minLength).
     * @return 如果验证通过则为 true.
     */
    boolean validate(T value, Map<String, Object> metadata);

    /**
     * 获取该类型的默认值.
     */
    T getDefaultValue();

}
