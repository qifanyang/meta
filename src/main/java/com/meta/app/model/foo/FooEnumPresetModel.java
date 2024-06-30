package com.meta.app.model.foo;

import com.meta.core.EnumerableMeta;
import com.meta.core.Field;
import com.meta.core.Meta;
import com.meta.core.PresetMeta;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * enum 枚举定义预置元数据
 */
public enum FooEnumPresetModel  implements PresetMeta<Field>{
    NAME,
    AGE
    ;

    @Override
    public Field meta() {
        Field field = new Field();
        field.setName(this.name());
        return field;
    }

    /**
     * values是静态方法, 枚举也没法继承非接口, 返回
     * @return
     */
    public static List<Field> list(){
        return Arrays.asList(values()).stream().map(Meta::meta).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        Enum en = FooEnumPresetModel.AGE;
        List<Field> list = FooEnumPresetModel.list();
        System.out.println(list);

    }

}
