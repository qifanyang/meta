package com.meta.util;

import com.meta.core.entity.ModelDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;


@Component
public class ModelDataHelper {

    private RepositoryLocator repositoryLocator;

    public ModelDataHelper(RepositoryLocator repositoryLocator) {
        this.repositoryLocator = repositoryLocator;
    }

    /**
     * 保存实体并返回结果
     * @param entity 要保存的实体
     * @param <T> 实体类型参数
     * @return 保存后的实体
     */
    public <T extends ModelDataEntity> T saveEntity(T entity) {
        @SuppressWarnings("unchecked")
        Class<T> entityClass = (Class<T>) entity.getClass();
        JpaRepository<T, Long> repository = repositoryLocator.getRepository(entityClass);
        return repository.save(entity);
    }

    /**
     * 批量保存实体
     * @param entities 实体集合
     * @param <T> 实体类型
     * @return 保存后的实体列表
     */
    public <T extends ModelDataEntity> List<T> saveAllEntities(Iterable<T> entities) {
        if (!entities.iterator().hasNext()) {
            return Collections.emptyList();
        }

        @SuppressWarnings("unchecked")
        Class<T> entityClass = (Class<T>) entities.iterator().next().getClass();
        JpaRepository<T, Long> repository = repositoryLocator.getRepository(entityClass);
        return repository.saveAll(entities);
    }
}
