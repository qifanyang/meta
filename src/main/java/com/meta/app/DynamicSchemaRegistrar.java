package com.meta.app;

import jakarta.annotation.PostConstruct;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.tool.schema.SourceType;
import org.hibernate.tool.schema.TargetType;
import org.hibernate.tool.schema.spi.*;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Map;

public class DynamicSchemaRegistrar {

    @PostConstruct
    public void init() throws Exception {
        Class<?> dynamicEntity = DynamicEntityGenerator.generateEntityClass();

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySetting("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                .applySetting("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                .applySetting("hibernate.connection.url", "jdbc:mysql://localhost:3306/meta?useSSL=false&serverTimezone=UTC")
                .applySetting("hibernate.connection.username", "root")
                .applySetting("hibernate.connection.password", "123456")
                .applySetting("hibernate.hbm2ddl.auto", "update")
                .build();

        MetadataSources metadataSources = new MetadataSources(registry);
        metadataSources.addAnnotatedClass(dynamicEntity);
        Metadata metadata = metadataSources.buildMetadata();

        SchemaManagementTool tool = registry.getService(SchemaManagementTool.class);
        SchemaCreator schemaCreator = tool.getSchemaCreator(null);

        ExecutionOptions options = new ExecutionOptions() {
            @Override
            public boolean shouldManageNamespaces() {
                return true;
            }

            @Override
            public ExceptionHandler getExceptionHandler() {
                return null;
            }

            @Override
            public Map<String, Object> getConfigurationValues() {
                return Collections.emptyMap();
            }
        };

        schemaCreator.doCreation(
                metadata,
                options,
                ContributableMatcher.ALL,
                SourceDescriptorImpl.INSTANCE,
                TargetDescriptorImpl.INSTANCE
        );
    }

    private static class SourceDescriptorImpl implements SourceDescriptor {
        static final SourceDescriptorImpl INSTANCE = new SourceDescriptorImpl();

        @Override
        public SourceType getSourceType() {
            return SourceType.METADATA;
        }

        @Override
        public ScriptSourceInput getScriptSourceInput() {
            return null;
        }
    }

    private static class TargetDescriptorImpl implements TargetDescriptor {
        static final TargetDescriptorImpl INSTANCE = new TargetDescriptorImpl();

        @Override
        public EnumSet<TargetType> getTargetTypes() {
            return EnumSet.of(TargetType.DATABASE);
        }

        @Override
        public ScriptTargetOutput getScriptTargetOutput() {
            return null;
        }
    }
}





