package com.meta.app.model.hr.person;

import com.meta.core.entity.FieldEntity;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("PersonFieldEntity")
public class PersonFieldEntity extends FieldEntity {

    @Column
    private Integer age;

    @Column
    private String homeAddress;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }
}
