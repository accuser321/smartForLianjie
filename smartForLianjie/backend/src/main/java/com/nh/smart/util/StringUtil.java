package com.nh.smart.util;

import com.nh.smart.exception.MyExceptionUtil;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 王名渤
 * @date 2019-09-30 11:18
 */
public class StringUtil {

    // 验证码字符集
    private static final String[] words = {
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
    };


    /**
     * 判断字符串是否仅含有数字和字母
     *
     * @param str 字符串
     * @return
     */
    public static boolean checkLetterAndDigit(String str) {
        String regex = "^[a-z0-9A-Z]+$";
        return str.matches(regex);
    }


    /**
     * 判断字符串是否仅含有字母
     *
     * @param str 字符串
     * @return
     */
    public static boolean checkLetter(String str) {
        String regex = "^[a-zA-Z]+$";
        return str.matches(regex);
    }


    /**
     * 生成随机三位数字字符串
     */
    public static String createNum() {
        List<String> list = new ArrayList<>();
        Random ran = new Random();
        for (int i = 0; i < 3; i++) {
            // 取随机字符索引
            int n = ran.nextInt(words.length);
            list.add(words[n]);
        }
        String yang = list.get(0) + list.get(1) + list.get(2);
        return yang;
    }

    /**
     * 生成编号，规则是前缀+年月日时分秒毫秒+随机三位数字字符串
     */
    public synchronized static String createCode(String prefix) {
        // 判断前缀是否为空或是否为纯字母
        if (StringUtils.isBlank(prefix) || !StringUtil.checkLetter(prefix)) {
            throw MyExceptionUtil.mxe("参数格式有误，请校验");
        }

        // 获取当前时间到毫秒
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String time = df.format(new Date());
        String id ="";
        if (StringUtils.isNotEmpty(prefix)){
            id = prefix.toUpperCase() + time + createNum();
        }else {
            id =  time + createNum();
        }

        return id;
    }

    /**
     * 截取字符串str中指定字符 strStart、strEnd之间的字符串(包含strStart)
     *
     * @param str
     * @param strStart
     * @param strEnd
     * @return
     */
    public static String subString(String str, String strStart, String strEnd) {
        /* 找出指定的2个字符在 该字符串里面的 位置 */
        int strStartIndex = str.indexOf(strStart);
        int strEndIndex = str.indexOf(strEnd);

        /* index 为负数 即表示该字符串中 没有该字符 */
        if (strStartIndex < 0) {
            return "字符串 :---->" + str + "<---- 中不存在 " + strStart + ", 无法截取目标字符串";
        }
        if (strEndIndex < 0) {
            return "字符串 :---->" + str + "<---- 中不存在 " + strEnd + ", 无法截取目标字符串";
        }
        /* 开始截取 */
        String result = str.substring(strStartIndex, strEndIndex);
        return result;
    }


    /**
     * 校验身份证号
     *
     * @param id
     * @return
     * @Author 王名渤
     * @Date 2019/12/11
     */
    public static boolean checkId(String id) {
        // 身份证格式的正则校验
        String reg = "^\\d{15}$|^\\d{17}[0-9Xx]$";
        if (!id.matches(reg)) {
            return false;
        }

        // 身份证最后一位的校验算法
        char[] idchar = id.toCharArray();
        int sum = 0;
        int w[] = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        char[] ch = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
        for (int i = 0; i < idchar.length - 1; i++) {
            sum += (idchar[i] - '0') * w[i];
        }
        int c = sum % 11;
        char code = ch[c];
        char last = idchar[idchar.length - 1];
        last = last == 'x' ? 'X' : last;
        return last == code;
    }


    /**
     * 校验邮箱
     *
     * @param email
     * @return
     * @Author 王名渤
     * @Date 2020/2/3
     */
    public static boolean checkEmail(String email) {
        if (email == null) {
            return false;
        }
        String rule = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(rule);
        matcher = pattern.matcher(email);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }
}
