package com.meta.core;

import com.meta.core.field.FieldType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RenderDataGenerator {

    // 字段类型与控件类型的映射
    private static final Map<FieldType, ControlType> fieldControlMap = new HashMap<>();

    static {
        fieldControlMap.put(FieldType.TEXT, ControlType.INPUT_TEXT);
//        fieldControlMap.put(FieldType.TEXTAREA, ControlType.TEXTAREA);
//        fieldControlMap.put(FieldType.NUMBER, ControlType.INPUT_NUMBER);
//        fieldControlMap.put(FieldType.BOOLEAN, ControlType.CHECKBOX);
//        fieldControlMap.put(FieldType.DATE, ControlType.DATE_PICKER);
//        fieldControlMap.put(FieldType.ENUM, ControlType.RADIO_GROUP);
//        fieldControlMap.put(FieldType.SINGLE_SELECT, ControlType.CHECKBOX_GROUP);
//        fieldControlMap.put(FieldType.MULTI_SELECT, ControlType.CHECKBOX_GROUP);
//        fieldControlMap.put(FieldType.FILE, ControlType.FILE_UPLOAD);
//        fieldControlMap.put(FieldType.IMAGE, ControlType.IMAGE_UPLOAD);
//        fieldControlMap.put(FieldType.CUSTOM, ControlType.CUSTOM_COMPONENT);
    }

    // 生成渲染数据的方法
    public static Map<String, Object> generateRenderData(FieldDefinition11 field) {
        Map<String, Object> renderData = new HashMap<>();
        renderData.put("fieldName", field.getFieldName());
        renderData.put("label", field.getLabel());
        renderData.put("controlType", fieldControlMap.getOrDefault(field.getFieldType(), ControlType.CUSTOM_COMPONENT).name());
        renderData.put("required", field.isRequired());
        renderData.put("defaultValue", field.getDefaultValue());
        renderData.put("options", field.getOptions());
        renderData.put("optionsUrl", field.getOptionsUrl());
        renderData.put("validation", field.getValidation());
        renderData.put("layout", field.getLayout());
        renderData.put("interactivity", field.getInteractivity());
        renderData.put("styles", field.getStyles());
        return renderData;
    }

    // 示例生成多个字段的渲染数据
    public static List<Map<String, Object>> generateRenderData(List<FieldDefinition11> fields) {
        List<Map<String, Object>> renderDataList = new ArrayList<>();
        for (FieldDefinition11 field : fields) {
            renderDataList.add(generateRenderData(field));
        }
        return renderDataList;
    }

    public static void main(String[] args) {
        // 创建字段定义
        List<FieldDefinition11> fields = new ArrayList<>();

        // 示例字段：用户名
        fields.add(new FieldDefinition11(
                "username",
                Map.of("en", "User Name", "zh", "用户名"),
                FieldType.TEXT,
                true,
                "",
                null,
                null,
                Map.of("minLength", 3, "maxLength", 20, "regex", "^[A-Za-z0-9]+$"),
                Map.of("colSpan", 2, "hidden", false),
                Map.of("onChange", "validateUsername()"),
                Map.of("style", Map.of("color", "blue"))
        ));

        // 示例字段：性别选择
        fields.add(new FieldDefinition11(
                "gender",
                Map.of("en", "Gender", "zh", "性别"),
                FieldType.RADIO,
                true,
                "male",
                Map.of(
                        "options", List.of(
                                Map.of("label", "Male", "value", "male"),
                                Map.of("label", "Female", "value", "female")
                        )
                ),
                null,
                null,
                Map.of("colSpan", 1),
                null,
                null
        ));

        // 生成渲染数据
        List<Map<String, Object>> renderDataList = generateRenderData(fields);

        // 输出结果
        renderDataList.forEach(System.out::println);
    }
}
