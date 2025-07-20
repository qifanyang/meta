package com.meta.core.field;

import com.meta.core.dto.MyDTO;
import com.meta.core.entity.MailFieldEntity;
import com.meta.util.IdGenerator;

/**
 * 扩展FieldDefinition 示例
 */
public class MailField extends FieldDefinitionWrapper<MailFieldEntity> implements MailFieldDefinition {

    public MailField(){
        this(new MailFieldEntity());
        setId(IdGenerator.nextId());
    }

    public MailField(MailFieldEntity entity) {
        super(entity);
    }

    @Override
    public String getSender() {
        return meta().getSender();
    }

    @Override
    public void setSender(String sender) {
        meta().setSender(sender);
    }

    @Override
    public String getReceiver() {
        return meta().getReceiver();
    }

    @Override
    public void setReceiver(String receiver) {
        meta().setReceiver(receiver);
    }

    @Override
    public MyDTO getMyDto() {
        return null;
    }

    @Override
    public void setMyDto(MyDTO myDto) {

    }


}
