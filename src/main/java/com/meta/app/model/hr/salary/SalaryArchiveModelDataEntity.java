package com.meta.app.model.hr.salary;

import com.meta.core.entity.ModelDataEntity;
import com.meta.core.field.FieldBean;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = SalaryArchiveModelDataEntity.TABLE_NAME)
public class SalaryArchiveModelDataEntity extends ModelDataEntity {
    public static final String TABLE_NAME = "salary_archive";

    @Column
    private BigDecimal wage;

    @Override
    public void updateSpecificFieldValue(FieldBean fieldBean, Object fieldValue) {
        if (fieldBean.getCode().equals("wage")){
            setWage((BigDecimal) fieldValue);
        }
    }

    public BigDecimal getWage() {
        return wage;
    }

    public void setWage(BigDecimal wage) {
        this.wage = wage;
    }
}
