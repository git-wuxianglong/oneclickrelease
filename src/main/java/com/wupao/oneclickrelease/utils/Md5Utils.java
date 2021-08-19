package com.wupao.oneclickrelease.utils;

import org.apache.shiro.crypto.hash.SimpleHash;


/**
 * md5加密
 *
 * @author wuxianglong
 */
public class Md5Utils {

    /**
     * 密码加密
     *
     * @return md5结果
     */
    public static String md5Encryption(String source, String salt) {
        // 加密算法
        String algorithmName = "MD5";
        // 加密次数
        int hashIterations = 1024;
        SimpleHash simpleHash = new SimpleHash(algorithmName, source, salt, hashIterations);
        return simpleHash + "";
    }

    public static void main(String[] args) {
        String s = md5Encryption("123456", "123456");
        System.out.printf(s);
    }
}
