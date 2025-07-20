package com.meta.core.dao;

import com.meta.core.entity.ModelEntity;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface MetaModelDao<T extends ModelEntity> extends JpaRepository<T, String> {

    default Class<T> getEntityType(){
        Class<?> entityClass = GenericTypeResolver.resolveTypeArgument(this.getClass(), MetaFieldDao.class);
        if (entityClass == null) {
            throw new IllegalStateException("Unable to resolve entity class for " + this.getClass().getName());
        }
        return (Class<T>) entityClass;
    }

    default <S extends T> S saveEntity(S entity){
        //@Transient字段无法在生命周期中, 这里模拟生命周期调用下
        entity.writeMetaAttr();
        return save(entity);
    }
}
