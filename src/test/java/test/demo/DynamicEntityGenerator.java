package test.demo;

import jakarta.persistence.*;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FieldAccessor;

import java.lang.reflect.Modifier;
import java.util.UUID;

public class DynamicEntityGenerator {
    public static Class<?> generateEntityClass() throws Exception {
        String className = "com.example.dynamic.Entity_" + UUID.randomUUID().toString().replace("-", "");

        return new ByteBuddy()
                .subclass(Object.class)
                .name(className)
                .annotateType(AnnotationDescription.Builder.ofType(Entity.class).build())
                .annotateType(AnnotationDescription.Builder.ofType(Table.class)
                        .define("name", "dynamic_table")
                        .build())
                .defineField("id", Long.class, Modifier.PRIVATE)
                .annotateField(AnnotationDescription.Builder.ofType(Id.class).build())
                .annotateField(AnnotationDescription.Builder.ofType(GeneratedValue.class).define("strategy", GenerationType.IDENTITY).build())
                .defineField("name", String.class, Modifier.PRIVATE)
                .annotateField(AnnotationDescription.Builder.ofType(Column.class).define("name", "name").build())
                .defineMethod("getId", Long.class, Modifier.PUBLIC)
                .intercept(FieldAccessor.ofField("id"))
                .defineMethod("setId", void.class, Modifier.PUBLIC)
                .withParameter(Long.class, "id")
                .intercept(FieldAccessor.ofField("id"))
                .defineMethod("getName", String.class, Modifier.PUBLIC)
                .intercept(FieldAccessor.ofField("name"))
                .defineMethod("setName", void.class, Modifier.PUBLIC)
                .withParameter(String.class, "name")
                .intercept(FieldAccessor.ofField("name"))
                .make()
                .load(DynamicEntityGenerator.class.getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                .getLoaded();
    }
}
