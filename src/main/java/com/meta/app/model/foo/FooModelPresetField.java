package com.meta.app.model.foo;

import com.meta.app.model.bar.BarModel;
import com.meta.app.model.foo.field.FooAField;
import com.meta.app.model.foo.field.FooBField;
import com.meta.app.model.foo.field.FooModelField;
import com.meta.core.Field;
import com.meta.core.PresetMeta;

import java.util.List;

/**
 * class类定义预置元数据
 */
public class FooModelPresetField implements PresetMeta<List<Field>> {

    public static final Field a = new FooAField();
    public static final Field b = new FooBField();
    public static final Field c = new FooBField();

    public FooModelPresetField(){

    }

    @Override
    public List<Field> meta() {
//        return List.of(a, b, c, new FooModelField());
        return List.of(a, new FooModelField());
    }
}
