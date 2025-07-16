package com.meta.core.dao;

import com.meta.core.entity.FieldEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FieldDao extends JpaRepository<FieldEntity, String> {
}
