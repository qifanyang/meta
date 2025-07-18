package com.meta.core.entity;

import com.meta.core.MailFieldDefinition;

import java.util.Objects;

public class MailFieldEntity extends FieldEntity implements MailFieldDefinition {

    private String sender;
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
//        receiver = Objects.toString(getAttr().get("receiver"), "");
    }
}
