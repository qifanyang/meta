package com.meta.app.model.bar;

import com.meta.app.model.bar.field.BarAField;
import com.meta.core.field.FieldBean;
import com.meta.core.BaseModel;
import com.meta.core.entity.FieldEntity;

import java.util.List;

public class BarModel extends BaseModel<BarData> {

    @Override
    public List<FieldBean> meta() {
        return List.of(new BarAField(new FieldEntity()));
    }


}
