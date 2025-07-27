package com.meta.util;

import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.CodeVisitorSupport;

import java.util.*;

public class GroovyAstExtractor {

    public static void main(String[] args) {
        String scriptText = """
            def a = 10
            b = 20
            
            def calculateSum(x, y) {
                return x + y + a // 访问外部变量a
            }
            
            def processData() {
                def temp = calculateSum(b, 5) // 调用函数，访问b
                println "Result: ${temp}"
                printMessage("Hello from Groovy") // 调用另一个函数
            }
            
            processData()
            
            // 外部函数调用，通常在低代码中是内置函数
            SUM(1, 2)
            IF(temp_var > 0, other_var, 0)
            """;

        System.out.println(parseVariables(scriptText));;
    }

    /**
     * 返回表达式中的变量, 包含被调用函数的参数
     * @param scriptText
     * @return
     */
    public static List<String> parseVariables(String scriptText) {
        if (scriptText == null || scriptText.isBlank()){
            return Collections.emptyList();
        }
        Set<String> declaredVariables = new HashSet<>();
        Set<String> usedVariables = new HashSet<>();
        Set<String> definedFunctions = new HashSet<>();
        Set<String> calledFunctions = new HashSet<>();

        CompilerConfiguration config = new CompilerConfiguration();
        CompilationUnit compilationUnit = new CompilationUnit(config);

        SourceUnit sourceUnit = compilationUnit.addSource("myscript.groovy", scriptText);

        try {
            compilationUnit.compile(org.codehaus.groovy.control.Phases.CANONICALIZATION); // 编译到AST生成阶段

            // 遍历所有ClassNode (对于脚本来说，通常是 Script 类)
            for (ClassNode classNode : compilationUnit.getAST().getClasses()) {
                // 查找定义的变量 (FieldNode 或 VariableExpression)
                // 脚本顶级变量可能作为 Script 类的 FieldNode
                classNode.getFields().forEach(field -> {
                    declaredVariables.add(field.getName());
                });

                // 查找定义的方法
                classNode.getMethods().forEach(method -> {
                    definedFunctions.add(method.getName());
                    // 遍历方法体中的表达式，查找变量和函数调用
                    method.getCode().visit(new CodeVisitorSupport() {
                        @Override
                        public void visitVariableExpression(VariableExpression expression) {
                            // 排除方法参数和局部变量（如果需要）
                            // 简单起见，这里都收集为 usedVariables
                            usedVariables.add(expression.getName());
                            super.visitVariableExpression(expression);
                        }

                        @Override
                        public void visitMethodCallExpression(MethodCallExpression call) {
                            if (call.getMethodAsString() != null) {
                                calledFunctions.add(call.getMethodAsString());
                            }
                            super.visitMethodCallExpression(call);
                        }
                    });
                });

                //上面已经你遍历过run方法, 不单独找Declared Variables 了
                // 遍历脚本的顶级语句，查找变量和函数调用
                // Script.run() 方法的体, 上面已经
//                if (classNode.getName().endsWith("myscript")) { // 脚本通常编译成一个匿名内部类
//                    if (classNode.getMethods("run") != null && !classNode.getMethods("run").isEmpty()) {
//                        classNode.getMethods("run").get(0).getCode().visit(new CodeVisitorSupport() {
//                            @Override
//                            public void visitVariableExpression(VariableExpression expression) {
//                                declaredVariables.add(expression.getName());
//                                super.visitVariableExpression(expression);
//                            }
//
//                            @Override
//                            public void visitMethodCallExpression(MethodCallExpression call) {
//                                if (call.getMethodAsString() != null) {
//                                    calledFunctions.add(call.getMethodAsString());
//                                }
//                                super.visitMethodCallExpression(call);
//                            }
//                        });
//                    }
//                }
            }

            System.out.println("--- AST Analysis Results ---");
            System.out.println("Declared Variables (Top-level/Field-like): " + declaredVariables);
            System.out.println("Used Variables (including arguments, local & top-level): " + usedVariables);
            System.out.println("Defined Functions: " + definedFunctions);
            System.out.println("Called Functions: " + calledFunctions);

            usedVariables.remove("args");//main方法参数
            usedVariables.remove("this");
            return new LinkedList<>(usedVariables);
        }catch (Exception e){
            throw new IllegalArgumentException("解析脚本参数异常, script = " + scriptText, e);
        }
    }
}
