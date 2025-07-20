package com.meta.app.model.foo;

import com.meta.core.BaseModel;
import com.meta.core.ModelData;

import java.util.Map;

public class FooModelTest {

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        FooData fooData = BaseModel.runModel(FooModel.class, FooData.class, Map.of("a", 1, "b", 2));
        System.out.println(System.currentTimeMillis() - start);
        start = System.currentTimeMillis();
        ModelData fooData1 = BaseModel.runModel(FooModel.class, Map.of("a", 1, "b", 2));
        System.out.println(System.currentTimeMillis() - start);
        start = System.currentTimeMillis();
        ModelData fooData2 = BaseModel.runModel(FooModel.class, Map.of("a", 1, "b", 2));
        System.out.println(System.currentTimeMillis() - start);
        start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++){
            BaseModel.runModel(FooModel.class, Map.of("a", 1, "b", 2));
        }
        System.out.println(System.currentTimeMillis() - start);
        FooModel model = new FooModel();
        ModelData modelData = model.run(Map.of("a", 1, "b", 2));
        System.out.println(modelData);
    }
}
