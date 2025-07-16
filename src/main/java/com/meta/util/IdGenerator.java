package com.meta.util;

public class IdGenerator {
    private static final SnowflakeIdGenerator generator = new SnowflakeIdGenerator(1, 1);

    public static String nextId() {
        return String.valueOf(generator.nextId());
    }
}

