package com.meta.core.model;

import com.meta.core.entity.ModelEntity;
import com.meta.core.field.ConversionContext;
import com.meta.core.field.FieldBean;
import com.meta.core.field.FieldType;
import com.meta.core.field.FieldTypeHandler;
import com.meta.core.field.handler.FieldDataException;
import com.meta.util.IdGenerator;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ModelBean extends ModelDefinitionWrapper<ModelEntity> implements FieldTypeHandler {

    public ModelBean(){
        this(new ModelEntity());
    }

    public ModelBean(ModelEntity entity){
        super(entity);
        meta().setId(IdGenerator.nextId());
    }

    public List<FieldBean> getFields() {
        return getPresetFields();
    }

    protected void copy2Field(List<FieldBean> fieldList){
        for (FieldBean fieldBean : fieldList) {
            fieldBean.setModelId(getId());
            fieldBean.setModelCode(getCode());
        }
    }

    protected List<FieldBean> relateFields(List<FieldBean> fieldBeans){
        fieldBeans.forEach(i -> i.setFieldType(FieldType.RELATION.name()));
        return fieldBeans;
    }


    @Override
    public String getTypeId(FieldBean fieldBean) {
        return fieldBean.getFieldType();
    }

    @Override
    public Class getStorageType(FieldBean fieldBean) {
        return FieldType.valueOf(fieldBean.getFieldType()).getStorageType(fieldBean);
    }

    @Override
    public Object parseFromString(String input, FieldBean fieldBean) {
        return fieldBean.formatToValue(input);
    }

    @Override
    public Object parseFromUntyped(Object rawValue, FieldBean fieldBean, ConversionContext context) throws FieldDataException {
        //源模型引入字段时, 字段类型重置为relation, 这里通过field code在当前模型中寻找目标字段
        List<FieldBean> fields = getFields();
        Optional<FieldBean> optional = fields.stream().filter(i -> i.getCode().equals(fieldBean.getCode())).findAny();
        return optional.map(i -> FieldType.valueOf(i.getFieldType()).parseFromUntyped(rawValue, i, null)).orElseThrow();
    }

    @Override
    public String formatForDisplay(Object value, FieldBean fieldBean, ConversionContext context) {
        //主要是复杂数据类型让对应的模型内部自己处理, 屏蔽内部复杂度. 保持对外统一
        //比如 一个业务下拉数据, 不用关注业务含义
        List<FieldBean> fields = getFields();
        Optional<FieldBean> optional = fields.stream().filter(i -> i.getCode().equals(fieldBean.getCode())).findAny();
        return optional.map(i -> FieldType.valueOf(i.getFieldType()).formatForDisplay(value, i, null)).orElseThrow();

    }

    @Override
    public boolean validate(Object value, Map metadata) {
        return false;
    }

    @Override
    public Object getDefaultValue() {
        return null;
    }
}
