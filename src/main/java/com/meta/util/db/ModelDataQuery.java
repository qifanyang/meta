package com.meta.util.db;

import com.meta.core.entity.ModelDataEntity;
import com.meta.core.field.FieldBean;
import com.meta.core.model.ModelBean;
import com.meta.util.AppContext;
import com.meta.util.LetterSequenceGenerator;
import jakarta.persistence.*;
import jakarta.persistence.metamodel.Attribute;
import jakarta.persistence.metamodel.EntityType;
import org.hibernate.Session;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.metamodel.mapping.EntityMappingType;
import org.hibernate.metamodel.model.domain.EntityDomainType;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 模型数据检索
 */
public class ModelDataQuery {

    private Map<String, String> modelKeyMap = new LinkedHashMap<>();
    private Map<String, Object> conditionMap = new LinkedHashMap<>();

    public String sql(){
        LetterSequenceGenerator aliasGenerator = new LetterSequenceGenerator();
        RepositoryLocator repositoryLocator = AppContext.getBean(RepositoryLocator.class);
        EntityColumnNameResolver columnNameResolver = AppContext.getBean(EntityColumnNameResolver.class);
        //// 1. 定义 SELECT 字段及其别名
        LinkedHashMap<String, String> selectFields = new LinkedHashMap<>();
        List<JoinConfig> joinConfigs = new ArrayList<>();
        boolean startJoin = false;
        String primaryTable = null;
        String primaryTableAlias = null;
        String primaryTableKey = null;
        String joinedTable;
        String joinedTableAlias;
        for (Map.Entry<String, String> modelEntry : modelKeyMap.entrySet()) {
            ModelBean modelBean = AppContext.getBean(modelEntry.getKey(), ModelBean.class);
            String dataTable = modelBean.getDataTable();
            EntityManager entityManager = AppContext.getBean(EntityManager.class);
            EntityFinder entityFinder = AppContext.getBean(EntityFinder.class);
            String alias = aliasGenerator.getNextSequence();
            List<FieldBean> fields = modelBean.getFields();
            Class<?> entityClass = entityFinder.findEntityByTableName(dataTable).orElse(null);
            Session session = entityManager.unwrap(Session.class);
            EntityType entityMapping = session.getSessionFactory()
                    .getMetamodel()  // 使用 RuntimeMetamodel 替代 MappingMetamodel
                    .entity(entityClass);

            for (FieldBean field : fields) {
                Attribute attribute = entityMapping.getAttribute(field.getCode());
                String colName = ((Field) attribute.getJavaMember()).getAnnotation(Column.class).name();
                if (colName == null || colName.isEmpty()){
                    colName = camelToSnakeCase(field.getCode());
                }
                selectFields.put(alias + "." + colName, field.getCode());
            }
            if (startJoin){
//                Map<String, Object> joinMap = new HashMap<>();
//                joinMap.put("joinType", "LEFT JOIN");
//                joinMap.put("joinedTable", dataTable);
//                joinMap.put("joinedTableAlias", alias);
//                joinMap.put("primaryTableAlias", primaryTableAlias);
//                joinMap.put("primaryTableField", "id");
//                joinMap.put("joinedTableField", modelEntry.getValue());
                JoinConfig joinConfig = new JoinConfig(primaryTable, primaryTableAlias, primaryTableKey, dataTable, alias, modelEntry.getValue(), "INNER JOIN");
                joinConfigs.add(joinConfig);
            }else{
                //主表
                primaryTable = dataTable;
                primaryTableAlias = alias;
                primaryTableKey = modelEntry.getValue();
            }
            startJoin = true;
        }

        // 3. 定义查询条件 (Map 列表)
//        List<Map<String, Object>> conditions = new ArrayList<>();
        List<QueryCondition> conditions = new ArrayList<>();
        for (Map.Entry<String, Object> conditionEntry : conditionMap.entrySet()) {
            QueryCondition queryCondition = new QueryCondition("a." + conditionEntry.getKey(), Operator.EQ, conditionEntry.getValue());
            conditions.add(queryCondition);
        }

        StringBuilder sqlBuilder = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();
        // SELECT 子句
        String selectClause = selectFields.entrySet().stream()
                .map(entry -> entry.getKey() + " AS " + entry.getValue())
                .collect(Collectors.joining(", "));
        sqlBuilder.append("SELECT ").append(selectClause).append(" ");

        // FROM 子句
        sqlBuilder.append("FROM ").append(primaryTable).append(" ").append(primaryTableAlias).append(" ");
// JOIN 子句
        if (joinConfigs != null) {
            for (JoinConfig config : joinConfigs) {
                sqlBuilder.append(config.joinType).append(" ")
                        .append(config.joinedTable).append(" ").append(config.joinedTableAlias).append(" ")
                        .append("ON ").append(config.primaryTableAlias).append(".").append(config.primaryTableField)
                        .append(" = ").append(config.joinedTableAlias).append(".").append(config.joinedTableField)
                        .append(" ");
            }
        }

        // WHERE 子句
        sqlBuilder.append("WHERE 1=1 "); // 方便拼接 AND

        if (conditions != null) {
            int paramIndex = 0;
            for (QueryCondition condition : conditions) {
                String paramName = "param" + (paramIndex++); // 动态生成参数名
                sqlBuilder.append(" AND ");

                switch (condition.operator) {
                    case EQ:
                        sqlBuilder.append(condition.fieldName).append(" = :").append(paramName);
                        parameters.put(paramName, condition.value);
                        break;
                    case LIKE:
                        sqlBuilder.append(condition.fieldName).append(" LIKE :").append(paramName);
                        parameters.put(paramName, "%" + condition.value.toString() + "%");
                        break;
                    case GT:
                        sqlBuilder.append(condition.fieldName).append(" > :").append(paramName);
                        parameters.put(paramName, condition.value);
                        break;
                    case LT:
                        sqlBuilder.append(condition.fieldName).append(" < :").append(paramName);
                        parameters.put(paramName, condition.value);
                        break;
                    case GTE:
                        sqlBuilder.append(condition.fieldName).append(" >= :").append(paramName);
                        parameters.put(paramName, condition.value);
                        break;
                    case LTE:
                        sqlBuilder.append(condition.fieldName).append(" <= :").append(paramName);
                        parameters.put(paramName, condition.value);
                        break;
                    case NEQ:
                        sqlBuilder.append(condition.fieldName).append(" != :").append(paramName);
                        parameters.put(paramName, condition.value);
                        break;
                    case IS_NULL:
                        sqlBuilder.append(condition.fieldName).append(" IS NULL");
                        // 无需参数
                        break;
                    case IS_NOT_NULL:
                        sqlBuilder.append(condition.fieldName).append(" IS NOT NULL");
                        // 无需参数
                        break;
                    default:
                        throw new UnsupportedOperationException("不支持的操作符: " + condition.operator);
                }
            }
        }

        // ORDER BY
//        if (orderBy != null && !orderBy.trim().isEmpty()) {
//            sqlBuilder.append(" ORDER BY ").append(orderBy);
//        }

        // LIMIT 和 OFFSET (分页)
//        if (limit != null && limit > 0) {
//            sqlBuilder.append(" LIMIT ").append(limit);
//        }
//        if (offset != null && offset >= 0) {
//            sqlBuilder.append(" OFFSET ").append(offset);
//        }
        String finalSql = sqlBuilder.toString();
        System.out.println("Executing Native SQL:\n" + finalSql);

        return finalSql;
    }

