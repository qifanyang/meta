package com.meta.core.entity;

import com.meta.core.field.ext.MailFieldDefinition;
import com.meta.core.dto.MyDTO;
import jakarta.persistence.*;


@Entity
@DiscriminatorValue("MailFieldEntity")
public class MailFieldEntity extends FieldEntity<MailFieldEntity> implements MailFieldDefinition<MailFieldEntity> {

    //子类需要控制新建字段, 但是可以通过pre()和post()方法放到attr中
    @Transient //使用该注解, 属性不参数jpa生命周期
    private String sender;

    @Transient
    private String receiver;

    @Transient
    private MyDTO myDto;

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

    public MyDTO getMyDto() {
        return myDto;
    }

    public void setMyDto(MyDTO myDto) {
        this.myDto = myDto;
    }

    @Override
    public MailFieldEntity meta() {
        return super.meta();
    }
}
