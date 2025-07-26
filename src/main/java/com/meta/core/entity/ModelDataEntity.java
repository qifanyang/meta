package com.meta.core.entity;

import com.meta.core.ColumnListConverter;
import com.meta.core.ColumnMapConverter;
import com.meta.core.field.FieldBean;
import com.meta.core.model.ModelDefinition;
import jakarta.persistence.*;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 模型运行时数据基类, 继承该类可扩展数据表
 */
@MappedSuperclass
public class ModelDataEntity extends BaseEntity {

    @Column(name = "model_id")
    private String modelId;

    @Column(name = "model_code")
    private String modelCode;

    /**
     * 模型字段+自定义等字段
     */
    @Convert(converter = ColumnListConverter.class)
    @Column(name = "fields", columnDefinition = "json")
    private List<FieldEntity> fields = new LinkedList<>();

    /**
     * 模型运行时所有原始值(固定字段值+自定义字段值)
     * key为code, value为属性值
     */
    @Convert(converter = ColumnMapConverter.class)
    @Column(name = "field_values", columnDefinition = "json")
    private Map<String, Object> fieldValues = new LinkedHashMap<>();

    /**
     * 表格展示,excel导出等
     * key为code, value为显示值
     */
    @Convert(converter = ColumnMapConverter.class)
    @Column(name = "field_displays", columnDefinition = "json")
    private Map<String, String> fieldDisplays = new LinkedHashMap<>();

    /**
     * 当前模型关联创建或更新的其它模型数据
     * key : field code
     * value: 关联模型数据实体
     */
    @Transient
    private Map<String, ModelDataEntity> relationModelData = new LinkedHashMap<>();

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public List<FieldEntity> getFields() {
        return fields;
    }

    public void setFields(List<FieldEntity> fields) {
        this.fields = fields;
    }

    public Map<String, Object> getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(Map<String, Object> fieldValues) {
        this.fieldValues = fieldValues;
    }

    public Map<String, String> getFieldDisplays() {
        return fieldDisplays;
    }

    public void setFieldDisplays(Map<String, String> fieldDisplays) {
        this.fieldDisplays = fieldDisplays;
    }

    public Map<String, ModelDataEntity> getRelationModelData() {
        return relationModelData;
    }

    public void setRelationModelData(Map<String, ModelDataEntity> relationModelData) {
        this.relationModelData = relationModelData;
    }


    //ModelData 为定义definition, 通用方法先放到这里
    public void updateSpecificFieldValue(FieldBean fieldBean, Object fieldValue){

    }
}
