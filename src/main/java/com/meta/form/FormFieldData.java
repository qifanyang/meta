package com.meta.form;

import java.util.Map;

/**
 * @author yangqifan
 * @date 2025/1/23
 */
public class FormFieldData {

    private Map<String, String> label;       // 标签（多语言支持）
    private boolean required;                // 是否必填
    private Object defaultValue;             // 默认值
    private Map<String, Object> options;     // 静态选项（针对 ENUM 和 MULTI_SELECT）
    private String optionsUrl;               // 动态选项接口
    private Map<String, Object> validation;  // 校验规则
    private Map<String, Object> layout;      // 布局配置
    private Map<String, Object> interactivity;// 动态行为
    private Map<String, Object> styles;      // 样式配置
}
