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
        setDataTable(PersonModelDataEntity.TABLE_NAME);
    }

    @Override
    public List<FieldBean> getPresetFields() {
        List<FieldBean> presetFields = new ArrayList<>();
        presetFields.addAll(PersonFieldEnum.getAllFields());
        copy2Field(presetFields);
        return presetFields;
    }
}
