package com.xxxx.seckill.utils;

import java.util.UUID;

/**
 * <p>
 * UUID工具类
 * </p>
 *
 * @author Mr.Shi
 * @since 2023-09-12 23:25
 **/

public class UUIDUtil {

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");//正常生成UID,然后替换到其中的"-"
    }

    public static void main(String[] args) {//4b1f4dab-b1c7-4a8c-a2f6-b36fc271baf8 加-有32位
        System.out.println(UUID.randomUUID().toString());//95315de2-3d8a-45ac-a1f0-b922c4b0227b
    }
}