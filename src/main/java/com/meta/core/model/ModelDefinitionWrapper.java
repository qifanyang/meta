package com.meta.core.model;

import com.meta.core.MetaDefinitionWrapper;


public class ModelDefinitionWrapper<T> extends MetaDefinitionWrapper implements ModelDefinition{

    private ModelDefinition definition;

    public ModelDefinitionWrapper(ModelDefinition definition) {
        super(definition);
        if (definition == null){
            throw new IllegalArgumentException("ModelDefinition must not be null");
        }
        this.definition = definition;
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
