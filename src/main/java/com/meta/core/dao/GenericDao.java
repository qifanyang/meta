package com.meta.core.dao;

import com.meta.core.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public class GenericDao<T extends BaseEntity>{

    @Autowired
    private JpaRepository<T, Long> repository;

    public GenericDao(JpaRepository<T, Long> repository) {
        this.repository = repository;
    }

    public void save(T entity) {
        repository.save(entity);
    }
}
