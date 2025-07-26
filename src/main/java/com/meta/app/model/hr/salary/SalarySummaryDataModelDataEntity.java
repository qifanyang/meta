package com.meta.app.model.hr.salary;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = SalarySummaryDataModelDataEntity.TABLE_NAME)
public class SalarySummaryDataModelDataEntity {
    public static final String TABLE_NAME = "salary_summary_data";
}
