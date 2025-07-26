package com.meta.app.model.hr.attendance;

import com.meta.core.field.FieldBean;
import com.meta.core.field.FieldType;
import com.meta.core.model.ModelBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component(AttendanceModel.CODE)
public class AttendanceModel extends ModelBean {

    public static final String CODE = "meta_attendance";

    public AttendanceModel(){
        setCode(CODE);
        setName("考勤");
    }

    @Override
    public List<FieldBean> getPresetFields() {
        List<FieldBean> presetFields = new ArrayList<>();
        presetFields.add(FieldBean.of("workDays", "工作天数", FieldType.NUMBER_DECIMAL.name()));
        presetFields.add(FieldBean.of("leaveDays", "请假天数", FieldType.NUMBER_DECIMAL.name()));
        copy2Field(presetFields);
        return presetFields;
    }
}
