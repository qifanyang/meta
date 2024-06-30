package com.meta.core;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public interface EnumerableMeta<T> {

  default List<T> enumerateMeta(){
     try {
        // 获取枚举类的 values() 方法
        Method method = getClass().getMethod("values");
        // 调用 values() 方法并返回结果
        Meta<T>[] values = (Meta<T>[]) method.invoke(null);
        return Arrays.asList(values).stream().map(Meta::meta).collect(Collectors.toList());
     } catch (Exception e) {
        e.printStackTrace();
        // 异常处理，例如可以返回一个空数组或者抛出运行时异常
        return null;
     }
  }


}
