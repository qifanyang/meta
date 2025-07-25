package com.meta.core.model;

import com.meta.core.BaseModel;
import com.meta.core.MetaDefinition;
import com.meta.core.ScriptRunner;
import com.meta.core.entity.ModelDataEntity;
import com.meta.core.field.FieldBean;
import com.meta.core.surpport.GroovyUtil;

import javax.script.ScriptException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface ModelDefinition extends MetaDefinition {

    String getDataTable();

    void setDataTable(String dataTable);

    default ModelDataEntity run(Map<String, Object> params){
        return run(params, ModelRunOptions.DEFAULT);
    }

    default ModelDataEntity run(Map<String, Object> params, ModelRunOptions options){
        ModelDataEntity modelData = instanceModelDataEntity(options.getDataEntityClass());
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
            } else if (field.getAssociatedModel() != null) {
                Class<? extends BaseModel> associatedModel = field.getAssociatedModel();
//                fieldValue = runModel(associatedModel, params);
            }
            modelData.getFieldValues().put(field.getCode(), fieldValue);
            modelData.getFieldDisplays().put(field.getCode(), field.formatToDisplay(fieldValue));
        }
        return modelData;
    }

    /**
     * 模型对应运行时数据实体, 可扩展指定额外的数据表
     * @return
     */
    default ModelDataEntity instanceModelDataEntity(Class<? extends ModelDataEntity> dataEntityClass){
        ModelDataEntity dataEntity;
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
