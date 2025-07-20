package com.meta.core;

import com.meta.core.entity.ModelEntity;
import com.meta.core.field.FieldBean;
import com.meta.core.model.ModelDefinitionWrapper;
import com.meta.util.IdGenerator;

import java.util.ArrayList;
import java.util.List;

public class ModelBean2 extends ModelDefinitionWrapper<ModelEntity>{

    private List<FieldBean> fields = new ArrayList<>();

    public ModelBean2(){
        super(new ModelEntity());
        meta().setId(IdGenerator.nextId());
    }

    public void addField(FieldBean fieldBean) {
        fields.add(fieldBean);
    }

    public List<FieldBean> getFields() {
        return fields;
    }
}
