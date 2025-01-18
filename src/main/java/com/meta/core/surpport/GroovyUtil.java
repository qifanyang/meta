package com.meta.core.surpport;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.util.Map;

public class GroovyUtil {

    public static Object run(Map<String, Object> params, String script){
        Binding binding = new Binding();
        if (params != null){
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                binding.setVariable(entry.getKey(), entry.getValue());
            }
        }

        GroovyShell shell = new GroovyShell(binding);
        Object result = shell.evaluate(script);
        return result;
    }

}
