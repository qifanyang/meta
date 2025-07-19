package com.meta.core.dao;

import com.meta.core.entity.MailFieldEntity;

/**
 * 公用meta_field表, @DiscriminatorValue("MailFieldEntity") 默认条件
 */
public interface MailFieldDao extends MetaFieldDao<MailFieldEntity> {

    @Override
    default Class<MailFieldEntity> getEntityType(){
        return MailFieldEntity.class;
    }
}
