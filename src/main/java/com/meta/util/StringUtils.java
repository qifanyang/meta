package com.meta.util;


import com.meta.core.BuiltInFunctions;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 提供常用的字符串处理内置函数。
 * Implements BuiltInFunctions 接口。
 */
public class StringUtils implements BuiltInFunctions {

    /**
     * 将给定字符串的首字母大写。
     *
     * @param str 待处理的字符串。
     * @return 首字母大写后的字符串。如果输入为 null 或空，返回原始字符串。
     */
    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * 连接多个字符串。
     *
     * @param delimiter 连接符。
     * @param strings 要连接的字符串数组。
     * @return 连接后的字符串。
     */
    public static String join(String delimiter, String... strings) {
        if (strings == null || strings.length == 0) {
            return "";
        }
        return Arrays.stream(strings)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(delimiter));
    }

    /**
     * 判断字符串是否为空（null 或空字符串）。
     *
     * @param str 待判断的字符串。
     * @return 如果字符串为 null 或空，返回 true；否则返回 false。
     */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    // 可以添加更多字符串函数，例如 SUBSTRING, TRIM, CONTAINS, REPLACE 等
    public static int LENGTH(String str) {
        return str == null ? 0 : str.length();
    }
}
