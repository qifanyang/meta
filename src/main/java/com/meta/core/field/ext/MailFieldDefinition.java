package com.meta.core.field.ext;

import com.meta.core.dto.MyDTO;
import com.meta.core.field.FieldDefinition;

public interface MailFieldDefinition<T> extends FieldDefinition<T> {
    String getSender();

    void setSender(String sender);

    String getReceiver();

    void setReceiver(String receiver);

    MyDTO getMyDto();

    void setMyDto(MyDTO myDto);


}
