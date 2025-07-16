package com.meta.core;

import java.util.*;

/**
 * 模型运行产生的数据对象基类, 包含公共属性字段
 */
public class ModelData {

    private String id;

    /**
     * 引用的model
     */
    private transient Model model;

    /**
     * 模型字段+自定义等字段
     */
    private transient List<Field> fields = new ArrayList<>();

    /**
     * 模型运行时所有原始值(固定字段值+自定义字段值)
     * key为code, value为属性值
     */
    private Map<String, Object> values = new HashMap<>();

    /**
     * 表格展示,excel导出等
     * key为code, value为显示值
     */
    private Map<String, String> displays = new HashMap<>();

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public Map<String, Object> getValues() {
        return values;
    }

    public void setValues(Map<String, Object> values) {
        this.values = values;
    }

    public Map<String, String> getDisplays() {
        return displays;
    }

    public void setDisplays(Map<String, String> displays) {
        this.displays = displays;
    }

    public void addValue(String fieldId, Object value){
        values.put(fieldId, value);
        displays.put(fieldId, Objects.toString(value, ""));
    }
}
