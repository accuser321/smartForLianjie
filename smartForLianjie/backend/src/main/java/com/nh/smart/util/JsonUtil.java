package com.nh.smart.util;

import java.util.Map;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @ClassName: JsonUtil
 * @Description: Json、Map、Object转换工具类
 * @Author Demo
 * @DateTime 2020年5月6日 下午2:47:02
 */
public class JsonUtil {

	public static Map obj2map(Object obj) throws Exception {
        String s = JSON.toJSONString(obj);
        Map<String, Object> map = json2map(s);
        return map;
    }


    public static <T> T json2obj(String jsonStr, Class<T> clazz) throws Exception {
        return JSON.parseObject(jsonStr, clazz);
    }


    public static Map<String, Object> json2map(String jsonStr) throws Exception {
        return JSON.parseObject(jsonStr, Map.class);
    }


    public static <T> T map2obj(Map<?, ?> map, Class<T> clazz) throws Exception {
        return JSON.parseObject(JSON.toJSONString(map), clazz);
    }
}
