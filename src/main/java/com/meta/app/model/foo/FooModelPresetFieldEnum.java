package com.meta.app.model.foo;

import com.meta.core.field.FieldBean;
import com.meta.core.Meta;
import com.meta.core.PresetMeta;
import com.meta.core.entity.FieldEntity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * enum 枚举定义预置元数据
 */
public enum FooModelPresetFieldEnum implements PresetMeta<FieldBean> {
    NAME,
    AGE;

    @Override
    public FieldBean meta() {
        FieldBean field = new FieldBean(new FieldEntity());
        field.setName(this.name());
        return field;
    }

    /**
     * values是静态方法, 枚举也没法继承非接口, 返回
     *
     * @return
     */
    public static List<FieldBean> enumMeta() {
        return Arrays.asList(values()).stream().map(Meta::meta).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        Enum en = FooModelPresetFieldEnum.AGE;
        List<FieldBean> list = FooModelPresetFieldEnum.enumMeta();
        System.out.println(list);

    }

}
