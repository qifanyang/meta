package com.meta.core.model.ext;

import com.meta.core.entity.BaseModelDataEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = MailModelDataEntity.TABLE_NAME)
public class MailModelDataEntity extends BaseModelDataEntity {
    public static final String TABLE_NAME = "mail_data";
}
