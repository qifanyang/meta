package com.meta.core.model;

import com.meta.core.BaseMetaBean;


public class ModelDefinitionWrapper<T> extends BaseMetaBean implements ModelDefinition{

    private ModelDefinition definition;

    public ModelDefinitionWrapper(ModelDefinition definition) {
        super(definition);
        if (definition == null){
            throw new IllegalArgumentException("ModelDefinition must not be null");
        }
        this.definition = definition;
    }

    public T getEntity(){
        return (T) definition;
    }

    @Override
    public String getDataTable() {
        return definition.getDataTable();
    }

    @Override
    public void setDataTable(String dataTable) {
        definition.setDataTable(dataTable);
    }



    @Override
    public T meta() {
        return (T) definition;
    }
}
