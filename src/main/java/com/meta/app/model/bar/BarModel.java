package com.meta.app.model.bar;

import com.meta.core.Field;
import com.meta.core.Model;

import java.util.List;

public class BarModel extends Model<BarData> {

    @Override
    public List<Field> meta() {
        //TODO
        return super.meta();
    }

    public static void main(String[] args) {
        BarModel barModel = new BarModel();
        List<Field> meta = barModel.meta();
        BarData run = barModel.run(null);
    }
}
