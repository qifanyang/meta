package com.meta.app.model.hr.person;

import com.meta.core.entity.ModelDataEntity;
import com.meta.core.field.FieldBean;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = PersonModelDataEntity.TABLE_NAME)
public class PersonModelDataEntity extends ModelDataEntity {
    public static final String TABLE_NAME = "person_data";

    @Column
    private String name;
    @Column(name = "id_number")
    private String idNumber;
    @Column
    private String phone;

    @Override
    public void updateSpecificFieldValue(FieldBean fieldBean, Object fieldValue) {
        switch (fieldBean.getCode()){
            case "name" -> setName((String) fieldValue);
            case "idNumber" -> setIdNumber((String) fieldValue);
            case "phone" -> setPhone((String) fieldValue);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
