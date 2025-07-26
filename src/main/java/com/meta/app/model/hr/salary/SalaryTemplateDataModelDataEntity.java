package com.meta.app.model.hr.salary;

import com.meta.core.entity.ModelDataEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = SalaryTemplateDataModelDataEntity.TABLE_NAME)
public class SalaryTemplateDataModelDataEntity extends ModelDataEntity {
    public static final String TABLE_NAME = "salary_template_data";
}
