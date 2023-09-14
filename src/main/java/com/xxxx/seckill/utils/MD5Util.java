package com.xxxx.seckill.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * <p>
 * MD5加密测试
 * </p>
 *
 * @author Mr.Shi
 * @since 2023-09-14 14:31
 **/

public class MD5Util {
    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }
    // 和前端的salt保持一直
    private static final String salt = "1a2b3c4d";

    /**
     * 第一次加密
     * @param inputPass
     * @return
     */
    public static String inputPassToFromPass(String inputPass){
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }
    /**
     * 第二次加密
     * @param fromPass,salt
     * @return
     */
    public static String fromPassToDBPass(String fromPass, String salt){
        String str = "" + salt.charAt(0) + salt.charAt(2) + fromPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDBPass(String inputPass, String salt){
        String fromPass = inputPassToFromPass(inputPass);
        String dbPass = fromPassToDBPass(fromPass, salt);
        return dbPass;
    }

    /**
     * 测试md5加密
     * @param args
     */
    public static void main(String[] args) {
        // d3b1294a61a07da9b49b6e22b2cbd7f9
        System.out.println(inputPassToFromPass("123456"));
        System.out.println(fromPassToDBPass("d3b1294a61a07da9b49b6e22b2cbd7f9", "1a2b3c4d"));
        System.out.println(inputPassToDBPass("123456", "1a2b3c4d1a2b3c4d"));// b7797cce01b4b131b433b6acf4add449
    }
}

