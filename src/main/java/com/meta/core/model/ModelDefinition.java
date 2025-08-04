package com.meta.core.model;

import com.meta.core.*;
import com.meta.core.entity.ModelDataEntity;
import com.meta.core.field.FieldBean;
import com.meta.core.field.FieldType;
import com.meta.core.surpport.GroovyUtil;
import com.meta.util.*;
import com.meta.util.db.ModelDataQuery;
import com.meta.util.db.RepositoryLocator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.Assert;

import javax.script.ScriptException;
import java.util.*;

public interface ModelDefinition extends MetaDefinition {
    /**
     * 每个模块应该有自己的函数
     */
    String keyBuiltInFunctions = "key_built_in_functions";

    String getDataTable();

    void setDataTable(String dataTable);

    /**
     * 返回模型输入
     * 1. 参数map
     * 2. 模型(可以包含条件用来筛选数据)
     * 3. 如果涉及数据权限, 需要联表查询person
     *
     * @return
     */
    default List<ModelInput> getInputs(){return null;};

    default void setInput(List<ModelInput> input){};

    /**
     * 预置输出, 自定义输出需要合并 TODO
     * @return
     */
    default List<ModelOutput> getOutputs(){return null;}

    /**
     * 返回模块内置函数, 脚本执行import static 静态方法
     * @return
     */
    default List<Class<? extends BuiltInFunctions>> builtInFunctions(){
        return List.of(MathFunctions.class, StringUtils.class);
    }

    default List<ModelDataEntity> run(List<ModelInput> inputs){
        return run(inputs, ModelRunContext.DEFAULT);
    }

    default List<ModelDataEntity> run(Map<String, Object> params){
        ModelInput modelInput = new ModelInput();
        modelInput.setParams(params);
        return run(List.of(modelInput), ModelRunContext.DEFAULT);
    }

    default List<ModelDataEntity> run(List<ModelInput> inputs, ModelRunContext context) {
        if (inputs == null || inputs.isEmpty()) {
            throw new IllegalStateException("模型输入不能为空!");
        }
        Map<String, Object> params = new LinkedHashMap<>();
        List<ModelDataEntity> resultList = new LinkedList<>();
        boolean hasModelInput = false;
        for (ModelInput input : inputs) {
            //查询模型数据, 模型数据会有多条, 循环执行
            //TODO 模型数据查询缓存, 循环计算时直接获取
            if (input.getModelCode() == null && input.getParams() != null) {
                params.putAll(input.getParams());
            } else {
                hasModelInput = true;
                //多个params输入, 这里不能跳过
            }
        }
        if (hasModelInput) {
            ModelDataQuery builder = ModelDataQuery.builder();
            for (ModelInput input : inputs) {
                if (input.getModelCode() != null) {
                    if (input.isMainInput()) {
                        builder.mainModel(input.getModelCode(), input.getJoinKey());
                    } else {
                        if (input.getJoinKey() != null || !input.getJoinKey().isBlank()){
                            builder.joinModel(input.getModelCode(), input.getJoinKey());
                        }
                    }
                }
            }
            List<Map<String, Object>> inputDataList = builder.execute();
//        Object o = context.getCacheMap().get(input.getModelCode());
            for (Map inputMap : inputDataList) {
                HashMap<String, Object> inputParams = new HashMap<>(params);
                inputParams.putAll(inputMap);
                ModelDataEntity modelData = doRun(inputParams, context);
                resultList.add(modelData);
            }
        } else {
            resultList.add(doRun(params, context));
        }
        return resultList;
    }

