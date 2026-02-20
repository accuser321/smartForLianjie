package com.nh.smart.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CommunicationUtil {

    private static String RLY_APPID = "8a216da8679d0e9d0167bf78448b1262";
    private static String RLY_TOKEN = "eb558d0fe0f8fe1427b5af344c226d04";
    private static String RLY_SERVER_IP = "dcsvip1imapp.cloopen.net";
    private static Integer RLY_PORT = 8883;
    private static String RLY_VERSION = "2013-12-26";


    public static String getLS(String data) throws Exception {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        String format = df.format(new Date());
        String sig = MD5Util.MD5EncodeForHex(RLY_APPID + RLY_TOKEN + format).toUpperCase();
        String url = "https://" + RLY_SERVER_IP + ":" + RLY_PORT + "/" + RLY_VERSION + "/Application/" + RLY_APPID + "/IM/GetIMHistoryMsg?sig=" + sig;
        String encode = Base64.getEncoder().encodeToString((RLY_APPID + ":" + format).getBytes());
        URL realUrl = new URL(url);
        // 打开和URL之间的连接
        URLConnection connection = realUrl.openConnection();
        // 设置通用的请求属性
        connection.setRequestProperty("Accept", ":application/json");
        connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
        connection.setRequestProperty("Authorization", encode);

        // 发送POST请求必须设置如下两行
        connection.setDoOutput(true);
        connection.setDoInput(true);
        // 获取URLConnection对象对应的输出流
        out = new PrintWriter(connection.getOutputStream());
        out.print(data);
        // flush输出流的缓冲
        out.flush();
        // 定义BufferedReader输入流来读取URL的响应
        in = new BufferedReader(new InputStreamReader(
                connection.getInputStream(), "UTF-8"));
        String line;
        while ((line = in.readLine()) != null) {
            result += line;
        }
        out.close();
        in.close();

        return result;
    }

    public static void main(String[] args) {
        try {
        String data = "{\"msgDecompression\":0,\"receiver\":\"8a216da8679d0e9d0167bf78448b1262#10004535\",\"sender\":\"100003\",\"time\":\"\"}";
            PrintWriter out = null;
            BufferedReader in = null;
            String result = "";
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
            String format = df.format(new Date());
            String sig = MD5Util.MD5EncodeForHex(RLY_APPID + RLY_TOKEN + format).toUpperCase();
            String url = "https://" + RLY_SERVER_IP + ":" + RLY_PORT + "/" + RLY_VERSION + "/Application/" + RLY_APPID + "/IM/GetIMHistoryMsg?sig=" + sig;
            String encode = Base64.getEncoder().encodeToString((RLY_APPID + ":" + format).getBytes());
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("Accept", ":application/json");
            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            connection.setRequestProperty("Authorization", encode);

            // 发送POST请求必须设置如下两行
            connection.setDoOutput(true);
            connection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(connection.getOutputStream());
            out.print(data);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            out.close();
            in.close();

            JSONObject jsonObject = JSONObject.parseObject(result);

                JSONArray result1 = jsonObject.getJSONArray("result");
                for (Object o : result1) {
                    String msgContent = new String(Base64.getDecoder().decode(((JSONObject) o).getString("msgContent")));
                    Map map = new HashMap();
                    map.put("msgReceiver", ((JSONObject) o).get("msgReceiver"));
                    map.put("msgId", ((JSONObject) o).get("msgId"));
                    map.put("msgDateCreated", ((JSONObject) o).get("msgDateCreated"));
                    map.put("msgContent", msgContent);
                    map.put("msgType", ((JSONObject) o).get("msgType"));
                    map.put("msgSender", ((JSONObject) o).get("msgSender"));
                    System.out.println(map);

                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
