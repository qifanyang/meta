package com.meta.core.model.ext;

import com.meta.core.model.ModelDefinition;
import com.meta.core.model.ModelDefinitionWrapper;
import com.meta.util.IdGenerator;

public class MailModel extends ModelDefinitionWrapper<MailModelEntity> implements MailModelDefinition {

    public MailModel() {
        this(new MailModelEntity());
    }

    public MailModel(ModelDefinition definition) {
        super(definition);
        meta().setId(IdGenerator.nextId());
    }

    @Override
    public String getSmtp() {
        return meta().getSmtp();
    }

    @Override
    public void setSmtp(String smtp) {
        meta().setSmtp(smtp);
    }
}
