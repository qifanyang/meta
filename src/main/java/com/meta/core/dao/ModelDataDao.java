package com.meta.core.dao;

import com.meta.core.entity.BaseModelDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelDataDao extends JpaRepository<BaseModelDataEntity, String> {
}
