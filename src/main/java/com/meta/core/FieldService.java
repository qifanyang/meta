package com.meta.core;

import com.meta.core.dao.MetaFieldDao;
import com.meta.core.entity.MetaEntity;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FieldService {

    @Autowired
    private List<MetaFieldDao> fieldDaoList;

    private Map<Class<? extends MetaEntity>, MetaFieldDao> fieldDaoMap = new HashMap<>();

    @PostConstruct
    public void init(){
        for (MetaFieldDao metaFieldDao : fieldDaoList) {
            fieldDaoMap.put(metaFieldDao.getEntityType(), metaFieldDao);
        }
    }

    public List<MetaFieldDao> getFieldDaoList() {
        return fieldDaoList;
    }

    public void setFieldDaoList(List<MetaFieldDao> fieldDaoList) {
        this.fieldDaoList = fieldDaoList;
    }


    public <T> List<T> fieldList(Class<T> cls){
        MetaFieldDao metaFieldDao = fieldDaoMap.get(cls);
        return metaFieldDao.findAll();
    }


}
