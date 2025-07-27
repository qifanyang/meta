package com.meta.core.surpport;

import com.meta.core.model.ModelDefinition;
import com.meta.util.SHAUtil;
import org.codehaus.groovy.jsr223.GroovyScriptEngineImpl;

import javax.script.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GroovyUtil {

    private static ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
    private static GroovyScriptEngineImpl scriptEngine = (GroovyScriptEngineImpl) scriptEngineManager.getEngineByName("groovy");
    private static Map<String, CompiledScript> scriptCache = new ConcurrentHashMap<>();

    public static Object run(Map<String, Object> params, String script) throws ScriptException {
        Bindings binding = new SimpleBindings(params);
        String finalScript = prependStaticImports((List<Class<?>>) params.get(ModelDefinition.keyBuiltInFunctions), script);
        // 为本次执行创建新的 SimpleScriptContext 实例
        String scriptSHA1 = SHAUtil.SHA1(finalScript);
        CompiledScript compiledScript = scriptCache.get(scriptSHA1);
        if (compiledScript == null) {
            compiledScript = scriptEngine.compile(finalScript);
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

    private static String prependStaticImports(List<Class<?>> builtInFunctions, String script) {
        if (builtInFunctions == null || builtInFunctions.isEmpty()){
            return script;
        }
        StringBuilder sb = new StringBuilder();
        for (Class<?> function : builtInFunctions) {
            sb.append("import static ").append(function.getName()).append(".*\n");
        }
        sb.append(script);
        return sb.toString();
    }

}
