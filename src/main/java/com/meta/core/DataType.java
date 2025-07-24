package com.meta.core;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 定义数据类型,
 * 并实现数据转换成对应的目标java数据类型, 类似实现Convert<Object, T>
 *
 */
public enum DataType {

    STRING(String.class){
        @Override
        public String convert(Object data) {
            //"null" String.valueOf() , Objects.toString() 空值默认转为null字符串
            return (data == null || "null".equals(data.toString().trim())) ? null : data.toString();
        }
    },
    NUMBER(BigDecimal.class){
        @Override
        public BigDecimal convert(Object data) {
            if (data == null){
                return null;
            }
            if (data instanceof BigDecimal decimal){
                return decimal;
            }else{
                String bigDecimalStr = data.toString().trim();
                if(!bigDecimalStr.isBlank() && !"null".equals(bigDecimalStr)) {
                    return new BigDecimal(bigDecimalStr);
                }
            }
            return null;
        }

        @Override
        public String display(Object data) {
            BigDecimal value = convert(data);
            return value == null ? "" : value.toPlainString();
        }
    },
    BOOLEAN(Boolean.class){
        @Override
        public Boolean convert(Object data) {
            if(data == null) {
                return false;
            }
            return Boolean.parseBoolean(Objects.toString(data, "false"));
        }
    },
    DATE(LocalDate.class){
        @Override
        public LocalDate convert(Object data) {
            if(data == null) {
                return null;
            }
            return super.convert(data);
        }
    },
    DATETIME(LocalDateTime.class){
        @Override
        public LocalDateTime convert(Object data) {
            return super.convert(data);
        }
    },
    MODEL(Object.class),
    ;

    private Class<?> javaType;

    DataType(Class<?> javaType){
        this.javaType = javaType;
    }

    /**
     * 转换为java数据类型
     * @param data 要转换的数据
     * @param <T> 转换为目标java类型
     * @return
     */
    public <T> T convert(Object data) {
        throw new IllegalArgumentException();
    }

    public String display(Object data){
        Object value = convert(data);
        return Objects.toString(data, "");
    }
}
