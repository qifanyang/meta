package com.meta.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class NativeQueryUtils {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 执行原生查询，返回List<Object[]>
     */
    public List<Object[]> queryList(String sql, Map<String, Object> params) {
        Query query = entityManager.createNativeQuery(sql);
        setParameters(query, params);
        return query.getResultList();
    }

    /**
     * 执行原生查询，映射为实体类
     */
    public <T> List<T> queryList(String sql, Map<String, Object> params, Class<T> resultClass) {
        Query query = entityManager.createNativeQuery(sql, resultClass);
        setParameters(query, params);
        return query.getResultList();
    }

    /**
     * 执行单个结果查询（适用于 count、sum 等）
     */
    public Object querySingle(String sql, Map<String, Object> params) {
        Query query = entityManager.createNativeQuery(sql);
        setParameters(query, params);
        return query.getSingleResult();
    }

    /**
     * 执行更新/删除语句
     */
    public int executeUpdate(String sql, Map<String, Object> params) {
        Query query = entityManager.createNativeQuery(sql);
        setParameters(query, params);
        return query.executeUpdate();
    }

    private void setParameters(Query query, Map<String, Object> params) {
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
    }
}

