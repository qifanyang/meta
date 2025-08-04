package com.meta.app.model.hr.salary;

import com.meta.core.field.FieldType;
import com.meta.core.MetaFieldManager;
import com.meta.core.field.FieldBean;
import com.meta.core.model.ModelBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component(SalaryArchiveModel.CODE)
public class SalaryArchiveModel extends ModelBean {
    public static final String CODE = "meta_salary_archive";

    @Autowired
    private MetaFieldManager fieldManager;


    public SalaryArchiveModel(){
        setCode(CODE);
        setName("薪酬档案");
        setDataTable(SalaryArchiveModelDataEntity.TABLE_NAME);
    }

    @Override
    public boolean isPreset() {
        return true;
    }

//    @Override
//    public List<FieldBean> getFields() {
//        List<FieldBean> fieldBeanList = FieldBean.of(fieldManager.fieldList(CODE, FieldEntity.class));
//        Map<String, FieldBean> fieldBeanMap = fieldBeanList.stream().collect(Collectors.toMap(FieldBean::getId, Function.identity(), (existing, replacement) -> existing));
//        //从数据库加载的时候追加新增的预置字段
//        for (FieldBean presetField : getPresetFields()) {
//            if (!fieldBeanMap.containsKey(presetField.getCode())) {
//                fieldBeanList.add(presetField);
//            }
//        }
//        return fieldBeanList;
//    }

    @Override
    public List<FieldBean> getPresetFields() {
        //关联人员花名册字段, 只存id, 和关联字段元数据, 不存值
        //然后关联查询
        List<FieldBean> presetFields = new ArrayList<>();
        presetFields.add(FieldBean.of("wage", "基本工资", FieldType.NUMBER_DECIMAL.name(), "wage"));
        presetFields.add(FieldBean.of("personId", "人员ID", FieldType.TEXT.name()));
        copy2Field(presetFields);
        return presetFields;
    }
}
