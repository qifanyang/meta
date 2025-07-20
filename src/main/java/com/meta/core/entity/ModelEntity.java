package com.meta.core.entity;


import com.meta.core.model.ModelDefinition;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "meta_model")
public class ModelEntity extends MetaEntity implements ModelDefinition {

    @Comment("模型对应的数据表名,用作数据路由")
    @Column(name = "data_table")
    private String dataTable = ModelDataEntity.TABLE_NAME;

    public String getDataTable() {
        return dataTable;
    }

    public void setDataTable(String dataTable) {
        this.dataTable = dataTable;
    }

    @Override
    public Object meta() {
        return null;
    }

}
