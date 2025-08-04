package com.meta.app.model.hr.salary;

import com.meta.app.model.hr.attendance.AttendanceModel;
import com.meta.app.model.hr.person.PersonModel;
import com.meta.app.model.hr.social.SocialModel;
import com.meta.core.ModelOutput;
import com.meta.core.field.FieldBean;
import com.meta.core.field.FieldType;
import com.meta.core.model.ModelBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 薪酬方案->方案对应人员生成工资表
 */
@Component(SalaryTemplateModel.CODE)
public class SalaryTemplateModel extends ModelBean {

    public static final String CODE = "meta_salary_template";

    @Autowired
    private PersonModel personModel;
    @Autowired
    private SocialModel socialModel;
    @Autowired
    private AttendanceModel attendanceModel;
    @Autowired
    private SalaryArchiveModel salaryArchiveModel;

    public SalaryTemplateModel(){
        setCode(CODE);
        setName("薪酬方案");
        setDataTable(SalaryPersonDataEntity.TABLE_NAME);
    }

    @Override
    public List<FieldBean> getPresetFields() {
        List<FieldBean> presetFields = new ArrayList<>();
        //引入其他模型字段
        presetFields.addAll(relateFields(personModel.getPresetFields()));
        presetFields.addAll(socialModel.getPresetFields());
        presetFields.addAll(attendanceModel.getPresetFields());
        presetFields.addAll(salaryArchiveModel.getPresetFields());


        List<FieldBean> fields = new ArrayList<>();
        fields.add(FieldBean.of("payable", "本期收入", FieldType.NUMBER_DECIMAL.name(), "wage - (wage/workDays)*leaveDays"));
        fields.add(FieldBean.of("grandPayable", "累计收入", FieldType.NUMBER_DECIMAL.name(), "10000"));
        fields.add(FieldBean.of("tax", "个税", FieldType.NUMBER_DECIMAL.name(), "SUM(grandTax, 88, 11)"));
        fields.add(FieldBean.of("realWage", "应发工资", FieldType.NUMBER_DECIMAL.name(), "payable - pi - fund - tax"));

//        presetFields.add(FieldBean.of("payable", "本期收入", FieldType.NUMBER_DECIMAL.name(), "wage - (wage/workDays)*leaveDays"));
//        presetFields.add(FieldBean.of("grandPayable", "累计收入", FieldType.NUMBER_DECIMAL.name(), "10000"));
//        presetFields.add(FieldBean.of("tax", "个税", FieldType.NUMBER_DECIMAL.name(), "grandTax * 0.1"));
//        presetFields.add(FieldBean.of("realWage", "应发工资", FieldType.NUMBER_DECIMAL.name(), "payable - pi - fund - tax"));

        //模型执行触发创建其它模型数据(先使用默认数据表, 一般使用单独的表)
//        FieldBean salaryTemplateDataField = FieldBean.of(SalaryTemplateDataModel.CODE, "工资表", FieldType.MODEL.name());
//        salaryTemplateDataField.setIdentityCodes(List.of("year", "month"));
//        fields.add(salaryTemplateDataField);
        FieldBean salarySummaryDataField = FieldBean.of(SalarySummaryDataModel.CODE, "工资表汇总", FieldType.MODEL.name());
        salarySummaryDataField.setIdentityCodes(List.of("year", "month"));
        fields.add(salarySummaryDataField);
        copy2Field(fields);
        presetFields.addAll(fields);
        return presetFields;
    }

    @Override
    public List<ModelOutput> getOutputs() {
        ModelOutput modelOutput = new ModelOutput();
        modelOutput.setModelCode(SalaryTemplateDataModel.CODE);
        modelOutput.setIdentityCodes(List.of("year", "month"));
        return List.of(modelOutput);
    }
}
