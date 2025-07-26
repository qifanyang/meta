package com.meta.app.model.hr.person;

import com.meta.core.field.FieldBean;
import com.meta.core.field.FieldType;
import com.meta.core.model.ModelBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component(PersonModel.CODE)
public class PersonModel extends ModelBean {

    public static final String CODE = "meta_person";

    public PersonModel(){
        setCode(CODE);
        setName("人员花名册");
    }

    @Override
    public List<FieldBean> getPresetFields() {
        List<FieldBean> presetFields = new ArrayList<>();
        presetFields.add(FieldBean.of("name", "姓名", FieldType.TEXT.name()));
        presetFields.add(FieldBean.of("idNumber", "身份证", FieldType.TEXT.name() ));
        presetFields.add(FieldBean.of("phone", "电话", FieldType.TEXT.name()));
        copy2Field(presetFields);
        return presetFields;
    }
}
