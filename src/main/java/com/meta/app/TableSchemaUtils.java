package com.meta.app;

import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Date;

@Component
public class TableSchemaUtils {

    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    @Autowired
    public TableSchemaUtils(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
    }

    public void createTableIfNotExists(String tableName, Map<String, String> columns) {
        StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS `" + tableName + "` (");
        sql.append("id BIGINT AUTO_INCREMENT PRIMARY KEY");
        for (Map.Entry<String, String> entry : columns.entrySet()) {
            sql.append(", `").append(entry.getKey()).append("` ").append(entry.getValue());
        }
        sql.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;");
        jdbcTemplate.execute(sql.toString());
    }

    public Map<String, String> getExistingColumnTypes(String tableName) throws SQLException {
        Map<String, String> columns = new HashMap<>();
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            try (ResultSet rs = metaData.getColumns(null, null, tableName, null)) {
                while (rs.next()) {
                    String columnName = rs.getString("COLUMN_NAME");
                    String typeName = rs.getString("TYPE_NAME").toUpperCase();
                    columns.put(columnName, typeName);
                }
            }
        }
        return columns;
    }

    public void syncEntityToTable(Class<?> entityClass, String tableName) throws SQLException {
        Map<String, String> desiredColumns = new LinkedHashMap<>();

        for (Field field : entityClass.getDeclaredFields()) {
            if (field.getName().equals("id")) continue;
            String columnName = field.getName();
            if (field.isAnnotationPresent(Column.class)) {
                Column col = field.getAnnotation(Column.class);
                if (!col.name().isEmpty()) columnName = col.name();
            }
            String columnType = mapJavaTypeToSql(field.getType());
            if (columnType != null) {
                desiredColumns.put(columnName, columnType);
            }
        }

        createTableIfNotExists(tableName, desiredColumns);

        Map<String, String> existingColumns = getExistingColumnTypes(tableName);
        for (Map.Entry<String, String> entry : desiredColumns.entrySet()) {
            String name = entry.getKey();
            String desiredType = entry.getValue().toUpperCase();
            String existingType = existingColumns.get(name);
            if (existingType == null) {
                jdbcTemplate.execute("ALTER TABLE `" + tableName + "` ADD COLUMN `" + name + "` " + desiredType);
            } else {
                String normalizedExisting = existingType.replaceAll("\\(.*?\\)", "").toUpperCase();
                String normalizedDesired = desiredType.replaceAll("\\(.*?\\)", "").toUpperCase();
                if (!normalizedExisting.equals(normalizedDesired)) {
                    System.out.println("字段类型不同：" + name + " 当前类型=" + existingType + " 期望类型=" + desiredType);
                }
            }
        }

        for (String existing : existingColumns.keySet()) {
            if (!desiredColumns.containsKey(existing) && !existing.equals("id")) {
                System.out.println("多余字段：" + existing);
                // 可选：生成 drop column 语句，但不自动执行
            }
        }
    }

    private String mapJavaTypeToSql(Class<?> type) {
        if (type == String.class) return "VARCHAR(255)";
        if (type == Integer.class || type == int.class) return "INT";
        if (type == Long.class || type == long.class) return "BIGINT";
        if (type == Boolean.class || type == boolean.class) return "TINYINT(1)";
        if (type == Double.class || type == double.class) return "DOUBLE";
        if (type == Float.class || type == float.class) return "FLOAT";
        if (type == BigDecimal.class) return "DECIMAL(18,2)";
        if (type == LocalDate.class) return "DATE";
        if (type == LocalDateTime.class) return "DATETIME";
        if (type == Date.class) return "DATETIME";
        return null;
    }
}

