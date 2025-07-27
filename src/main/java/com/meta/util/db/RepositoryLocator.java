package com.meta.util.db;

import com.meta.core.entity.ModelDataEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RepositoryLocator {

    private final ApplicationContext applicationContext;
    private final Repositories repositories;

    public RepositoryLocator(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.repositories = new Repositories(applicationContext);
    }

    @SuppressWarnings("unchecked")
    public <T extends ModelDataEntity, ID> JpaRepository<T, ID> getRepository(Class<T> entityClass) {
        Optional<Object> repository = repositories.getRepositoryFor(entityClass);
        return (JpaRepository<T, ID>) repository.orElseThrow(
                () -> new IllegalArgumentException("No JpaRepository found for entity: " + entityClass.getName()));
    }

    // 使用ResolvableType确保类型安全
    public <T extends ModelDataEntity, ID> JpaRepository<T, ID> getRepository(Class<T> entityClass, Class<ID> idClass) {
        Optional<Object> repository = repositories.getRepositoryFor(entityClass);
        if (repository.isPresent()) {
            ResolvableType type = ResolvableType.forClassWithGenerics(
                    JpaRepository.class, entityClass, idClass);
            if (type.isInstance(repository.get())) {
                return (JpaRepository<T, ID>) repository.get();
            }
        }
        throw new IllegalArgumentException("No matching repository found");
    }

    public <T extends ModelDataEntity> T save(T entity) {
        @SuppressWarnings("unchecked")
        Class<T> entityClass = (Class<T>) entity.getClass();
        JpaRepository<T, String> repository = getRepository(entityClass);
        return repository.save(entity);
    }

    public boolean hasRepositoryFor(Class<?> entityClass) {
        Repositories repositories = new Repositories(applicationContext);
        return repositories.hasRepositoryFor(entityClass);
    }
}
