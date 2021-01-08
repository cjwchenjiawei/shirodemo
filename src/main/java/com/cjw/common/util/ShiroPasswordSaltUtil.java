package com.cjw.common.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.util.UUID;

public class ShiroPasswordSaltUtil {
    //加密方式
    public static final String HASH_ALGORITHM_NAME="MD5";

    /**
     * 生成32的随机盐值
     */
    public static String createSalt(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static final String md5Salt(String password){
        return md5Salt(password,createSalt());
    }

    public static final String md5Salt(Object password, String salt){
        //盐：为了即使相同的密码不同的盐加密后的结果也不同
        ByteSource byteSalt = ByteSource.Util.bytes(salt);
        System.out.println(salt);
        //加密次数
        int hashIterations = 1024;
        SimpleHash result = new SimpleHash(HASH_ALGORITHM_NAME, password, byteSalt, hashIterations);
        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println( md5Salt("123456"));
    }
}
