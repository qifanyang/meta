package com.meta.form;

/**
 * 包含固定的控件, 扩展控件需要注册
 */
public enum FormControlEnum implements FormControl{
    SINGLE_SELECT
    ;

    @Override
    public String controlType() {
        return name();
    }

    @Override
    public FormField formField() {
        return null;
    }

    @Override
    public FormFieldType formFieldType() {
        return null;
    }
}
