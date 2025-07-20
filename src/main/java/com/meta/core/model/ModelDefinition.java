package com.meta.core.model;

import com.meta.core.MetaDefinition;

public interface ModelDefinition extends MetaDefinition {

    String getDataTable();

    void setDataTable(String dataTable);
}
