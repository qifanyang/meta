package com.meta.form;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 表单控件
 */
public interface FormControl extends FormFieldDefinition {

    Map<String, FormControl> registryControlMap = new ConcurrentHashMap<>();

    /**
     * 控件类型, 返回字符串不是枚举, 是因为扩展的控件未知
     * @return
     */
    String controlType();

    FormField formField();

    default void registryControl(FormControl control){
        registryControlMap.putIfAbsent(control.controlType(), control);
    }
}
