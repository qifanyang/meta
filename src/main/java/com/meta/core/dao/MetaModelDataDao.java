package com.meta.core.dao;

import com.meta.core.entity.ModelDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface MetaModelDataDao<T extends ModelDataEntity> extends JpaRepository<T, String>, JpaSpecificationExecutor<T> {
}
