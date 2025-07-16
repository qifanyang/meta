package com.meta.util;


/**
 * 雪花算法（Snowflake） 是 Twitter 提出的 64-bit 唯一 ID 生成方案，
 * 格式如下：
 *  | 1bit | 41bit 时间戳 | 10bit 机器信息 | 12bit 自增序列 |
 *
 * 它能生成趋势递增、全局唯一、高性能的主键，非常适合多租户 + 分布式系统
 */
public class SnowflakeIdGenerator {

    private final long workerId;
    private final long datacenterId;
    private final long sequenceBits = 12L;
    private final long workerIdBits = 5L;
    private final long datacenterIdBits = 5L;

    private final long maxWorkerId = ~(-1L << workerIdBits);       // 31
    private final long maxDatacenterId = ~(-1L << datacenterIdBits); // 31

    private final long workerIdShift = sequenceBits;                    // 12
    private final long datacenterIdShift = sequenceBits + workerIdBits;// 17
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits; // 22

    private final long sequenceMask = ~(-1L << sequenceBits);

    private final long epoch = 1700000000000L; // 自定义时间起点（建议固定）

    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public SnowflakeIdGenerator(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0)
            throw new IllegalArgumentException("workerId out of range");
        if (datacenterId > maxDatacenterId || datacenterId < 0)
            throw new IllegalArgumentException("datacenterId out of range");

        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    public synchronized long nextId() {
        long timestamp = timeGen();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards");
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - epoch) << timestampLeftShift)
                | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift)
                | sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long ts = timeGen();
        while (ts <= lastTimestamp) {
            ts = timeGen();
        }
        return ts;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }
}

