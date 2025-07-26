package com.meta.app.model.hr.salary;

import com.meta.core.entity.ModelDataEntity;
import com.meta.core.field.FieldBean;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = SalarySummaryDataModelDataEntity.TABLE_NAME)
public class SalarySummaryDataModelDataEntity extends ModelDataEntity {
    public static final String TABLE_NAME = "salary_summary_data";

    @Column
    private BigDecimal year;
    @Column
    private BigDecimal month;
    @Column
    private BigDecimal personCount;
    @Column
    private BigDecimal payableTotal;
    @Column
    private BigDecimal incomeType;
    @Column
    private String taxAgent;

    @Override
    public void updateSpecificFieldValue(FieldBean fieldBean, Object fieldValue) {
        //字段抽出为枚举后统一处理
        //低代码动态构建则使用反射处理
        switch (fieldBean.getCode()){
            case "year" -> setYear((BigDecimal) fieldValue);
            case "month" -> setMonth((BigDecimal) fieldValue);
            case "personCount" -> setPersonCount((BigDecimal) fieldValue);
            case "payableTotal"-> setPayableTotal((BigDecimal) fieldValue);
            case "incomeType"-> setIncomeType((BigDecimal) fieldValue);
            case "taxAgent"-> setTaxAgent((String) fieldValue);
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

    public BigDecimal getIncomeType() {
        return incomeType;
    }

    public void setIncomeType(BigDecimal incomeType) {
        this.incomeType = incomeType;
    }

    public String getTaxAgent() {
        return taxAgent;
    }

    public void setTaxAgent(String taxAgent) {
        this.taxAgent = taxAgent;
    }
}
