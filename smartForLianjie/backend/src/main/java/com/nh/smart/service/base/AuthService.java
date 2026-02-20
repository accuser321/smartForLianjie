package com.nh.smart.service.base;

import com.alibaba.fastjson.JSONObject;
import com.nh.smart.entity.base.Result;
import com.nh.smart.entity.record.SmartComMuser;

import java.util.Map;


/**
 * smart_com_manageuser#渠道管理员表 业务逻辑的接口类
 */
public interface AuthService {

    /**
     * 获取token
     *
     * @param map
     * @return
     */
    Result getToken(Map<String, String> map, String comid, String ip) throws Exception;


    /**
     * 用户绑定
     *
     * @param empno    工号
     * @param password 密码
     * @param type     人员类型
     * @return
     */
    JSONObject login(String empno, String password, String type) throws Exception;


    /**
     * 获取用户信息
     *
     * @param user
     * @return
     */
    Result getUser(SmartComMuser user) throws Exception;


    /**
     * 刷新用户token
     *
     * @param ip
     * @return
     * @throws Exception
     */
    JSONObject refreshToken(String ip) throws Exception;


    /**
     * 微信解绑
     *
     * @param userid
     * @throws Exception
     */
    void delOpenid(String userid, String comid) throws Exception;


    /**
     * 获取微信配置
     *
     * @return
     */
    JSONObject getWxInfo(String comid) throws Exception;


    /**
     * 获取当前用户信息
     *
     * @return
     */
    JSONObject getUerInfo() throws Exception;

    /**
     * 根据userid获取对应用户信息
     *
     * @param userid
     * @return
     * @throws Exception
     */
    JSONObject getinfobyid(String userid) throws Exception;


    /**
     * 获取自增用户id
     *
     * @return
     */
    String getUserId() throws Exception;

    /**
     * 注册发送短信验证码
     *
     * @return
     */
    Result register(String phone) throws Exception;

    /**
     * 注册验证
     *
     * @return
     */
    JSONObject verification(String phone, String code) throws Exception;

    /**
     * 登录发送短信验证码
     *
     * @return
     */
    Result msglogin(String phone) throws Exception;

    /**
     * 手机验证码登录
     *
     * @return
     */
    JSONObject msgVerification(String phone, String code) throws Exception;


    /**
     * 更换手机号发送验证码
     *
     * @Param: [phone]
     * @return: com.saascs.common.entity.Result
     * @Author: 张宁
     * @Date: 2020/2/28
     */
    Result changePhone(String phone) throws Exception;
}
