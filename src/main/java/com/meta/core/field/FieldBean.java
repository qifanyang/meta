package com.meta.core.field;

import com.meta.core.BaseModel;
import com.meta.core.entity.FieldEntity;
import com.meta.util.IdGenerator;
import jakarta.persistence.Transient;

import java.util.UUID;

public class FieldBean<T extends FieldEntity> extends FieldDefinitionWrapper<FieldEntity> {

    /**
     * 关联模型会执行
     */
    @Transient
    private Class<? extends BaseModel> associatedModel;

    public FieldBean(){
        this(new FieldEntity());
        meta().setId(IdGenerator.nextId());
    }

    public FieldBean(FieldEntity fieldEntity){
        super(fieldEntity);
        meta().setId(IdGenerator.nextId());
        meta().setExpression("a+b");
    }

    public static FieldBean of(String code, String name, String fieldType){
        FieldBean fb = new FieldBean();
        fb.setCode(code);
        fb.setName(name);
        fb.setFieldType(fieldType);
        return fb;
    }


    public Class<? extends BaseModel> getAssociatedModel() {
        return associatedModel;
    }

    public void setAssociatedModel(Class<? extends BaseModel> associatedModel) {
        this.associatedModel = associatedModel;
    }
}
