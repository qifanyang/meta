package com.meta.app.model.bar;

import com.meta.app.model.bar.field.BarAField;
import com.meta.core.FieldBean;
import com.meta.core.Model;
import com.meta.core.entity.FieldEntity;

import java.util.List;

public class BarModel extends Model<BarData> {

    @Override
    public List<FieldBean> meta() {
        return List.of(new BarAField(new FieldEntity()));
    }


}
