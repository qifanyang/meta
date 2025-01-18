package com.meta.core;

import java.util.Map;


public interface ScriptRunner {

    Object eval(Map<String, Object> bindings, String script);

    /**
     * 脚本导入的依赖库
     * @return
     */
    default String imports(){
        return "";
    }


}
