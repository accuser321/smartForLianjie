package com.nh.smart.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nh.smart.dao.record.SmartComMuserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.nh.smart.util.HttpClientUtils.HttpsUtil;


@Component
@Configuration
@Slf4j
public class MessageTemplateUtils {

    @Autowired
    private SmartComMuserDao smartComMuserDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private static MessageTemplateUtils messageTemplateUtils;

    /**
     * 微信公众号开发模式token
     */
    private static String validToken;
    public void setAccessKeySecret(String validToken) {
        MessageTemplateUtils.validToken = "TSOAKAesnABTschj";
    }


    @PostConstruct
    public void init() {
        messageTemplateUtils = this;
        messageTemplateUtils.smartComMuserDao = this.smartComMuserDao;
        messageTemplateUtils.redisTemplate = this.redisTemplate;
    }


    /**
     * 获取AccessToken
     *
     * @return
     * @throws Exception
     */
    public static String getAccessToken(String comid) throws Exception {
        Object o = messageTemplateUtils.redisTemplate.opsForValue().get(comid + "AccessToken");
        if (o != null) {
            return o.toString();
        }
        Map<String, String> map =messageTemplateUtils.smartComMuserDao.getWxInfo(comid);
        String appId = map.get("appid");
        String appsecret =map.get("appsecrt");
        // 访问地址
        String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
        String request_url = TOKEN_URL.replace("APPID", appId).replace("APPSECRET", appsecret);
        JSONObject jsonObject = HttpsUtil(request_url, "GET", null);
        if (null != jsonObject) {
            String access_tocken = jsonObject.getString("access_token");
            // 放入缓存中，设置过期时间为90分钟
            messageTemplateUtils.redisTemplate.opsForValue().set(comid + "AccessToken", access_tocken, 90, TimeUnit.MINUTES);
            return access_tocken;
        }
        return null;
    }


    /**
     * 获取JsapiTicket
     *
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019-07-18 11:47
     */
    public static String getJsapiTicket() throws Exception {
        String comid = JwtTokenUtil.getComid();
        // 访问地址
        String accessToken = getAccessToken(comid);
        String JsapiTicketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
        String request_url = JsapiTicketUrl.replace("ACCESS_TOKEN", accessToken);
        JSONObject jsonObject = HttpsUtil(request_url, "GET", null);
        if (null != jsonObject) {
            String ticket = jsonObject.getString("ticket");
            String expires_in = jsonObject.getString("expires_in");
            return ticket;
        }
        return null;
    }


    /**
     * 签名算法
     *
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019-07-18 14:50
     */
    public static Map<String, String> getSign(String url, String comid) throws Exception {
        String jsapiTicket;
        Object o = messageTemplateUtils.redisTemplate.opsForValue().get(comid + "JsapiTicket");
        if (o != null) {
            jsapiTicket = o.toString();
        } else {
            jsapiTicket = getJsapiTicket();
            // 放入缓存中，设置过期时间为90分钟
            messageTemplateUtils.redisTemplate.opsForValue().set(comid + "JsapiTicket", jsapiTicket, 90, TimeUnit.MINUTES);
        }

        Map<String, String> ret = new HashMap<>(5);
        String nonceStr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        String signature = "";

        // 注意这里参数名必须全部小写，且必须有序
        String string = "jsapi_ticket=" + jsapiTicket + "&noncestr=" + nonceStr + "&timestamp=" + timestamp + "&url=" + url;

        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            // 对string字符串进行SHA-1加密处理
            crypt.update(string.getBytes("UTF-8"));
            // 对加密后字符串转成16进制
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapiTicket);
        ret.put("timestamp", timestamp);
        ret.put("nonceStr", nonceStr);
        ret.put("signature", signature);
        return ret;
    }


    /**
     * 对加密后字符串转成16进制
     *
     * @param hash
     * @return
     * @author 王名渤
     * @date 2019-07-18 14:55
     */
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }


    /**
     * 发送图文消息提醒
     *
     * @param openid      指定用户的openid
     * @param title       标题
     * @param description 描述
     * @param url         消息跳转链接
     * @param picurl      消息提醒头像
     * @param comid       渠道编码
     * @return
     * @Author 张宁
     * @Date 2019/11/7
     */
    public static JSONObject sendKFMessage(String openid, String title, String description, String url, String picurl, String comid) throws Exception {
        String token = getAccessToken(comid);
        String postUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + token;
        // 图文消息请求实体
        JSONObject jsonObject = new JSONObject();
        // 图文消息内容实体
        JSONObject news = new JSONObject();
        JSONArray newsarray = new JSONArray();
        JSONObject articles = new JSONObject();
        // 设置业务员openid
        jsonObject.put("touser", openid);
        // 设置客服消息类型
        jsonObject.put("msgtype", "news");

        // 设置图文消息内容
        articles.put("title", title);
        articles.put("description", description);
        articles.put("url", url);
        articles.put("picurl", picurl);

        newsarray.add(articles);
        news.put("articles", newsarray);
        jsonObject.put("news", news);

        String string = HttpClientUtils.sendPostJsonStr(postUrl, jsonObject.toJSONString());
        JSONObject result = JSON.parseObject(string);
        int errcode = result.getIntValue("errcode");
        if (errcode == 0) {
            // 发送成功
            log.info("客服消息~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~发送成功");
        } else {
            // 发送失败
            log.info("客服消息~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~发送失败");
        }
        return articles;
    }


    /**
     * 发送图文消息提醒
     *
     * @param openid  指定用户的openid
     * @param content 文本消息内容
     * @param comid   渠道编码
     * @return
     * @Author 王名渤
     * @Date 2019/12/25
     */
    public static JSONObject sendTextKFMessage(String openid, String content, String comid) throws Exception {
        String token = getAccessToken(comid);
        String postUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + token;
        // 图文消息请求实体
        JSONObject jsonObject = new JSONObject();
        JSONObject articles = new JSONObject();

        // 设置业务员openid
        jsonObject.put("touser", openid);
        // 设置客服消息类型
        jsonObject.put("msgtype", "text");
        // 设置图文消息内容
        articles.put("content", content);
        jsonObject.put("text", articles);

        String string = HttpClientUtils.sendPostJsonStr(postUrl, jsonObject.toJSONString());
        JSONObject result = JSON.parseObject(string);
        int errcode = result.getIntValue("errcode");
        if (errcode == 0) {
            // 发送成功
            log.info("客服消息~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~发送成功");
        } else {
            // 发送失败
            log.info("客服消息~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~发送失败");
        }
        return articles;
    }


    /**
     * 验证微信服务器发送的随机字符串是否一致
     *
     * @param signature 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp、nonce参数
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/12/23
     */
    public static boolean validParams(String signature, String timestamp, String nonce) throws Exception {
        // 创建包含时间戳，随机数, token的数组并排序
        String arr[] = new String[]{timestamp, nonce, validToken};
        Arrays.sort(arr);

        // 生成字符串
        StringBuffer bf = new StringBuffer();
        for (String s : arr) {
            bf.append(s);
        }

        // sha1加密
        String text = PasswordUtil.sha1(bf.toString());

        // 如果相同返回true
        if (text.equals(signature)) {
            return true;
        }
        return false;
    }

}
