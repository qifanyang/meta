package com.meta.core.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = ModelDataEntity.TABLE_NAME)
public class ModelDataEntity extends BaseModelDataEntity {

    public static final String TABLE_NAME = "meta_runtime_data";

}
