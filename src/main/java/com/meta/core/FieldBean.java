package com.meta.core;

import com.meta.core.entity.FieldEntity;
import jakarta.persistence.Transient;

import java.util.UUID;

public class FieldBean extends FieldDefinitionWrapper implements FieldDefinition {

    /**
     * 关联模型会执行
     */
    @Transient
    private Class<? extends Model> associatedModel;

    private FieldEntity fieldEntity;

    public FieldBean(FieldEntity fieldEntity){
        super(fieldEntity);
        //TODO, 持久化
        fieldEntity.setId(UUID.randomUUID().toString());
        fieldEntity.setName(getClass().getName());
        fieldEntity.setCode(getClass().getName());
        fieldEntity.setExpression("a+b");
    }

//    @Override
//    public Field meta() {
//        return this;
//    }


    public Class<? extends Model> getAssociatedModel() {
        return associatedModel;
    }

    public void setAssociatedModel(Class<? extends Model> associatedModel) {
        this.associatedModel = associatedModel;
    }

    @Override
    public FieldEntity meta() {
        return fieldEntity;
    }
}
