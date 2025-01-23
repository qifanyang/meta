package com.meta.core.surpport;

public class Branch {
    private String type; // "if", "else if", "else"
    private Condition condition;
    private Action action;

    // 用于 if 或 else if 分支
    public Branch(String type, Condition condition, Action action) {
        this.type = type;
        this.condition = condition;
        this.action = action;
    }

    // 用于 else 分支
    public Branch(String type, Action action) {
        this.type = type;
        this.action = action;
        this.condition = null; // else 分支没有条件
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    @Override
    public String toString() {
        if ("else".equals(type)) {
            return "else {\n" + action + "\n}";
        } else if ("else if".equals(type)) {
            return "else if (" + condition + ") {\n" + action + "\n}";
        } else {
            return "if (" + condition + ") {\n" + action + "\n}";
        }
    }
}

