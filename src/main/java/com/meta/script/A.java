package com.meta.script;

import javax.script.*;

/**
 * @author yangqifan
 * @date 2025/1/15
 */
public class A {
    public static void main(String[] args) throws ScriptException {
        // Groovy 脚本
        String groovyScript = "def greet(name) { return 'Hello, ' + name }; greet('Groovy')";

        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("groovy");
        CompiledScript compiledScript = ((Compilable) scriptEngine).compile(groovyScript);
        Object eval = compiledScript.eval();
        System.out.println(eval);


    }
}
