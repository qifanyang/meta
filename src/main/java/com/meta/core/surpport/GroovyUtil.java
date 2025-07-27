package com.meta.core.surpport;

import com.meta.util.SHAUtil;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.util.GroovyScriptEngine;
import org.codehaus.groovy.jsr223.GroovyScriptEngineImpl;

import javax.script.*;
import java.io.StringReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GroovyUtil {

    private static ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
    private static GroovyScriptEngineImpl scriptEngine = (GroovyScriptEngineImpl) scriptEngineManager.getEngineByName("groovy");
    private static Map<String, CompiledScript> scriptCache = new ConcurrentHashMap<>();

    public static Object run(Map<String, Object> params, String script, String importer) throws ScriptException {
        importer = (importer == null || importer.isBlank()) ? "static import com.meta.app.model.hr.salary.MathFunctions.*;\n" : importer;
        Bindings binding = new SimpleBindings(params);
        String allScript = appendPrefix(importer, script);
        // 为本次执行创建新的 SimpleScriptContext 实例
        SimpleScriptContext scriptContext = new SimpleScriptContext();
        String scriptSHA1 = SHAUtil.SHA1(allScript);
        CompiledScript compiledScript = scriptCache.get(scriptSHA1);
        if (compiledScript == null) {
            compiledScript = scriptEngine.compile(new StringReader(allScript));
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

    private static String appendPrefix(String importer, String script) {
        if (importer == null || importer.isBlank()) {
            return script;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(importer);
        sb.append(script);
        return sb.toString();
    }

}
