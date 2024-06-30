package com.meta.core;

import java.util.List;

public abstract class AbstractMeta<T> implements Meta<T> {

    /**
     * 全局唯一ID, 不同实例拥有不同ID
     */
    private String id;
    /**
     * 编码, 不同实例可以有相同编码
     */
    private String code;
    /**
     * 名字
     */
    private String name;

    private List<T> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }
}
