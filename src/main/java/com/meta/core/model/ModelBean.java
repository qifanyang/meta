package com.meta.core.model;

import com.meta.core.entity.ModelEntity;
import com.meta.core.field.FieldBean;
import com.meta.util.IdGenerator;

import java.util.ArrayList;
import java.util.List;

public class ModelBean extends ModelDefinitionWrapper<ModelEntity>{

    private List<FieldBean> fields = new ArrayList<>();

    public ModelBean(){
        this(new ModelEntity());

    }

    public ModelBean(ModelEntity entity){
        super(entity);
        meta().setId(IdGenerator.nextId());
    }

    public void addField(FieldBean fieldBean) {
        fields.add(fieldBean);
    }

    public List<FieldBean> getFields() {
        return fields;
    }
}
