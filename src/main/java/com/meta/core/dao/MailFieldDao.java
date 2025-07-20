package com.meta.core.dao;

import com.meta.core.entity.MailFieldEntity;

/**
 * 公用meta_field表, @DiscriminatorValue("MailFieldEntity") 默认条件
 *
 * 默认用FieldDao查询字段, 如果有mailField特定的查询逻辑放在这里, 不用污染FieldDao
 */
public interface MailFieldDao extends MetaFieldDao<MailFieldEntity> {

}
