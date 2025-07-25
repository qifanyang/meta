package com.meta.core.dao;

import com.meta.core.entity.ModelDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelDataDao extends JpaRepository<ModelDataEntity, String> {
}
