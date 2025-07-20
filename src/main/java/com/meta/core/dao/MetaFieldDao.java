package com.meta.core.dao;

import com.meta.core.entity.FieldEntity;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface MetaFieldDao<T extends FieldEntity> extends JpaRepository<T, String> {

    default Class<T> getEntityType(){
        Class<?> entityClass = GenericTypeResolver.resolveTypeArgument(this.getClass(), MetaFieldDao.class);
        if (entityClass == null) {
            throw new IllegalStateException("Unable to resolve entity class for " + this.getClass().getName());
        }
        return (Class<T>) entityClass;
    }

    List<T> findByNameContaining(String keyword);

    //查询某模型下所有字段，未删除
    Page<T> findPageByModelIdAndDeletedFalse(String modelId, Pageable pageable);

    //
    Page<T> findPageByTenantId(String tenantId, Pageable pageable);
    /**
     * 不能重写, 使用自定义方法
     * org.springframework.data.jpa.repository.support.CrudMethodMetadataPostProcessor.CrudMethodMetadataPopulatingMethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
     * CrudMethodMetadata metadata = (CrudMethodMetadata) TransactionSynchronizationManager.getResource(method);
     *
     * 				if (metadata != null) {
     * 					return invocation.proceed();
     *                                }
     * 重写后这里判断metadata不为空, 方法体再调用save()方法会递归调用
     * @param entity
     * @param <S>
     * @return
     */
    default <S extends T> S saveEntity(S entity){
        //@Transient字段无法在生命周期中, 这里模拟生命周期调用下
        entity.writeMetaAttr();
        return save(entity);
    }
}