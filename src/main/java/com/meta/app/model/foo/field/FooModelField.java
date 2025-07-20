package com.meta.app.model.foo.field;

import com.meta.app.model.bar.BarModel;
import com.meta.core.field.FieldBean;
import com.meta.core.entity.FieldEntity;

public class FooModelField extends FieldBean {

    public FooModelField(FieldEntity f){
        super(f);
        setExpression(null);
        setAssociatedModel(BarModel.class);
        setCode("BarModel");
    }
}
