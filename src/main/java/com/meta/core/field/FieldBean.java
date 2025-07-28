package com.meta.core.field;

import com.meta.core.entity.FieldEntity;
import com.meta.core.model.ModelBean;
import com.meta.util.AppContext;
import com.meta.util.GroovyAstExtractor;
import com.meta.util.IdGenerator;

import java.util.ArrayList;
import java.util.List;

public class FieldBean<T extends FieldEntity> extends FieldDefinitionWrapper<FieldEntity> {


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
        return of(code, name, fieldType, code);
    }

    public static FieldBean of(String code, String name, String fieldType, String exp){
        FieldBean fb = new FieldBean();
        fb.setCode(code);
        fb.setName(name);
        fb.setFieldType(fieldType);
        fb.setExpression(exp);
        fb.setDependentVariables(GroovyAstExtractor.parseVariables(exp));
        return fb;
    }

    public static FieldBean of(String code, String name, String fieldType, String exp, List<String> dependentVariables){
        FieldBean fb = new FieldBean();
        fb.setCode(code);
        fb.setName(name);
        fb.setFieldType(fieldType);
        fb.setExpression(exp);
//        fb.setDependentVariables(dependentVariables);
        fb.setDependentVariables(GroovyAstExtractor.parseVariables(exp));
        return fb;
    }

    @Override
    public FieldEntity meta() {
        return super.meta();
    }

    public String formatToDisplay(Object value){
        return FieldType.valueOf(getFieldType()).formatForDisplay(value, null, null);
    }

    public Object formatToValue(Object value){
//        return FieldType.valueOf(getFieldType()).getDataType().convert(value);
        return FieldType.valueOf(getFieldType()).parseFromUntyped(value, this, null);
    }

}
