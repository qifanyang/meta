package com.meta.form;

import java.util.Map;

/**
 * 表单字段, 用于涉及前端展示的字段, 区别于不用于前端展示的字段Field
 * @author yangqifan
 * @date 2025/1/23
 */
public class FormField{

    private Map<String, String> label;       // 标签（多语言支持）
    private Map<String, Object> options;     // 静态选项（针对 ENUM 和 MULTI_SELECT）
    private String optionsUrl;               // 动态选项接口
    private Map<String, Object> validation;  // 校验规则
    private Map<String, Object> layout;      // 布局配置
    private Map<String, Object> interactivity;// 动态行为
    private Map<String, Object> styles;      // 样式配置
    private FormFieldType formFieldType;

    //字段间转换
}
