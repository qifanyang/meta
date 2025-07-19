package com.meta.core.dao;

import com.meta.core.entity.FieldEntity;

public interface FieldDao extends MetaFieldDao<FieldEntity> {

    @Override
    default Class<FieldEntity> getEntityType(){
        return FieldEntity.class;
    }
}
