package com.meta.core;

import com.meta.core.surpport.GroovyUtil;

import java.lang.reflect.InvocationTargetException;
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
public abstract class Model<T extends ModelData> extends AbstractMeta<List<Field>> {

    private Module module;

    /**
     * 通用模型运行方法 PECS
     *
     * @return ModelData 模型运行时数据对象
     */
    public static <D extends ModelData> D runModel(Class<? extends Model> modelCls, Class<D> dataCls, Map<String, Object> params) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ModelData modelData = createModelData(modelCls);
        Model model = modelData.getModel();
        if (model == null) {
            model = modelCls.getDeclaredConstructor().newInstance();
            modelData.setModel(model);
        }
        doRun(model, modelData, params);
        return dataCls.cast(modelData);
    }

    private static void doRun(Model model, ModelData modelData, Map<String, Object> params) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<Field> meta = model.meta();
        if (meta == null) {
            return;
        }
        for (Field field : meta) {
            modelData.getFields().add(field);
            Object fieldValue = null;
            if (field.getExpression() != null && !field.getExpression().isBlank()) {
                fieldValue = model.scriptRunner().eval(params, field.getExpression());
            } else if (field.getAssociatedModel() != null) {
                Class<? extends Model> associatedModel = field.getAssociatedModel();
                fieldValue = runModel0(associatedModel, params);
            }
            modelData.addValue(field.getCode(), fieldValue);
        }
    }

    /**
     * 内部使用, 返回ModeData类型不强转
     * @param modelCls
     * @param params
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private static ModelData runModel0(Class<? extends Model> modelCls,  Map<String, Object> params) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ModelData modelData = createModelData(modelCls);
        Model model = modelData.getModel();
        if (model == null) {
            model = modelCls.getDeclaredConstructor().newInstance();
            modelData.setModel(model);
        }
        doRun(model, modelData, params);
        return modelData;
    }

    /**
     * 返回ModelData
     *
     * @param cls
     * @return
     */
    @SuppressWarnings("unchecked")
    private static ModelData createModelData(Class<? extends Model> cls) {
        Type genericSuperclass = cls.getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
            Type type = parameterizedType.getActualTypeArguments()[0];
            if (type instanceof Class<?>) {
                try {
                    return ((Class<? extends ModelData>) type).getDeclaredConstructor().newInstance();
                } catch (NoSuchMethodException e) {
                    throw new IllegalStateException(cls.getName() + "无参构造函数不存在");
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
        }
        throw new IllegalStateException(cls.getName() + "未指定泛型类型");
    }


    public T run(Map<String, Object> params) throws Exception {
        return (T) runModel(getClass(), null, params);
    }

    public ScriptRunner scriptRunner() {
        return (bindings, script) -> GroovyUtil.run(bindings, script);
    }

    @Override
    public List<Field> meta() {
        return super.meta();
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }
}
