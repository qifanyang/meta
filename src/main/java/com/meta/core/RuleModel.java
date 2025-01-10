package com.meta.core;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author yangqifan
 * @date 2025/1/10
 */
public abstract class RuleModel extends Model implements Iterable {

    public RuleModelIterator iterator() {
        return new OnceModelIterator();
    }

    public void run(Map<String, Object> params) {

        RuleModelIterator iterator = iterator();
        iterator.fetchData(params);
        while (iterator.hasNext()) {
            Object next = iterator.next();
            if (next != null) {

            }
            List<Field> fields = meta();
            //字段可以设置公式, 函数, 逻辑控制等
             // 数据对象?
             //运行规则产生运行时数据, 返回?
             //模型运行时不修改模型数据, 不存储运行时数据
        }

    }

    /**
     * @author yangqifan
     * @date 2025/1/10
     */
    public interface RuleModelIterator<T> extends Iterator {

        /**
         * 获取需要迭代的数据源
         *
         * @return
         */
        default void fetchData(Map<String, Object> params) {
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
