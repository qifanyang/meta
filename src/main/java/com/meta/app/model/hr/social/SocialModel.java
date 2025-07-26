package com.meta.app.model.hr.social;

import com.meta.core.field.FieldBean;
import com.meta.core.field.FieldType;
import com.meta.core.model.ModelBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component(SocialModel.CODE)
public class SocialModel extends ModelBean {
    public static final String CODE = "meta_social";

    public SocialModel(){
        setCode(CODE);
        setName("社保");
    }

    @Override
    public List<FieldBean> getPresetFields() {
        List<FieldBean> presetFields = new ArrayList<>();
        presetFields.add(FieldBean.of("pi", "养老保险", FieldType.NUMBER_DECIMAL.name(), "88"));
        presetFields.add(FieldBean.of("fund", "公积金", FieldType.NUMBER_DECIMAL.name(), "99"));
        copy2Field(presetFields);
        return presetFields;
    }
}
