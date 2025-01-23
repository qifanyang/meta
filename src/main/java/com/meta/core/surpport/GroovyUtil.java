package com.meta.core.surpport;

import com.meta.util.SHAUtil;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.util.GroovyScriptEngine;
import org.codehaus.groovy.jsr223.GroovyScriptEngineImpl;

import javax.script.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GroovyUtil {

    private static ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
    private static GroovyScriptEngineImpl scriptEngine = (GroovyScriptEngineImpl) scriptEngineManager.getEngineByName("groovy");
    private static Map<String, CompiledScript> scriptCache = new ConcurrentHashMap<>();

    public static Object run(Map<String, Object> params, String script) throws ScriptException {
        Bindings binding = new SimpleBindings(params);
        String scriptSHA1 = SHAUtil.SHA1(script);
        CompiledScript compiledScript = scriptCache.get(scriptSHA1);
        if (compiledScript == null) {
            compiledScript = scriptEngine.compile(script);
            scriptCache.put(scriptSHA1, compiledScript);
        }
        return compiledScript.eval(binding);

        //下面这种性能超低
//        Binding binding = new Binding();
//        if (params != null){
//            for (Map.Entry<String, Object> entry : params.entrySet()) {
//                binding.setVariable(entry.getKey(), entry.getValue());
//            }
//        }
//
//        GroovyShell shell = new GroovyShell(binding);
//        Object result = shell.evaluate(script);
//        return result;
    }

}
