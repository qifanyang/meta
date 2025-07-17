package com.meta.app;

import com.meta.app.model.foo.FooModel;
import com.meta.core.FieldBean;

import java.util.List;

public class Demo {

    public static void main(String[] args) {

        //创建model实例, 可以使用单例, 动态元数据涉访问持久层, 可以使用cache提高性能
        FooModel fooModel = new FooModel();

        //获取model字段列表
        List<FieldBean> meta = fooModel.meta();

        //保存model字段列表值

        //查询model-根据字段列表

        //基于model完成业务操作
    }
}
