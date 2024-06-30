package com.meta.core;

/**
 * 元数据根接口
 *
 * 模型结构如下:
 * Application --1:N--> Module
 * Module      --1:N--> Model
 * Model       --1:N--> Field
 * @param <T>
 */
public interface Meta<T> {

    T meta();

    default Meta<?> visit(){
        return this;
    }

}
