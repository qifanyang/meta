package com.meta.core;

/**
 * 元数据根接口
 * <p>
 * 模型结构如下:
 * Application --1:N--> Module
 * Module      --1:N--> Model
 * Model       --1:N--> Field 另外一个名字 Member
 * <p>
 * 3M层级 module model member 每个都有property or attribute
 *
 * @param <T>
 */
public interface Meta<T> {

    /**
     * 返回元数据entity
     * @return
     */
     default T meta(){
         return (T) this;
     }

    /**
     * 是否是预置数据
     * @return
     */
     default boolean isPreset(){
         return false;
     }

    default Meta<?> visit() {
        return this;
    }

}
