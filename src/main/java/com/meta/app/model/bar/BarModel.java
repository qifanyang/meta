package com.meta.app.model.bar;

import com.meta.app.model.bar.field.BarAField;
import com.meta.core.Field;
import com.meta.core.Model;

import java.util.List;
import java.util.Map;

public class BarModel extends Model<BarData> {

    @Override
    public List<Field> meta() {
        return List.of(new BarAField());
    }


}
