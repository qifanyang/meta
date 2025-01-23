package com.meta.app.model.bar;

import com.meta.core.IterableModel;

import java.util.Map;


public class SalaryTemplateModel extends IterableModel {

    public static void main(String[] args) {
        SalaryTemplateModel model = new SalaryTemplateModel();
        model.run(Map.of(),obj ->{}, null);
    }

}
