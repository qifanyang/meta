package com.meta.core;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 多次执行的模型
 * 场景1:后台管理系统需要批量生成数据使用
 */
public class IterableModel extends Model{

    //新建可迭代模型
    //1.配置要迭代的数据(数据源, 单个模型[过滤条件], sql(过滤条件))
    //2.可使用

    public void run(Map<String, Object> params, Consumer consumer, RuleModelIterator iterator) {
        if (consumer == null) {
            throw new IllegalArgumentException("consumer不能为空");
        }

        iterator.fetchData(params);
        while (iterator.hasNext()) {
            Object next = iterator.next();
            if (next != null) {

            }
            if (!iterator.filter(next)){
                continue;
            }
            List<FieldBean> meta = meta();
            //字段可以设置公式, 函数, 逻辑控制, 其它模型等
            //数据对象?
            //运行规则产生运行时数据, 返回?
            //模型运行时不修改模型数据, 不存储运行时数据
            Object obj = new Object();
            consumer.accept(obj);

        }

    }


    /**
     * @author yangqifan
     * @date 2025/1/10
     */
    public interface RuleModelIterator<T> extends Iterator {

        /**
         * 获取需要迭代的数据
         *
         * @return
         */
        default void fetchData(Map<String, Object> params) {
        }

        default boolean filter(T t){
            return true;
        }


    }

    class OnceModelIterator<T> implements RuleModelIterator {
        boolean iterated = false;
        @Override
        public boolean hasNext() {
            return !iterated;
        }

        @Override
        public T next() {
            this.iterated = true;
            return null;
        }

    }
}
