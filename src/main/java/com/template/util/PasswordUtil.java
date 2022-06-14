package com.template.util;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * 密码工具类
 *
 * @author Doug Liu
 * @since 2022-06-10
 *
 */
public class PasswordUtil {

    /**
     * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
     */
    public static String generateSalt() {
        return (int) (Math.random() * 9000 + 1000) + "";
    }

    /**
     * 生成盐值
     * @param plainPassword 密码明文
     * @param salt          盐值
     * @return String
     */
    public static String encryptPassword(String plainPassword, String salt) {
        return new Md5Hash(plainPassword, salt, 1).toHex();
    }

    /**
     * 验证密码
     * @param plainPassword 明文密码
     * @param salt          盐值
     * @param password      密码
     * @return Boolean
     */
    public static Boolean validatePassword(String plainPassword, String salt, String password) {
        String newPassword = encryptPassword(plainPassword, salt);
        return password.equals(newPassword);
    }
}
