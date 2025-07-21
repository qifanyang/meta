package com.meta.core.model;

import com.meta.core.MetaDefinition;
import com.meta.core.field.FieldBean;

import java.util.Collections;
import java.util.List;

public interface ModelDefinition extends MetaDefinition {

    String getDataTable();

    void setDataTable(String dataTable);


    default List<FieldBean> getFields(){
        return Collections.emptyList();
    }

    default List<FieldBean> getPresetFields(){
        return Collections.emptyList();
    }

    default List<FieldBean> getCustomFields(){
        return Collections.emptyList();
    }
}