    default ModelDataEntity doRun(Map<String, Object> params, ModelRunContext options){
        ModelDataEntity modelData = instanceModelDataEntity(params, options);
        //copy 模型属性
        modelData.setModelId(getId());
        modelData.setModelCode(getCode());

        //注册内置函数,放到params中
        List<Class<? extends BuiltInFunctions>> builtInFunctions = builtInFunctions();
        if (!builtInFunctions.isEmpty()){
            params.put(keyBuiltInFunctions, builtInFunctions);
        }

        List<FieldBean> fields = FieldBeanSorterDFS.sortFieldBeans(getFields(), true);
        for (FieldBean field : fields) {
            if (options.isCopyFields()){
                modelData.getFields().add(field.meta());
            }
            Object fieldValue = null;
            if (field.getFieldType().equals(FieldType.MODEL.name())) {
//                ModelBean relationModelBean = AppContext.getBean(field.getCode(), ModelBean.class);
//                Assert.isTrue(relationModelBean != null, "关联模型实例不存在, modelCode = " + field.getCode());
//                ModelRunContext relationRunOptions = new ModelRunContext();
//                relationRunOptions.setIdentityCodes(field.getIdentityCodes());
//
//                ModelDataEntity relationModelData = relationModelBean.doRun(params, relationRunOptions);
//                fieldValue = relationModelData.getId();
//                //关联模型数据执行后 单独存储. 外部主模型数据负责保存
//                modelData.getRelationModelData().put(field.getCode(), relationModelData);
            } else if (field.getExpression() != null && !field.getExpression().isBlank()) {
                //自依赖初始化, 使用默认值初始化
                if (!field.getExpression().equals(field.getCode()) && field.getDependentVariables() != null && field.getDependentVariables().contains(field.getCode())){
                    //payableTotal = payableTotal + payable, 自依赖,
                    Object defaultValue = field.formatToValue(field.getDefaultValue());
                    params.put(field.getCode(), defaultValue);
                }
                fieldValue = scriptRunner().eval(params, field.getExpression());
            }
            Object formatToValue = field.formatToValue(fieldValue);
            modelData.updateSpecificFieldValue(field, formatToValue);
            modelData.getFieldValues().put(field.getCode(), formatToValue);
            modelData.getFieldDisplays().put(field.getCode(), field.formatToDisplay(fieldValue));
            //TODO 计算后的值放入上下文 这里要重新设计上下文, 存在重复的code 要更新上下文?
            params.put(field.getCode(), formatToValue);
        }

        List<ModelOutput> outputs = getOutputs();
        if (outputs != null){
            for (ModelOutput output : outputs) {
                ModelBean relationModelBean = AppContext.getBean(output.getModelCode(), ModelBean.class);
                Assert.isTrue(relationModelBean != null, "关联模型实例不存在, modelCode = " + output.getModelCode());
                ModelRunContext subContext = new ModelRunContext();
                subContext.setIdentityCodes(output.getIdentityCodes());

                ModelDataEntity relationModelData = relationModelBean.doRun(params, subContext);
                //fieldValue = relationModelData.getId();
                modelData.getRelationModelData().put(output.getModelCode(), relationModelData);
            }
        }

        return modelData;
    }

    /**
     * 模型对应运行时数据实体, 可扩展指定额外的数据表, 这里可以是创建实体, 也可能是查询实体
     * @return
     */
    default ModelDataEntity instanceModelDataEntity(Map<String, Object> params, ModelRunContext options){
        String dataTable = getDataTable();
        if (dataTable == null || dataTable.isEmpty()){
            throw new IllegalStateException("创建模型数据实体失败, 未指定数据表, 请检查模型dataTable值");
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
        ModelDataEntity dataEntity = null;
        if (options.getIdentityCodes() != null) {
            //指定了model查询unique codes, 表明是要先查询关联数据, 而不是直接创建新的数据
            JpaRepository<? extends ModelDataEntity, String> modelDataDao = AppContext.getBean(RepositoryLocator.class).getRepository(dataEntityClass);
            List<ModelDataEntity> existModelData = ((JpaSpecificationExecutor)modelDataDao).findAll((root, query, cb) -> {
                List<Predicate> predicates = new ArrayList<>();
                for (String code : options.getIdentityCodes()) {
                    Object value = params.get(code);
                    if (value instanceof String) {
                        predicates.add(cb.like(root.get(code), "%" + value + "%"));
                    } else {
                        predicates.add(cb.equal(root.get(code), value));
                    }
                }
                return cb.and(predicates.toArray(new Predicate[0]));
            });
            Assert.isTrue(existModelData != null && existModelData.size() < 2, String.format("uniqueCodes指定的modelData不唯一, modelMode = %s", getCode()));
            dataEntity = existModelData.size() == 1 ? existModelData.get(0) : null;
        }
        if (dataEntity != null){
            //查询到对应modelData, 不新创建直接返回
            return dataEntity;
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
