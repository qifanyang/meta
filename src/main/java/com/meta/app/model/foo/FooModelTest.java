package com.meta.app.model.foo;

import com.meta.core.Model;
import com.meta.core.ModelData;

import java.util.Map;

public class FooModelTest {

    public static void main(String[] args) throws Exception {
        FooData fooData = Model.runModel(FooModel.class, FooData.class, Map.of("a", 1, "b", 2));
        FooModel model = new FooModel();
        ModelData modelData = model.run(Map.of("a", 1, "b", 2));
        System.out.println(modelData);
    }
}
