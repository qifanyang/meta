package com.meta.form;

public abstract class AbstractFormControl implements FormControl{

    public AbstractFormControl(){
        registryControl(this);
    }

    @Override
    public FormField formField() {
        return null;
    }
}
