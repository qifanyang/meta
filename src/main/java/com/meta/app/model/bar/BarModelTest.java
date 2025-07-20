package com.meta.app.model.bar;

import com.meta.core.field.FieldBean;

import java.util.List;
import java.util.Map;

public class BarModelTest {

    public static void main(String[] args) throws Exception {
        BarModel barModel = new BarModel();
        List<FieldBean> meta = barModel.meta();
        BarData run = barModel.run(Map.of("a", 1, "b", 2));
        System.out.println(run);
    }
}
