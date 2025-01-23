package com.meta.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHAUtil {

    public static String SHA1(String input){
        return generateSHA(input, "SHA-1");
    }

    public static String SHA256(String input){
        return generateSHA(input, "SHA-256");
    }

    public static String SHA512(String input){
        return generateSHA(input, "SHA-512");
    }

    public static String generateSHA(String input, String algorithm) {
        try {
            // 获取指定算法的 MessageDigest 实例
            MessageDigest digest = MessageDigest.getInstance(algorithm);

            // 计算哈希值
            byte[] hashBytes = digest.digest(input.getBytes());

            // 将字节数组转换为十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0'); // 补零
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("指定的哈希算法不可用: " + algorithm, e);
        }
    }

    public static void main(String[] args) {
        String input = "Hello, SHA!";

        // 使用不同的 SHA 算法
        String sha1Hash = generateSHA(input, "SHA-1");
        String sha256Hash = generateSHA(input, "SHA-256");
        String sha512Hash = generateSHA(input, "SHA-512");

        System.out.println("原始字符串: " + input);
        System.out.println("SHA-1 签名: " + sha1Hash);
        System.out.println("SHA-256 签名: " + sha256Hash);
        System.out.println("SHA-512 签名: " + sha512Hash);
    }
}
