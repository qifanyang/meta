package com.meta.core.model;

import com.meta.core.BaseModel;
import com.meta.core.MetaDefinition;
import com.meta.core.ScriptRunner;
import com.meta.core.entity.ModelDataEntity;
import com.meta.core.entity.ModelEntity;
import com.meta.core.field.FieldBean;
import com.meta.core.field.FieldType;
import com.meta.core.surpport.GroovyUtil;
import com.meta.util.AppContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Table;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.util.Assert;

import javax.script.ScriptException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ModelDefinition extends MetaDefinition {

    String getDataTable();

    void setDataTable(String dataTable);

    default ModelDataEntity run(Map<String, Object> params){
        return run(params, ModelRunOptions.DEFAULT);
    }

    default ModelDataEntity run(Map<String, Object> params, ModelRunOptions options){
        ModelDataEntity modelData = instanceModelDataEntity(params, options);
        //copy 模型属性
        modelData.setModelId(getId());
        modelData.setModelCode(getCode());

        List<FieldBean> fields = getFields();
        for (FieldBean field : fields) {
            if (options.isCopyFields()){
                modelData.getFields().add(field.meta());
            }
            Object fieldValue = null;
            if (field.getExpression() != null && !field.getExpression().isBlank()) {
                fieldValue = scriptRunner().eval(params, field.getExpression());
            } else if (field.getFieldType().equals(FieldType.MODEL.name())) {
                ModelBean relationModelBean = AppContext.getBean(field.getCode(), ModelBean.class);
                Assert.isTrue(relationModelBean == null, "关联模型实例不存在, modelCode = " + field.getCode());
                ModelRunOptions relationRunOptions = new ModelRunOptions();
                relationRunOptions.setUniqueCodes(field.getUniqueCodes());
                ModelDataEntity relationModelData = relationModelBean.run(modelData.getFieldValues(), relationRunOptions);
                fieldValue = relationModelData.getId();
                relationModelData.getRelationModelData().put(field.getCode(), relationModelData);
            }
            modelData.getFieldValues().put(field.getCode(), fieldValue);
            modelData.getFieldDisplays().put(field.getCode(), field.formatToDisplay(fieldValue));
        }
        return modelData;
    }

    /**
     * 模型对应运行时数据实体, 可扩展指定额外的数据表, 这里可以是创建实体, 也可能是查询实体
     * @return
     */
    default ModelDataEntity instanceModelDataEntity(Map<String, Object> params, ModelRunOptions options){
        String dataTable = getDataTable();
        if (dataTable == null || dataTable.isEmpty()){
            throw new IllegalStateException("创建模型数据实体失败, 未指定数据表, 请检查模型dataTable值");
        }
        ModelDataEntity dataEntity = null;
        if (options.getUniqueCodes() != null) {
            //指定了model查询unique codes, 表明是要先查询关联数据, 而不是直接创建新的数据
            //
        }
        if (dataEntity != null){
            return dataEntity;
        }
        Class<? extends ModelDataEntity> dataEntityClass = null;
        Set<EntityType<?>> entities = AppContext.getBean(EntityManager.class).getMetamodel().getEntities();
        for (EntityType<?> entity : entities) {
            Table tableAnnotation = entity.getJavaType().getAnnotation(Table.class);
            if (tableAnnotation != null && dataTable.equalsIgnoreCase(tableAnnotation.name())) {
                dataEntityClass = (Class<? extends ModelDataEntity>) entity.getJavaType();
                break;
            }
        }
        if (dataEntityClass == null) {
            throw new IllegalStateException("创建模型数据实体失败, 无模型数据实体Class, 请检查模型dataTable值");
        }
        try {
            dataEntity = dataEntityClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("创建模型数据实体失败", e);
        }

        return dataEntity;
    }


    default ScriptRunner scriptRunner() {
        return (bindings, script) -> {
            try {
                return GroovyUtil.run(bindings, script);
            } catch (ScriptException e) {
                throw new IllegalStateException(e);
            }
        };
    }


    /**
     * 模型字段元数据
     * @return
     */
    default List<FieldBean> getFields(){
        return Collections.emptyList();
    }

    /**
     * 模型预置字段元数据
     * @return
     */
    default List<FieldBean> getPresetFields(){
        return Collections.emptyList();
    }

    /**
     * 模型自定义字段元数据
     * @return
     */
    default List<FieldBean> getCustomFields(){
        return Collections.emptyList();
    }
}
