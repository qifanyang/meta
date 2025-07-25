package com.meta.core.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = GenericModelDataEntity.TABLE_NAME)
public class GenericModelDataEntity extends ModelDataEntity {

    public static final String TABLE_NAME = "meta_runtime_data";

}
