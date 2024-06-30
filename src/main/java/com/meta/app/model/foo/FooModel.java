package com.meta.app.model.foo;

import com.meta.core.DynamicMeta;
import com.meta.core.Field;
import com.meta.core.Model;
import com.meta.core.PresetMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * 业务模型(静态元数据+动态元数据)
 */
public class FooModel extends Model {

    /**
     * 预置源可以有多个
     */
    private FooClassPresetModel presetClassMeta = new FooClassPresetModel();
//    private FooEnumPresetModel presetEnumMeta;

    /**
     * 动态源可以有多个
     */
    private DynamicMeta<List<Field>> dynamicMeta;
    private DynamicMeta<List<Field>> dynamicMeta1;
    private DynamicMeta<List<Field>> dynamicMeta2;


    @Override
    public List<Field> meta() {
        List<Field> allFields = new ArrayList<>();
        //静态字段定义
        allFields.addAll(presetClassMeta.meta());
        allFields.addAll(FooEnumPresetModel.enumMeta());

        //动态字段
        allFields.addAll(dynamicMeta.meta());
        allFields.addAll(dynamicMeta1.meta());
        allFields.addAll(dynamicMeta2.meta());
        return allFields;
    }
}
