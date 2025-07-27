package com.meta.app.model.hr.salary;

import com.meta.core.field.FieldBean;
import com.meta.core.field.FieldType;
import com.meta.core.model.ModelBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component(SalaryTemplateRelationArchiveModel.CODE)
public class SalaryTemplateRelationArchiveModel extends ModelBean {
    public static final String CODE = "meta_salary_template_relation_archive";

    public SalaryTemplateRelationArchiveModel(){
        setCode(CODE);
        setName("薪酬方案人员配置");
    }

    @Override
    public List<FieldBean> getPresetFields() {
        List<FieldBean> presetFields = new ArrayList<>();
        presetFields.add(FieldBean.of("archive_id", "档案ID", FieldType.TEXT.name()));
        return presetFields;
    }
}
