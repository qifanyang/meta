package com.meta.core.model;

import com.meta.core.entity.GenericModelDataEntity;
import com.meta.core.entity.ModelDataEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模型执行可选参数
 */
public class ModelRunContext {

    public static final ModelRunContext DEFAULT = new ModelRunContext();

    /***
     * 模型数据Class
     */
    private Class<? extends ModelDataEntity> dataEntityClass = GenericModelDataEntity.class;
    /**
     * 是否复制fields, 生成明细数据时可以考虑不copy
     */
    private boolean copyFields = true;
    /**
     * 用于表示模型唯一标识列, field code
     */
    private List<String> identityCodes;

    private Map<String, Object> cacheMap = new HashMap<>();

    public Class<? extends ModelDataEntity> getDataEntityClass() {
        return dataEntityClass;
    }

    public void setDataEntityClass(Class<? extends ModelDataEntity> dataEntityClass) {
        this.dataEntityClass = dataEntityClass;
    }

    public boolean isCopyFields() {
        return copyFields;
    }

    public void setCopyFields(boolean copyFields) {
        this.copyFields = copyFields;
    }

    public List<String> getIdentityCodes() {
        return identityCodes;
    }

    public void setIdentityCodes(List<String> uniqueCodes) {
        this.identityCodes = uniqueCodes;
    }

    public Map<String, Object> getCacheMap() {
        return cacheMap;
    }

    public void setCacheMap(Map<String, Object> cacheMap) {
        this.cacheMap = cacheMap;
    }
}
