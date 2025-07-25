package com.meta.app.model.hr.salary;

import com.meta.app.model.hr.attendance.AttendanceModel;
import com.meta.app.model.hr.person.PersonModel;
import com.meta.app.model.hr.social.SocialModel;
import com.meta.core.field.FieldBean;
import com.meta.core.field.FieldType;
import com.meta.core.model.ModelBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
    }

    @Override
    public List<FieldBean> getPresetFields() {
        List<FieldBean> presetFields = new ArrayList<>();
        //引入其他模型字段
        presetFields.addAll(personModel.getPresetFields());
        presetFields.addAll(socialModel.getPresetFields());
        presetFields.addAll(attendanceModel.getPresetFields());
        presetFields.addAll(salaryArchiveModel.getPresetFields());

        presetFields.add(FieldBean.of("payable", "本期收入", FieldType.NUMBER.name(), "wage - (wage/workDays)*leaveDays"));
        presetFields.add(FieldBean.of("grandPayable", "累计收入", FieldType.NUMBER.name(), "10000"));
        presetFields.add(FieldBean.of("tax", "个税", FieldType.NUMBER.name(), "grandTax * 0.1"));
        presetFields.add(FieldBean.of("realWage", "应发工资", FieldType.NUMBER.name(), "payable - pi - fund - tax"));
        copy2Field(presetFields);
        return presetFields;
    }
}
