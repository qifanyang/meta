package com.meta.core.field;

import com.meta.core.BaseModel;
import com.meta.core.entity.FieldEntity;
import com.meta.util.IdGenerator;
import jakarta.persistence.Transient;

import java.util.ArrayList;
import java.util.List;

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

    public static FieldBean of(){
        return new FieldBean();
    }

    public static FieldBean of(FieldEntity fieldEntity){
        return new FieldBean(fieldEntity);
    }

    public static List<FieldBean> of(List<FieldEntity> fieldEntities){
        List<FieldBean> fieldBeans = new ArrayList<>();
        fieldEntities.forEach(entity -> fieldBeans.add(of(entity)));
        return fieldBeans;
    }

    public static FieldBean of(String code, String name, String fieldType){
        return of(code, name, fieldType, null);
    }

    public static FieldBean of(String code, String name, String fieldType, String exp){
        FieldBean fb = new FieldBean();
        fb.setCode(code);
        fb.setName(name);
        fb.setFieldType(fieldType);
        fb.setExpression(exp);
        return fb;
    }

    @Override
    public FieldEntity meta() {
        return super.meta();
    }

    public Class<? extends BaseModel> getAssociatedModel() {
        return associatedModel;
    }

    public void setAssociatedModel(Class<? extends BaseModel> associatedModel) {
        this.associatedModel = associatedModel;
    }

    public String formatToDisplay(Object value){
        return FieldType.valueOf(getFieldType()).formatForDisplay(value, null);
    }
}
