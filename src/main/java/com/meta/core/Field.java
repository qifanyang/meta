package com.meta.core;

public class Field implements Meta<Field>{

    private String name;

    @Override
    public Field meta() {
        return null;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
