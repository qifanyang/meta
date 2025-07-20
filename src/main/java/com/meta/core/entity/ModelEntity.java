package com.meta.core.entity;


import com.meta.core.model.ModelDefinition;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "meta_model")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "model_class")
@DiscriminatorValue("ModelEntity") //不指定使用类名
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
