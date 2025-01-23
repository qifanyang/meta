package com.meta.core;

/**
 * 元数据根接口
 *
 * 模型结构如下:
 * Application --1:N--> Module
 * Module      --1:N--> Model
 * Model       --1:N--> Field 另外一个名字 Member
 *
 * 3M层级 module model member 每个都有property or attribute
 * @param <T>
 */
public interface Meta<T> {

    default T meta(){return null;}

    default Meta<?> visit(){
        return this;
    }

}
