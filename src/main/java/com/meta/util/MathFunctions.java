package com.meta.util;

import com.meta.core.BuiltInFunctions;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 提供常用的数学计算内置函数。
 * Implements BuiltInFunctionsMarker 接口，表示它是一个内置函数集合类。
 */
public class MathFunctions implements BuiltInFunctions {

    public static BigDecimal SUM(BigDecimal x,  BigDecimal y) {
        return x.add(y);
    }

    /**
     * 计算数字列表的总和。
     *
     * @param numbers 包含数字（Integer, Long, Double, BigDecimal等）的列表。
     * @return 列表中所有数字的总和，以 BigDecimal 表示。
     */
    public static BigDecimal SUM(List<? extends Number> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return numbers.stream()
                .filter(Objects::nonNull) // 过滤掉 null 值
                .map(MathFunctions::toBigDecimal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static BigDecimal SUM(Number... numbers) {
        if (numbers == null || numbers.length == 0) {
            return BigDecimal.ZERO;
        }

        return Arrays.stream(numbers)
                .filter(Objects::nonNull) // 过滤掉 null 值
                .map(MathFunctions::toBigDecimal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * 计算数字列表的平均值。
     *
     * @param numbers 包含数字（Integer, Long, Double, BigDecimal等）的列表。
     * @return 列表中所有数字的平均值，以 BigDecimal 表示。如果列表为空，返回 BigDecimal.ZERO。
     */
    public static BigDecimal AVERAGE(List<? extends Number> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return BigDecimal.ZERO;
        }
        List<BigDecimal> bigDecimals = numbers.stream()
                .filter(Objects::nonNull)
                .map(MathFunctions::toBigDecimal)
                .collect(Collectors.toList());

        if (bigDecimals.isEmpty()) { // 过滤掉null后可能为空
            return BigDecimal.ZERO;
        }

        BigDecimal sum = bigDecimals.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum.divide(new BigDecimal(bigDecimals.size()), MathContext.DECIMAL128); // 使用MathContext保证精度
    }

    /**
     * 实现 IF 逻辑：如果条件为真，返回 valIfTrue，否则返回 valIfFalse。
     *
     * @param condition 逻辑条件。
     * @param valIfTrue 条件为真时返回的值。
     * @param valIfFalse 条件为假时返回的值。
     * @param <T> 返回值的类型。
     * @return 根据条件返回的值。
     */
    public static <T> T IF(boolean condition, T valIfTrue, T valIfFalse) {
        return condition ? valIfTrue : valIfFalse;
    }

    /**
     * 将任何 Number 类型转换为 BigDecimal，以确保精度。
     *
     * @param number 要转换的 Number 对象。
     * @return 对应的 BigDecimal 对象。
     */
    private static BigDecimal toBigDecimal(Number number) {
        if (number instanceof BigDecimal) {
            return (BigDecimal) number;
        } else if (number instanceof Integer || number instanceof Long || number instanceof Short || number instanceof Byte) {
            return new BigDecimal(number.longValue());
        } else if (number instanceof Float || number instanceof Double) {
            return new BigDecimal(number.doubleValue(), MathContext.DECIMAL64); // 对浮点数使用更安全的转换
        } else {
            return new BigDecimal(number.toString()); // Fallback for other Number types
        }
    }

    // 可以添加更多数学函数，例如 MAX, MIN, ROUND 等
    public static BigDecimal MAX(List<? extends Number> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return BigDecimal.ZERO; // 或者抛出 IllegalArgumentException
        }
        return numbers.stream()
                .filter(Objects::nonNull)
                .map(MathFunctions::toBigDecimal)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
    }

    public static BigDecimal MIN(List<? extends Number> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return BigDecimal.ZERO; // 或者抛出 IllegalArgumentException
        }
        return numbers.stream()
                .filter(Objects::nonNull)
                .map(MathFunctions::toBigDecimal)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
    }
}
