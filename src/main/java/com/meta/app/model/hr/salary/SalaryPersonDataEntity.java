package com.meta.app.model.hr.salary;

import com.meta.core.entity.ModelDataEntity;
import com.meta.core.field.FieldBean;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = SalaryPersonDataEntity.TABLE_NAME)
public class SalaryPersonDataEntity extends ModelDataEntity {
    public static final String TABLE_NAME = "salary_person_data";

    @Column
    private String name;

    @Column
    private BigDecimal wage;

    @Column
    private BigDecimal payable;

    @Override
    public void updateSpecificFieldValue(FieldBean fieldBean, Object fieldValue) {
        if (fieldBean.getCode().equals("name")){
            setName((String) fieldValue);
        }
        if (fieldBean.getCode().equals("wage")){
            setWage((BigDecimal) fieldValue);
        }
        if (fieldBean.getCode().equals("payable")){
            setPayable((BigDecimal) fieldValue);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getWage() {
        return wage;
    }

    public void setWage(BigDecimal wage) {
        this.wage = wage;
    }

    public BigDecimal getPayable() {
        return payable;
    }

    public void setPayable(BigDecimal payable) {
        this.payable = payable;
    }
}
