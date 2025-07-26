package com.meta.app.model.hr.salary;

import com.meta.core.field.FieldBean;
import com.meta.core.field.FieldType;
import com.meta.core.model.ModelBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component(SalarySummaryDataModel.CODE)
public class SalarySummaryDataModel extends ModelBean {
    public static final String CODE = "meta_salary_summary";

    public SalarySummaryDataModel(){
        setCode(CODE);
        setName("工资表汇总");
        setDataTable(SalarySummaryDataModelDataEntity.TABLE_NAME);
    }

    @Override
    public List<FieldBean> getPresetFields() {
        List<FieldBean> presetFields = new ArrayList<>();
        presetFields.add(FieldBean.of("incomeType", "收入类型", FieldType.NUMBER_DECIMAL.name()));
        presetFields.add(FieldBean.of("taxAgent", "扣缴义务人", FieldType.TEXT.name()));
        presetFields.add(FieldBean.of("year", "年", FieldType.NUMBER_DECIMAL.name()));
        presetFields.add(FieldBean.of("month", "月", FieldType.NUMBER_DECIMAL.name()));
        presetFields.add(FieldBean.of("personCount", "算薪人数", FieldType.NUMBER_DECIMAL.name()));
        presetFields.add(FieldBean.of("payableTotal", "本期收入合计", FieldType.NUMBER_DECIMAL.name()));
        presetFields.add(FieldBean.of("realWageTotal", "应发工资合计", FieldType.NUMBER_DECIMAL.name()));
        copy2Field(presetFields);
        return presetFields;
    }
}
