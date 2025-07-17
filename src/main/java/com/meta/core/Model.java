package com.meta.core;

import com.meta.core.surpport.GroovyUtil;

import javax.script.ScriptException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * model实例 可返回预置元数据和动态元数据
 * <p>
 * field可动态设置规则,可引用其他模型数据,
 * 可设置公式, 函数, 逻辑控制, 引用其它模型
 * <p>
 * 子类命名规则: 模型名+Model 或 模块名+模型名+Model, 名字区分度很高可以不加模块名
 */
public abstract class Model<T extends ModelData> extends AbstractMeta<List<FieldBean>> {

    private Module module;

    public static ModelData runModel(Class<? extends Model> modelCls, Map<String, Object> params) {
        ModelData modelData = createModelData(modelCls);
        Model model = modelData.getModel();
        if (model == null) {
            model = instantiateClass(modelCls);
            modelData.setModel(model);
        }
        doRun(model, modelData, params);
        return modelData;
    }

    /**
     * 通用模型运行方法 PECS
     *
     * @return ModelData 模型运行时数据对象
     */
    public static <D extends ModelData> D runModel(Class<? extends Model> modelCls, Class<D> modelDataCls, Map<String, Object> params) {
        ModelData modelData = createModelData(modelCls);
        Model model = modelData.getModel();
        if (model == null) {
            //缓存model到modelData
            model = instantiateClass(modelCls);
            modelData.setModel(model);
        }
        doRun(model, modelData, params);
        return (D) modelData;
    }

    private static void doRun(Model model, ModelData modelData, Map<String, Object> params) {
        List<FieldBean> meta = model.meta();
        if (meta == null) {
            return;
        }
        for (FieldBean field : meta) {
            modelData.getFields().add(field);
            Object fieldValue = null;
            if (field.getExpression() != null && !field.getExpression().isBlank()) {
                fieldValue = model.scriptRunner().eval(params, field.getExpression());
            } else if (field.getAssociatedModel() != null) {
                Class<? extends Model> associatedModel = field.getAssociatedModel();
                fieldValue = runModel(associatedModel, params);
            }
            modelData.addValue(field.getCode(), fieldValue);
        }
    }


    /**
     * 根据Model的子类实例化对象
     *
     * @param cls Model的子类
     * @return
     */
    @SuppressWarnings("unchecked")
    private static ModelData createModelData(Class<? extends Model> cls) {
        Type genericSuperclass = cls.getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
            Type type = parameterizedType.getActualTypeArguments()[0];
            if (type instanceof Class<?>) {
                return instantiateClass((Class<ModelData>) type);
            }
        }
        throw new IllegalStateException(cls.getName() + "未指定泛型类型");
    }


    public T run(Map<String, Object> params) throws Exception {
        return runModel(getClass(), null, params);
    }

    /**
     * 根据class实例化, 不检查构造方法private, 默认使用无参构建
     *
     * @param cls
     * @return
     */
    private static <T> T instantiateClass(Class<T> cls) {
        try {
            return cls.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException(cls.getName() + "无参构造函数不存在");
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public ScriptRunner scriptRunner() {
        return (bindings, script) -> {
            try {
                return GroovyUtil.run(bindings, script);
            } catch (ScriptException e) {
                throw new IllegalStateException(e);
            }
        };
    }

    @Override
    public List<FieldBean> meta() {
        return null;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }
}
