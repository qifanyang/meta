package com.meta.util.db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Table;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EntityFinder {

    private final EntityManager entityManager;

    public EntityFinder(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Class<?>> findEntityByTableName(String tableName) {
        for (EntityType<?> entityType : entityManager.getMetamodel().getEntities()) {
            // 获取实体类的@Table注解
            Table tableAnnotation = entityType.getJavaType().getAnnotation(Table.class);
            String entityTableName = tableAnnotation != null && !tableAnnotation.name().isEmpty()
                    ? tableAnnotation.name()
                    : entityType.getName(); // 默认使用实体类名

            if (entityTableName.equalsIgnoreCase(tableName)) {
                return Optional.of(entityType.getJavaType());
            }
        }
        return Optional.empty();
    }
}
