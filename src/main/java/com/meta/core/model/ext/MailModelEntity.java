package com.meta.core.model.ext;

import com.meta.core.entity.ModelEntity;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;

@Entity
@DiscriminatorValue("MailModelEntity")
public class MailModelEntity extends ModelEntity implements MailModelDefinition {

    @Transient
    private String smtp;

    @Override
    public String getSmtp() {
        return smtp;
    }

    @Override
    public void setSmtp(String smtp) {
        this.smtp = smtp;
    }
}