    /**
     * key 为join key
     * @param modelCode
     * @param joinKey
     * @return
     */
    public ModelDataQuery mainModel(String modelCode, String joinKey) {
        if (!modelKeyMap.isEmpty()) {
            throw new IllegalStateException("mainModel必须最先添加");
        }
        modelKeyMap.put(modelCode, joinKey);
        return this;
    }

    public ModelDataQuery joinModel(String modelCode, String joinKey) {
        modelKeyMap.put(modelCode, joinKey);
        return this;
    }

    public ModelDataQuery condition(String key, Object value) {
        conditionMap.put(key, value);
        return this;
    }

    /**
     * 驼峰命名转下划线命名 (JPA/Hibernate 默认策略)
     */
    private String camelToSnakeCase(String camelCaseString) {
        if (camelCaseString == null || camelCaseString.isEmpty()) {
            return camelCaseString;
        }
        return camelCaseString.replaceAll("([a-z0-9])([A-Z])", "$1_$2").toLowerCase();
    }

    // 联表配置的 Key: String (主表别名.连接字段) -> Value: Map
    // 例如: "e.departmentId" -> { "joinedTable": "departments", "joinedTableAlias": "d", "joinedTableField": "id", "joinType": "LEFT JOIN" }
    public static class JoinConfig {
        public String primaryTable; // 主表名
        public String primaryTableAlias; // 主表别名
        public String primaryTableField; // 主表连接字段

