package com.nh.smart.util;

import org.apache.commons.codec.binary.Hex;
import sun.misc.BASE64Encoder;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具类
 *
 * @author Demo
 * @date 2019-09-10 17:07
 */
public class MD5Util {


    /**
     * 加密，默认使用utf-8编码格式
     */
    public static String MD5EncodeForHex(String origin) throws Exception {
        return MD5EncodeForHex(origin.getBytes("utf-8"));
    }


    /**
     * 加密，使用传入的编码格式
     */
    public static String MD5EncodeForHex(String origin, String charsetname) throws Exception {
        return MD5EncodeForHex(origin.getBytes(charsetname));
    }


    private static String MD5EncodeForHex(byte[] origin) throws NoSuchAlgorithmException {
        return Hex.encodeHexString(digest("MD5", origin));
    }


    /**
     * 指定加密算法
     */
    private static byte[] digest(String algorithm, byte[] source) throws NoSuchAlgorithmException {
        MessageDigest md;
        md = MessageDigest.getInstance(algorithm);
        return md.digest(source);
    }


    public static void main(String[] args) {
        try {
            String s1 = "channelCode=DRBX&empno=125319&function=bdtgabc123";
            String s2 = MD5Util.MD5EncodeForHex(s1).toUpperCase();
            System.out.println(s2);
            String s3 = "channelCode=DRBX&empno=125319&function=bdtg&sign=" + s2;
            System.out.println(s3);
            BASE64Encoder encoder = new BASE64Encoder();
            String s4 = encoder.encodeBuffer(s3.getBytes());
            System.out.println(s4);
            String s5 = URLEncoder.encode(s4);
            System.out.println(s5);

            System.out.println("=============");

            String zz = "ZW1wbm89WkhaQTAyMDcmY2hhbm5lbENvZGU9RFJCWCZmdW5jdGlvbj1uYnNJbmZvJnNpZ249QUQ3OTNDRTUzNUM0MUI4MkE1OThCNDc4QzhCRkM0MUQ%3D";
            String decode1 = URLDecoder.decode(zz);
            System.out.println(decode1);
            String encode = URLEncoder.encode(decode1);
            System.out.println(encode);
            System.out.println(encode.equals(zz));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
