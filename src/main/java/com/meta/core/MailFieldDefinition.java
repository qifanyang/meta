package com.meta.core;

public interface MailFieldDefinition extends FieldDefinition{
    String getSender();

    void setSender(String sender);

    String getReceiver();

    void setReceiver(String receiver);
}