        public String joinedTable; // 被连接表名
        public String joinedTableAlias; // 被连接表别名
        public String joinedTableField; // 被连接表连接字段

        public String joinType; // "LEFT JOIN", "INNER JOIN"

        public JoinConfig(String primaryTable, String primaryTableAlias, String primaryTableField,
                          String joinedTable, String joinedTableAlias, String joinedTableField, String joinType) {
            this.primaryTable = primaryTable;
            this.primaryTableAlias = primaryTableAlias;
            this.primaryTableField = primaryTableField;
            this.joinedTable = joinedTable;
            this.joinedTableAlias = joinedTableAlias;
            this.joinedTableField = joinedTableField;
            this.joinType = joinType;
        }
    }

    // 查询条件的 Key: String (字段名，如 "e.name", "d.name") -> Value: Map
    // 例如: "e.name" -> { "operator": "LIKE", "value": "张" }
    public enum Operator {
        EQ, LIKE, GT, LT, GTE, LTE, NEQ, IS_NULL, IS_NOT_NULL
    }

    public static class QueryCondition {
        public String fieldName; // 例如 "e.name", "d.name", "p.id"
        public Operator operator;
        public Object value;     // 对于 IS_NULL/IS_NOT_NULL，此值可以为 null

        public QueryCondition(String fieldName, Operator operator, Object value) {
            this.fieldName = fieldName;
            this.operator = operator;
            this.value = value;
        }
    }

