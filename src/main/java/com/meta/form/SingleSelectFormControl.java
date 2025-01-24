package com.meta.form;

public class SingleSelectFormControl extends AbstractFormControl {
    @Override
    public String controlType() {
        return FormControlEnum.SINGLE_SELECT.controlType();
    }

    @Override
    public FormField formField() {
        return null;
    }
}
