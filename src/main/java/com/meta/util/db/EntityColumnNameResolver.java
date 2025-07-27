package com.meta.util.db;
import jakarta.persistence.Column;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.mapping.JpaPersistentProperty;
import org.springframework.data.mapping.context.PersistentEntities;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EntityColumnNameResolver {

    private final PersistentEntities persistentEntities;
    private final EntityManager entityManager;

    public EntityColumnNameResolver(EntityManager entityManager,
                                    MappingContext<?, ?> mappingContext) {
        this.entityManager = entityManager;
        this.persistentEntities = PersistentEntities.of(mappingContext);
    }

    public Optional<String> getColumnName(Class<?> entityClass, String propertyName) {
        return persistentEntities.getPersistentEntity(entityClass)
                .map(entity -> {
                    if (entity instanceof org.springframework.data.jpa.mapping.JpaPersistentEntity) {
                        JpaPersistentProperty property =
                                ((org.springframework.data.jpa.mapping.JpaPersistentEntity<?>) entity)
                                        .getPersistentProperty(propertyName);
                        if (property != null) {
                            return property.getName();
                        }
                    }
                    return null;
                });
    }

    // 备用方法：使用反射获取列名
    public Optional<String> getColumnNameByReflection(Class<?> entityClass, String fieldName) {
        try {
            java.lang.reflect.Field field = entityClass.getDeclaredField(fieldName);
            Column column = field.getAnnotation(Column.class);
            if (column != null && !column.name().isEmpty()) {
                return Optional.of(column.name());
            }
            return Optional.of(fieldName);
        } catch (NoSuchFieldException e) {
            return Optional.empty();
        }
    }
}