    /**
     * 构建并执行动态原生 SQL 查询。
     *
     * @param entityManager EntityManager 实例
     * @param selectFieldAliases 要查询的字段及其别名，例如 `{"e.id": "employeeId", "e.name": "employeeName", "d.name": "departmentName"}`。
     * 这些别名将用于映射结果到 Map。
     * @param fromTable 主表名
     * @param fromTableAlias 主表别名
     * @param joinConfigs 联表配置列表。每个 JoinConfig 包含被连接表信息和连接类型。
     * List<JoinConfig>，其中 JoinConfig 需要包含主表连接字段的信息 (例如 "e.department_id")。
     * 我们修改 JoinConfig 如下：
     * `primaryTableAlias`: 主表别名
     * `primaryTableField`: 主表连接字段
     * `joinedTable`: 被连接表名
     * `joinedTableAlias`: 被连接表别名
     * `joinedTableField`: 被连接表连接字段
     * `joinType`: 连接类型 ("LEFT JOIN", "INNER JOIN")
     * @param queryConditions 查询条件列表。
     * @param orderBy 排序字段 (例如 "e.id ASC")
     * @param limit 限制数量
     * @param offset 偏移量
     * @return 结果列表 (List<Map<String, Object>>)，其中 Map 的 Key 是 selectFieldAliases 中定义的别名。
     */
    public static List<Map<String, Object>> executeDynamicNativeQuery(
            EntityManager entityManager,
            LinkedHashMap<String, String> selectFieldAliases, // 使用 LinkedHashMap 保持 SELECT 字段顺序
            String fromTable,
            String fromTableAlias,
            List<Map<String, Object>> joinConfigs, // Map 形式的联表配置
            List<Map<String, Object>> queryConditions, // Map 形式的查询条件
            String orderBy,
            Integer limit,
            Integer offset) {

        StringBuilder sqlBuilder = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>(); // 存储命名参数

        // --- 1. SELECT 子句 ---
        String selectClause = selectFieldAliases.entrySet().stream()
                .map(entry -> entry.getKey() + " AS " + entry.getValue())
                .collect(Collectors.joining(", "));
        sqlBuilder.append("SELECT ").append(selectClause).append(" ");

        // --- 2. FROM 子句 ---
        sqlBuilder.append("FROM ").append(fromTable).append(" ").append(fromTableAlias).append(" ");

        // --- 3. JOIN 子句 ---
        if (joinConfigs != null) {
            for (Map<String, Object> configMap : joinConfigs) {
                String joinType = (String) configMap.get("joinType");
                String joinedTable = (String) configMap.get("joinedTable");
                String joinedTableAlias = (String) configMap.get("joinedTableAlias");
                String primaryTableAlias = (String) configMap.get("primaryTableAlias");
                String primaryTableField = (String) configMap.get("primaryTableField");
                String joinedTableField = (String) configMap.get("joinedTableField");

                sqlBuilder.append(joinType).append(" ")
                        .append(joinedTable).append(" ").append(joinedTableAlias).append(" ")
                        .append("ON ").append(primaryTableAlias).append(".").append(primaryTableField)
                        .append(" = ").append(joinedTableAlias).append(".").append(joinedTableField)
                        .append(" ");
            }
        }

        // --- 4. WHERE 子句 ---
        sqlBuilder.append("WHERE 1=1 "); // 方便拼接 AND

        if (queryConditions != null) {
            int paramIndex = 0;
            for (Map<String, Object> conditionMap : queryConditions) {
                String fieldName = (String) conditionMap.get("fieldName");
                Operator operator = Operator.valueOf((String) conditionMap.get("operator"));
                Object value = conditionMap.get("value");

                String paramName = "p" + (paramIndex++); // 动态生成命名参数
                sqlBuilder.append(" AND ");

                switch (operator) {
                    case EQ:
                        sqlBuilder.append(fieldName).append(" = :").append(paramName);
                        parameters.put(paramName, value);
                        break;
                    case LIKE:
                        sqlBuilder.append(fieldName).append(" LIKE :").append(paramName);
                        parameters.put(paramName, "%" + value.toString() + "%");
                        break;
                    case GT:
                        sqlBuilder.append(fieldName).append(" > :").append(paramName);
                        parameters.put(paramName, value);
                        break;
                    case LT:
                        sqlBuilder.append(fieldName).append(" < :").append(paramName);
                        parameters.put(paramName, value);
                        break;
                    case GTE:
                        sqlBuilder.append(fieldName).append(" >= :").append(paramName);
                        parameters.put(paramName, value);
                        break;
                    case LTE:
                        sqlBuilder.append(fieldName).append(" <= :").append(paramName);
                        parameters.put(paramName, value);
                        break;
                    case NEQ:
                        sqlBuilder.append(fieldName).append(" != :").append(paramName);
                        parameters.put(paramName, value);
                        break;
                    case IS_NULL:
                        sqlBuilder.append(fieldName).append(" IS NULL");
                        break; // IS NULL 不需要参数
                    case IS_NOT_NULL:
                        sqlBuilder.append(fieldName).append(" IS NOT NULL");
                        break; // IS NOT NULL 不需要参数
                    default:
                        throw new UnsupportedOperationException("不支持的操作符: " + operator);
                }
            }
        }

        // --- 5. GROUP BY 子句 (如果需要，通常在 COUNT 或处理 JOIN 重复时) ---
        // 这里的 GROUP BY 子句应该与 selectFieldAliases 中的非聚合字段对应
        // 如果 `hasProject` 这种条件导致多行，你需要 GROUP BY 所有 select 的字段来去重
        // 简单起见，这里假设 SELECT 字段是主键和非聚合字段，或者手动指定 GROUP BY
        // 例如：
        String groupByClause = selectFieldAliases.values().stream()
                .map(alias -> {
                    // 找到对应的原始字段名，因为 GROUP BY 应该使用原始字段名
                    return selectFieldAliases.entrySet().stream()
                            .filter(entry -> entry.getValue().equals(alias))
                            .map(Map.Entry::getKey)
                            .findFirst().orElseThrow(() -> new IllegalStateException("Alias not found in selectFieldAliases map: " + alias));
                })
                .collect(Collectors.joining(", "));
        if (!groupByClause.isEmpty()) {
            sqlBuilder.append(" GROUP BY ").append(groupByClause).append(" ");
        }


        // --- 6. ORDER BY 子句 ---
        if (orderBy != null && !orderBy.trim().isEmpty()) {
            sqlBuilder.append(" ORDER BY ").append(orderBy).append(" ");
        }

        // --- 7. LIMIT 和 OFFSET (分页) ---
        if (limit != null && limit > 0) {
            sqlBuilder.append(" LIMIT ").append(limit);
        }
        if (offset != null && offset >= 0) {
            sqlBuilder.append(" OFFSET ").append(offset);
        }

        String finalSql = sqlBuilder.toString();
        System.out.println("Generated Native SQL:\n" + finalSql);

        // 创建 Query，返回 Tuple 结果集
        Query query = entityManager.createNativeQuery(finalSql, Tuple.class);

        // 绑定参数
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        // 执行查询并处理结果
        List<Tuple> resultTuples = query.getResultList();
        return resultTuples.stream().map(tuple -> {
            Map<String, Object> row = new LinkedHashMap<>();
            for (TupleElement<?> element : tuple.getElements()) {
                // 使用别名作为 Map 的 Key
                row.put(element.getAlias(), tuple.get(element));
            }
            return row;
        }).collect(Collectors.toList());
    }

