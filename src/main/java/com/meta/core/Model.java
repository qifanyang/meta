package com.meta.core;

import com.meta.core.surpport.GroovyUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * model实例 可返回预置元数据和动态元数据
 * <p>
 * field可动态设置规则,可引用其他模型数据,
 * 可设置公式, 函数, 逻辑控制, 引用其它模型
 *
 * 子类命名规则: 模型名+Model 或 模块名+模型名+Model, 名字区分度很高可以不加模块名
 */
public abstract class Model<T extends ModelData> extends AbstractMeta<List<Field>> {

    private Module module;

    public T run(Map<String, Object> params) {
        T data = getInstance();
        List<Field> meta = meta();
        if (meta == null){
            return data;
        }
        for (Field field : meta) {
            data.getFields().add(field);
            Object value = scriptRunner().eval(params, field.getExpression());
            data.addValue(field.getId(), value);
        }
        return data;
    }

    @SuppressWarnings("unchecked")
    public T getInstance() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
            Type type = parameterizedType.getActualTypeArguments()[0];
            if (type instanceof Class<?>) {
                try {
                    return ((Class<T>) type).getDeclaredConstructor().newInstance();
                } catch (NoSuchMethodException e) {
                    throw new IllegalStateException(getClass().getName() + "无参构造函数不存在");
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
        }
        throw new IllegalStateException(getClass().getName() + "未指定泛型类型");
    }

    @Override
    public List<Field> meta() {
        return super.meta();
    }

    public ScriptRunner scriptRunner(){
        return (bindings, script) -> GroovyUtil.run(bindings, script);
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }
}
