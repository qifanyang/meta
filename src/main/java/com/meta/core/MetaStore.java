package com.meta.core;

import java.util.List;

/**
 * 元数据持久化访问
 */
public interface MetaStore<T> {

    void save();

    void save(String tenantId, String module, String subModule, List<T> data);

}
