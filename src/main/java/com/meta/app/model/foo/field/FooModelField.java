package com.meta.app.model.foo.field;

import com.meta.app.model.bar.BarModel;
import com.meta.core.Field;

public class FooModelField extends Field {

    public FooModelField(){
        setExpression(null);
        setAssociatedModel(BarModel.class);
        setCode("BarModel");
    }
}
