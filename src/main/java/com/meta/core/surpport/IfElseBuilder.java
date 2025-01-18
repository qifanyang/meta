package com.meta.core.surpport;

import java.util.ArrayList;
import java.util.List;

public class IfElseBuilder {
    private List<Branch> branches = new ArrayList<>();
    private boolean hasElseIf = false; // 标记是否已经使用 else if

    // 添加 if 分支
    public IfElseBuilder addIf(Condition condition, Action action) {
        branches.add(new Branch("if", condition, action));
        return this;
    }

    // 添加 else if 分支
    public IfElseBuilder addElseIf(Condition condition, Action action) {
        if (hasElseIf) {
            throw new IllegalStateException("只能有一个 else if 分支！");
        }
        branches.add(new Branch("else if", condition, action));
        hasElseIf = true;
        return this;
    }

    // 添加 else 分支
    public IfElseBuilder addElse(Action action) {
        branches.add(new Branch("else", action));
        return this;
    }

    // 构建最终的脚本
    public String build() {
        StringBuilder script = new StringBuilder();
        for (Branch branch : branches) {
            script.append(branch).append("\n");
        }
        return script.toString();
    }

    // 创建嵌套的 if-else
    public static IfElseBuilder createNestedIfElse() {
        return new IfElseBuilder();
    }

    public static void main(String[] args) {
        // 定义条件和操作
        Condition condition1 = new Condition("input > 10");
        Action action1 = new Action("result = 'Greater';");

        Condition condition2 = new Condition("input < 5");
        Action action2 = new Action("result = 'Smaller';");

        Action action3 = new Action("result = 'Other';");

        // 构建嵌套的 IfElse 语句
        IfElseBuilder nestedBuilder = IfElseBuilder.createNestedIfElse();
        nestedBuilder
                .addIf(new Condition("input == 7"), new Action("result = 'Equals 7';"))
                .addElse(new Action("result = 'Not 7';"));

        // 在外部的 IfElseBuilder 中，加入嵌套的 IfElseBuilder
        IfElseBuilder builder = new IfElseBuilder();
        String script = builder
                .addIf(condition1, action1)
                .addElseIf(condition2, action2)
                .addElse(new Action(nestedBuilder)) // 将嵌套的 IfElseBuilder 加入
                .build();

        System.out.println("Generated Script:");
        System.out.println(script);
    }
}

