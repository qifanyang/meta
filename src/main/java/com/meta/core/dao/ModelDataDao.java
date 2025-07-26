package com.meta.core.dao;

import com.meta.core.entity.ModelDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Map;

public interface ModelDataDao extends JpaRepository<ModelDataEntity, String>, JpaSpecificationExecutor<ModelDataEntity> {
}



