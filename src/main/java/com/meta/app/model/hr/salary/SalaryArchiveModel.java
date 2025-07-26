package com.meta.app.model.hr.salary;

import com.meta.core.field.FieldType;
import com.meta.core.MetaFieldManager;
import com.meta.core.entity.FieldEntity;
import com.meta.core.field.FieldBean;
import com.meta.core.model.ModelBean;
import com.meta.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component(SalaryArchiveModel.CODE)
public class SalaryArchiveModel extends ModelBean {
    public static final String CODE = "meta_salary_archive";

    @Autowired
    private MetaFieldManager fieldManager;


    public SalaryArchiveModel(){
        setCode(CODE);
        setName("薪酬档案");
        setId(IdGenerator.nextId());
    }

    @Override
    public boolean isPreset() {
        return true;
    }

    @Override
    public List<FieldBean> getFields() {
        List<FieldBean> fieldBeanList = FieldBean.of(fieldManager.fieldList(CODE, FieldEntity.class));
        Map<String, FieldBean> fieldBeanMap = fieldBeanList.stream().collect(Collectors.toMap(FieldBean::getId, Function.identity(), (existing, replacement) -> existing));
        //从数据库加载的时候追加新增的预置字段
        for (FieldBean presetField : getPresetFields()) {
            if (!fieldBeanMap.containsKey(presetField.getCode())) {
                fieldBeanList.add(presetField);
            }
        }
        return fieldBeanList;
    }

    @Override
    public List<FieldBean> getPresetFields() {
        List<FieldBean> presetFields = new ArrayList<>();
        presetFields.add(FieldBean.of("wage", "基本工资", FieldType.NUMBER_DECIMAL.name(), "wage"));
        copy2Field(presetFields);
        return presetFields;
    }
}
