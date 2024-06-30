package com.meta.core;

/**
 * 动态元数据, 动态创建的元数据, 标记接口
 */
public interface DynamicMeta<T> extends Meta<T>{


    default void register(T t){

    }
}
