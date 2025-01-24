package com.meta.core;

import java.util.UUID;

public class Field extends AbstractMeta<Field> {

    private FieldType fieldType;             // 字段类型
    private Object defaultValue;             // 默认值
    private boolean required;                // 是否必填
    /*** 表达式: 变量, 函数调用, 数学计算, 逻辑运算等*/
    private String expression;

    /**
     * 关联模型会执行
     */
    private Class<? extends Model> associatedModel;

    public Field(){
        //TODO, 持久化
        this.setId(UUID.randomUUID().toString());
        this.setName(getClass().getName());
        this.setCode(getClass().getName());
        this.setExpression("a+b");
    }

    @Override
    public Field meta() {
        return this;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Class<? extends Model> getAssociatedModel() {
        return associatedModel;
    }

    public void setAssociatedModel(Class<? extends Model> associatedModel) {
        this.associatedModel = associatedModel;
    }
}
