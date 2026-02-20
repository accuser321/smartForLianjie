package com.nh.smart.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.MessageDigest;

public class PasswordUtil {

    /**
     * @Param: [password]
     * @return: java.lang.String
     * @Author: wmy
     * @Date: 2019/9/20
     * 加密
     */
    public static String encryption(String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        password = bCryptPasswordEncoder.encode(password);
        return password;
    }


    /**
     * 具体规则可以自己定义 java代码
     *
     * @param password 新密码
     * @param
     * @return
     */
    public static boolean checkPassWord(String password) {
        //判断密码是否包含数字：包含返回1，不包含返回0
        int i = password.matches(".*\\d+.*") ? 1 : 0;

        //判断密码是否包含字母：包含返回1，不包含返回0
        int j = password.matches(".*[a-zA-Z]+.*") ? 1 : 0;

        //判断密码是否包含特殊符号(~!@#$%^&*()_+|<>,.?/:;'[]{}\)：包含返回1，不包含返回0
        int k = password.matches(".*[_+|<>,.?/:;'\\[\\]{}\"]+.*") ? 1 : 0;

        //判断密码长度是否在6-16位
        int l = password.length();

        if (i + j + k < 2 || l < 6 || l > 16) {
            return false;
        }
        return true;
    }


    /**
     * 将字符串进行sha1加密
     *
     * @param str 需要加密的字符串
     * @return 加密后的内容
     * @author 王名渤
     * @date 2019/12/23
     */
    public static String sha1(String str) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        digest.update(str.getBytes());
        byte messageDigest[] = digest.digest();

        StringBuffer hexString = new StringBuffer();
        // 字节数组转换为十六进制数
        for (int i = 0; i < messageDigest.length; i++) {
            String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
            if (shaHex.length() < 2) {
                hexString.append(0);
            }
            hexString.append(shaHex);
        }
        return hexString.toString();
    }
}
