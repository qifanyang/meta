package com.meta.app;

import com.meta.core.Field;
import com.meta.core.MetaStore;

import java.util.List;

public class MemoryMetaStore implements MetaStore<Field> {



    @Override
    public void save() {

    }

    @Override
    public void save(String tenantId, String module, String subModule, List<Field> data) {

    }
}
