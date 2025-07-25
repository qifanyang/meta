package com.meta.core.model;

import com.meta.core.entity.GenericModelDataEntity;
import com.meta.core.entity.ModelDataEntity;

/**
 * 模型执行可选参数
 */
public class ModelRunOptions {

    public static final ModelRunOptions DEFAULT = new ModelRunOptions();

    private Class<? extends ModelDataEntity> dataEntityClass = GenericModelDataEntity.class;
    private boolean copyFields = true;

    public Class<? extends ModelDataEntity> getDataEntityClass() {
        return dataEntityClass;
    }

    public void setDataEntityClass(Class<? extends ModelDataEntity> dataEntityClass) {
        this.dataEntityClass = dataEntityClass;
    }

    public boolean isCopyFields() {
        return copyFields;
    }

    public void setCopyFields(boolean copyFields) {
        this.copyFields = copyFields;
    }
}
