package com.meta.app.model.hr.salary;

import com.meta.core.entity.ModelDataEntity;
import com.meta.core.field.FieldBean;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = SalaryTemplateDataModelDataEntity.TABLE_NAME)
public class SalaryTemplateDataModelDataEntity extends ModelDataEntity {
    public static final String TABLE_NAME = "salary_template_data";

    @Column
    private BigDecimal year;
    @Column
    private BigDecimal month;
    @Column
    private BigDecimal personCount;
    @Column
    private BigDecimal payableTotal;

    @Override
    public void updateSpecificFieldValue(FieldBean fieldBean, Object fieldValue) {
        switch (fieldBean.getCode()){
            case "year" -> setYear((BigDecimal) fieldValue);
            case "month" -> setMonth((BigDecimal) fieldValue);
            case "personCount" -> setPersonCount((BigDecimal) fieldValue);
            case "payableTotal"-> setPayableTotal((BigDecimal) fieldValue);
        }
    }

    public BigDecimal getYear() {
        return year;
    }

    public void setYear(BigDecimal year) {
        this.year = year;
    }

    public BigDecimal getMonth() {
        return month;
    }

    public void setMonth(BigDecimal month) {
        this.month = month;
    }

    public BigDecimal getPersonCount() {
        return personCount;
    }

    public void setPersonCount(BigDecimal personCount) {
        this.personCount = personCount;
    }

    public BigDecimal getPayableTotal() {
        return payableTotal;
    }

    public void setPayableTotal(BigDecimal payableTotal) {
        this.payableTotal = payableTotal;
    }
}
