package com.meta.core.entity;

import com.meta.core.MailFieldDefinition;
import jakarta.persistence.*;

import java.util.Objects;


@Entity
@DiscriminatorValue("MailFieldEntity")
public class MailFieldEntity extends FieldEntity implements MailFieldDefinition {

    //子类需要控制新建字段, 但是可以通过pre()和post()方法放到attr中
    @Transient //使用该注解, 属性不参数jpa生命周期
    private String sender;

    @Transient
    private String receiver;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    @Override
    public void pre() {
        super.pre();
        getAttr().put("sender", getSender());
        getAttr().put("receiver", getReceiver());
    }

    @Override
    public void post() {
        super.post();
        setSender(Objects.toString(getAttr().get("sender"), ""));
        setReceiver(Objects.toString(getAttr().get("receiver"), ""));
    }
}