    /**
     * 构建并执行动态原生 SQL 的 COUNT 查询。
     * @param entityManager EntityManager 实例
     * @param fromTable 主表名
     * @param fromTableAlias 主表别名
     * @param joinConfigs 联表配置列表
     * @param queryConditions 查询条件列表
     * @return 总记录数
     */
    public static long executeDynamicNativeCountQuery(
            EntityManager entityManager,
            String fromTable,
            String fromTableAlias,
            List<Map<String, Object>> joinConfigs,
            List<Map<String, Object>> queryConditions) {

        StringBuilder sqlBuilder = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();

        sqlBuilder.append("SELECT COUNT(DISTINCT ").append(fromTableAlias).append(".id) "); // 假设主表有 id 字段

        sqlBuilder.append("FROM ").append(fromTable).append(" ").append(fromTableAlias).append(" ");

        if (joinConfigs != null) {
            for (Map<String, Object> configMap : joinConfigs) {
                String joinType = (String) configMap.get("joinType");
                String joinedTable = (String) configMap.get("joinedTable");
                String joinedTableAlias = (String) configMap.get("joinedTableAlias");
                String primaryTableAlias = (String) configMap.get("primaryTableAlias");
                String primaryTableField = (String) configMap.get("primaryTableField");
                String joinedTableField = (String) configMap.get("joinedTableField");

                sqlBuilder.append(joinType).append(" ")
                        .append(joinedTable).append(" ").append(joinedTableAlias).append(" ")
                        .append("ON ").append(primaryTableAlias).append(".").append(primaryTableField)
                        .append(" = ").append(joinedTableAlias).append(".").append(joinedTableField)
                        .append(" ");
            }
        }

        sqlBuilder.append("WHERE 1=1 ");

        if (queryConditions != null) {
            int paramIndex = 0;
            for (Map<String, Object> conditionMap : queryConditions) {
                String fieldName = (String) conditionMap.get("fieldName");
                Operator operator = Operator.valueOf((String) conditionMap.get("operator"));
                Object value = conditionMap.get("value");

                String paramName = "p" + (paramIndex++);
                sqlBuilder.append(" AND ");

                switch (operator) {
                    case EQ:
                        sqlBuilder.append(fieldName).append(" = :").append(paramName);
                        parameters.put(paramName, value);
                        break;
                    case LIKE:
                        sqlBuilder.append(fieldName).append(" LIKE :").append(paramName);
                        parameters.put(paramName, "%" + value.toString() + "%");
                        break;
                    case GT:
                        sqlBuilder.append(fieldName).append(" > :").append(paramName);
                        parameters.put(paramName, value);
                        break;
                    case LT:
                        sqlBuilder.append(fieldName).append(" < :").append(paramName);
                        parameters.put(paramName, value);
                        break;
                    case GTE:
                        sqlBuilder.append(fieldName).append(" >= :").append(paramName);
                        parameters.put(paramName, value);
                        break;
                    case LTE:
                        sqlBuilder.append(fieldName).append(" <= :").append(paramName);
                        parameters.put(paramName, value);
                        break;
                    case NEQ:
                        sqlBuilder.append(fieldName).append(" != :").append(paramName);
                        parameters.put(paramName, value);
                        break;
                    case IS_NULL:
                        sqlBuilder.append(fieldName).append(" IS NULL");
                        break;
                    case IS_NOT_NULL:
                        sqlBuilder.append(fieldName).append(" IS NOT NULL");
                        break;
                    default:
                        throw new UnsupportedOperationException("不支持的操作符: " + operator);
                }
            }
        }

        String finalCountSql = sqlBuilder.toString();
        System.out.println("Generated Native Count SQL:\n" + finalCountSql);

        Query query = entityManager.createNativeQuery(finalCountSql);

        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        return ((Number) query.getSingleResult()).longValue();
    }

}
