package com.meta.core;

/**
 * 扩展FieldDefinition 示例
 */
public class MailField extends FieldDefinitionWrapper implements MailFieldDefinition {
    public MailField(MailFieldDefinition definition) {
        super(definition);
    }

    public MailFieldDefinition _getDefinition(){
        return ((MailFieldDefinition)getDefinition());
    }
    @Override
    public String getSender() {
        return _getDefinition().getSender();
    }

    @Override
    public void setSender(String sender) {
        _getDefinition().setSender(sender);
    }

    @Override
    public String getReceiver() {
        return _getDefinition().getReceiver();
    }

    @Override
    public void setReceiver(String receiver) {
        _getDefinition().setReceiver(receiver);
    }


}
