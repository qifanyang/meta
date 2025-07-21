package com.meta.core;

import com.meta.core.dao.MetaFieldDao;
import com.meta.core.entity.MetaEntity;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MetaFieldManager {

    @Autowired
    private List<MetaFieldDao> fieldDaoList;

    private Map<Class<? extends MetaEntity>, MetaFieldDao> fieldDaoMap = new HashMap<>();

    @PostConstruct
    public void init(){
        for (MetaFieldDao metaFieldDao : fieldDaoList) {
            fieldDaoMap.put(metaFieldDao.getEntityType(), metaFieldDao);
        }
    }

    public <T> MetaFieldDao metaFieldDao(Class<T> cls){
        return fieldDaoMap.get(cls);
    }

    public <T> List<T> fieldList(Class<T> cls){
        MetaFieldDao metaFieldDao = fieldDaoMap.get(cls);
        return metaFieldDao.findAll();
    }

    public <T> List<T> fieldList(String modelCode, Class<T> cls){
        MetaFieldDao metaFieldDao = fieldDaoMap.get(cls);
        return metaFieldDao.findByModelCode(modelCode);
    }


    //根据模型查找字段


}
