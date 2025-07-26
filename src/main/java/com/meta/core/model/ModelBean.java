package com.meta.core.model;

import com.meta.core.entity.ModelDataEntity;
import com.meta.core.entity.ModelEntity;
import com.meta.core.field.FieldBean;
import com.meta.util.IdGenerator;

import java.util.ArrayList;
import java.util.List;

public class ModelBean extends ModelDefinitionWrapper<ModelEntity>{

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
}
