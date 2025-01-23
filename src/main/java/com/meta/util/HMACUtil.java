package com.meta.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * HMAC的秘钥和自己在签名内容上加上秘钥生成签名 有什么区
 *特性	        HMAC	                                   直接拼接秘钥+数据
 *安全性	        高：经过专门设计，抵抗多种攻击，如长度扩展攻击。	低：容易受到多种攻击（如长度扩展攻击）。
 *抗篡改性	    高：内外填充机制提高了抗篡改能力。	            低：篡改数据可能不会被发现。
 *算法标准	    标准算法，经过加密学验证。	                    无标准，可能存在设计漏洞。
 *实现复杂度	    稍复杂，但使用了标准库后实现简单。	            简单，但容易出错。
 *长度扩展攻击风险	无此风险，HMAC 的设计可以防止这种攻击。	    存在风险，尤其是对 SHA-256 的哈希拼接。
 *互操作性	    好：支持广泛的平台和库，容易与第三方系统集成。	    差：需要自定义实现，缺乏通用性。
 */
public class HMACUtil {

    public static String generateHMAC(String data, String key, String algorithm) {
        try {
            // 创建密钥
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), algorithm);

            // 获取 Mac 实例
            Mac mac = Mac.getInstance(algorithm);

            // 初始化 Mac 实例
            mac.init(secretKeySpec);

            // 生成 HMAC
            byte[] hmacBytes = mac.doFinal(data.getBytes());

            // 返回 Base64 编码的 HMAC
            return Base64.getEncoder().encodeToString(hmacBytes);
        } catch (Exception e) {
            throw new RuntimeException("HMAC 生成失败", e);
        }
    }

    public static void main(String[] args) {
        String data = "Hello, HMAC!";
        String key = "secret_key";
        String algorithm = "HmacSHA256";

        String hmac = generateHMAC(data, key, algorithm);
        System.out.println("HMAC-SHA256 签名: " + hmac);
    }
}
