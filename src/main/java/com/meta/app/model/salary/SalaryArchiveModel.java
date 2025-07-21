package com.meta.app.model.salary;

import com.meta.core.FieldType;
import com.meta.core.MetaFieldManager;
import com.meta.core.entity.FieldEntity;
import com.meta.core.field.FieldBean;
import com.meta.core.model.ModelBean;
import com.meta.util.IdGenerator;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component(SalaryArchiveModel.CODE)
public class SalaryArchiveModel extends ModelBean {

    @Autowired
    private MetaFieldManager fieldManager;

    public static final String CODE = "meta_salary_archive";

    public SalaryArchiveModel(){
        setCode(CODE);
        setName("薪酬档案");
        setId(IdGenerator.nextId());
        //新建的时候需要添加预置字段, 从数据库加载的时候追加新增的预置字段
//        List<FieldBean> fields = getFields();
//        Map<String, FieldBean> fieldBeanMap = fields.stream().collect(Collectors.toMap(FieldBean::getId, Function.identity(), (existing, replacement) -> existing));
//        for (FieldBean presetField : getPresetFields()) {
//            if (fieldBeanMap.containsKey(presetField.getCode())){
//                addField(presetField);
//            }
//        }
    }

    @Override
    public boolean isPreset() {
        return true;
    }

    @Override
    public List<FieldBean> getFields() {
        return FieldBean.of(fieldManager.fieldList(CODE, FieldEntity.class));
    }

    @Override
    public List<FieldBean> getPresetFields() {
        List<FieldBean> presetFields = new ArrayList<>();
        presetFields.add(FieldBean.of("age", "年龄", FieldType.INTEGER.name()));
        presetFields.add(FieldBean.of("age", "年龄", FieldType.INTEGER.name()));
        for (FieldBean presetField : presetFields) {
            presetField.setModelId(getId());
            presetField.setModelCode(getCode());
        }
        return presetFields;
    }
}
