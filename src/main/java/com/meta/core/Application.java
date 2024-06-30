package com.meta.core;

import java.util.List;

public class Application extends AbstractMeta<List<Module>>{

    @Override
    public List<Module> meta() {
        //静态编码
        //动态创建
        //所以需要有一张元数据表
        return null;
    }


    public static void main(String[] args) {
        Application application = new Application();
    }
}
