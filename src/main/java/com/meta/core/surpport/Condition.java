package com.meta.core.surpport;

public class Condition {
    private String expression;

    public Condition(String expression) {
        this.expression = expression;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return expression;
    }
}

