package com.meta.app.model.foo;

import com.meta.app.model.foo.field.FooAField;
import com.meta.app.model.foo.field.FooBField;
import com.meta.app.model.foo.field.FooModelField;
import com.meta.core.field.FieldBean;
import com.meta.core.PresetMeta;
import com.meta.core.entity.FieldEntity;

import java.util.List;

/**
 * class类定义预置元数据
 */
public class FooModelPresetField implements PresetMeta<List<FieldBean>> {

    public static final FieldBean a = new FooAField(new FieldEntity());
    public static final FieldBean b = new FooBField(new FieldEntity());
    public static final FieldBean c = new FooBField(new FieldEntity());

    public FooModelPresetField(){

    }

    @Override
    public List<FieldBean> meta() {
//        return List.of(a, b, c, new FooModelField());
        return List.of(a, new FooModelField(new FieldEntity()));
    }
}
