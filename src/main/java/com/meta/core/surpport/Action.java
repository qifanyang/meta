package com.meta.core.surpport;

public class Action {
    private String actionCode;
    private IfElseBuilder nestedIfElse;  // 支持嵌套的 if-else 结构

    // 用于常规操作
    public Action(String actionCode) {
        this.actionCode = actionCode;
        this.nestedIfElse = null;
    }

    // 用于嵌套 if-else
    public Action(IfElseBuilder nestedIfElse) {
        this.actionCode = null;
        this.nestedIfElse = nestedIfElse;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public IfElseBuilder getNestedIfElse() {
        return nestedIfElse;
    }

    public void setNestedIfElse(IfElseBuilder nestedIfElse) {
        this.nestedIfElse = nestedIfElse;
    }

    @Override
    public String toString() {
        if (nestedIfElse != null) {
            return nestedIfElse.build(); // 返回嵌套的 if-else
        }
        return actionCode;  // 返回常规操作代码
    }
}

