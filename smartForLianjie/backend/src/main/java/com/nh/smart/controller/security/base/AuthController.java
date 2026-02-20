package com.nh.smart.controller.security.base;

import com.alibaba.fastjson.JSONObject;
import com.nh.smart.annotation.MultiRequestBody;
import com.nh.smart.entity.base.Result;
import com.nh.smart.service.base.AuthService;
import com.nh.smart.service.material.SmartComKjLibwService;
import com.nh.smart.util.IpUtil;
import com.nh.smart.util.JwtTokenUtil;
import com.nh.smart.util.MessageTemplateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 权限控制器
 *
 * @author 王名渤
 * @date 2019-09-10 16:40
 */
@RestController
@Api(tags = "登陆管理")
@CrossOrigin()
// Keep both prefixes for legacy frontend compatibility.
@RequestMapping({"/auth", "/saas/auth"})
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private SmartComKjLibwService smartComKjLibwService;
    /**
     * 更换手机号发送验证码
     *
     * @Param: [phone]
     * @return: com.saascs.common.entity.Result
     * @Author: 张宁
     * @Date: 2020/2/28
     */
    @GetMapping("/changePhone/{phone}")
    @ApiOperation("获取手机号验证码(张宁)")
    @ApiImplicitParam(name = "phone", value = "手机号(必须)", required = false, dataType = "String")
    public Result getPhnoeNum(@PathVariable String phone) throws Exception {
        return authService.changePhone(phone);
    }

    // 兼容 query 形式：/saas/auth/changePhone?phone=xxx
    @GetMapping("/changePhone")
    public Result getPhnoeNumByQuery(@RequestParam String phone) throws Exception {
        return getPhnoeNum(phone);
    }


    /**
     * 南华网销注册发送验证码
     *
     * @param
     * @return
     */
    @GetMapping("/register/{phone}")
    @ApiOperation(value = "获取指定userid信息(yk)", notes = "获取微信用户openid")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "URL", value = "url", required = true, dataType = "String")
    })
    public Result register(@PathVariable String phone) throws Exception {
        return authService.register(phone);
    }

    // 兼容 query 形式：/saas/auth/register?phone=xxx
    @GetMapping("/register")
    public Result registerByQuery(@RequestParam String phone) throws Exception {
        return register(phone);
    }


    /**
     * 南华网销注册验证
     *
     * @param
     * @return
     */
    @PostMapping("/verification")
    @ApiOperation(value = "获取指定userid信息(yk)", notes = "获取微信用户openid")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "手机号", value = "phone", required = true, dataType = "String"),
            @ApiImplicitParam(name = "验证码", value = "code", required = true, dataType = "String")
    })
    public Result verification(@MultiRequestBody String phone, @MultiRequestBody String code) {
        try {
            JSONObject result = authService.verification(phone, code);
            if (result.get("code").equals(200)) {
                return Result.successJson(result, "注册成功");
            } else {
                return Result.errorJson(416, result.get("msg").toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.errorJson(500, "注册失败！");
        }
    }


    /**
     * 南华网销手机登录发送验证码
     *
     * @param
     * @return
     */
    @GetMapping("/msglogin/{phone}")
    @ApiOperation(value = "获取指定userid信息(yk)", notes = "获取微信用户openid")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "手机号", value = "phone", required = true, dataType = "String")
    })
    public Result msglogin(@PathVariable String phone) throws Exception {
        return authService.msglogin(phone);
    }

    // 兼容 query 形式：/saas/auth/msglogin?phone=xxx
    @GetMapping("/msglogin")
    public Result msgloginByQuery(@RequestParam String phone) throws Exception {
        return msglogin(phone);
    }


    /**
     * 南华网销手机登录验证
     *
     * @param
     * @return
     */
    @PostMapping("/msgVerification")
    @ApiOperation(value = "获取指定userid信息(yk)", notes = "获取微信用户openid")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "URL", value = "url", required = true, dataType = "String")
    })
    public Result msgVerification(@MultiRequestBody String phone, @MultiRequestBody String code) {
        try {
            JSONObject result = authService.msgVerification(phone, code);
            return Result.successJson(result, "登录成功");
        } catch (Exception e) {
            return Result.errorJson(500, e.getMessage());
        }
    }

    /**
     * 用户密码登陆
     *
     * @param empno    工号
     * @param password 密码
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "用户登陆(yk)", notes = "登陆成功返回token,刷新时间和过期时间")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "empno", value = "工号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "type", value = "人员类型", required = true, dataType = "String")
    })
    public Result login(@MultiRequestBody String empno, @MultiRequestBody String password, @MultiRequestBody String type) throws Exception {
        if (StringUtils.isBlank(empno) || StringUtils.isBlank(password)) {
            return Result.errorJson("用户名或密码不能为空");
        }

        empno = empno.replace(" ", "");
        password = password.replace(" ", "");
        type = type.replace(" ", "");
        // 密码不允许小于6位
        if (password.length() < 6) {
            return Result.errorJson("密码不能小于6位");
        }
        JSONObject jsonObject = authService.login(empno, password, type);
        return Result.successJson(jsonObject);
    }


    /**
     * 刷新token
     *
     * @Author 杨康
     * @Date 2019/10/31
     */
    @GetMapping("/refresh")
    @ApiOperation(value = "刷新token(yk)", notes = "刷新成功返回token,刷新时间和过期时间")
    @ApiImplicitParam(name = "用户id", value = "userid", required = true, dataType = "String")
    public Result refreshToken(HttpServletRequest request) throws Exception {

        JSONObject tokenJson = authService.refreshToken(IpUtil.getIpAddr(request));
        if (tokenJson == null) {
            return Result.errorJson("请重新登陆");
        }
        return Result.successJson(tokenJson, "刷新token成功");
    }


    /**
     * 解绑微信
     *
     * @return
     * @Param name 用户名
     * @Author 杨康
     * @Date 2019/10/31
     */
    @GetMapping("/delOpenid")
    @ApiOperation(value = "解绑微信(yk)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    public Result delOpenid() throws Exception {

        String openid = JwtTokenUtil.getOpenid();
        String comid = JwtTokenUtil.getComid();

        if (StringUtils.isBlank(openid)) {
            return Result.errorJson("用户信息获取失败");
        }
        authService.delOpenid(openid, comid);

        return Result.successJson("解绑成功");
    }


    /**
     * 获取token
     *
     * @param
     * @return
     */
    @PostMapping("/getToken")
    @ApiOperation(value = "获取openId返回token(yk)", notes = "获取微信用户openid")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Code", value = "code", required = true, dataType = "String"),
            @ApiImplicitParam(name = "客户来源", value = "fromempno", required = true, dataType = "String"),
            @ApiImplicitParam(name = "客户来源素材类型", value = "fromsctype", required = true, dataType = "String"),
            @ApiImplicitParam(name = "客户素材来源编号", value = "fromsno", required = true, dataType = "String")
    })
    public void getToken(HttpServletRequest request, @RequestBody Map<String, String> map, HttpServletResponse response) throws Exception {

        if (map == null) {
            map = new LinkedHashMap<>();
        }

        if (!map.containsKey("fromempno")) {
            map.put("fromempno", "");
        }
        if (!map.containsKey("fromsctype")) {
            map.put("fromsctype", "");
        }
        if (!map.containsKey("fromsno")) {
            map.put("fromsno", "");
        }


        // 获取当前访问的域名头
        String serverNameHeader = IpUtil.getServerNameHeader(request);
        String comid = IpUtil.getComid(serverNameHeader);

        JSONObject jsonObject = new JSONObject();
        if (StringUtils.isBlank(map.get("code"))) {
            jsonObject.put("code", 500);
            jsonObject.put("msg", "获取openid失败，请求code为空");
            jsonObject.put("hint", "请检查公众号回调是否携带code参数");
        } else {
            Result result = authService.getToken(map, comid, IpUtil.getIpAddr(request));
            if (!result.getFlag()) {
                jsonObject.put("code", 500);
                jsonObject.put("data", result.getData());
                jsonObject.put("msg", "获取openid失败");
                jsonObject.put("hint", "请检查appid/secret、回调域名、数据库微信配置");

            } else {
                jsonObject.put("code", 200);
                jsonObject.put("data", result.getData());
                jsonObject.put("msg", "获取openid成功");
            }
        }
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(jsonObject.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }


    /**
     * 获取微信公众号配置信息
     *
     * @param
     * @return
     */
    @GetMapping("/getWxInfo")
    @ApiOperation(value = "获取微信公众号配置信息(yk)", notes = "获取微信用户openid")
    @ApiImplicitParams({
    })
    public void getWxInfo(HttpServletResponse response, HttpServletRequest request) throws Exception {
        response.reset();
        // 获取当前访问的域名头
        String serverName = IpUtil.getServerNameHeader(request);
        String comid = IpUtil.getComid(serverName);

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        PrintWriter out = null;
        try {
            out = response.getWriter();
            try {
                JSONObject wxInfo = authService.getWxInfo(comid);
                if (!wxInfo.containsKey("appid") || StringUtils.isBlank(wxInfo.getString("appid"))) {
                    wxInfo.put("appid", envOrDefault("WX_APPID", ""));
                }
                if (!wxInfo.containsKey("scope") || StringUtils.isBlank(wxInfo.getString("scope"))) {
                    wxInfo.put("scope", "snsapi_userinfo");
                }
                if (!wxInfo.containsKey("ossurl")) {
                    wxInfo.put("ossurl", envOrDefault("OSS_URL", ""));
                }
                if (StringUtils.isBlank(wxInfo.getString("appid"))) {
                    wxInfo.put("msg", "微信配置缺失：appid为空，请先配置数据库smart_com_setting");
                }
                out.append(wxInfo.toJSONString());
            } catch (Exception ex) {
                JSONObject fallback = new JSONObject();
                fallback.put("appid", envOrDefault("WX_APPID", ""));
                fallback.put("scope", "snsapi_userinfo");
                fallback.put("ossurl", envOrDefault("OSS_URL", ""));
                fallback.put("msossurl", envOrDefault("MS_OSS_URL", ""));
                fallback.put("comname", envOrDefault("COM_NAME", "演示机构"));
                fallback.put("stylenum", "1");
                if (StringUtils.isBlank(fallback.getString("appid"))) {
                    fallback.put("msg", "微信配置缺失：appid为空，请先配置数据库smart_com_setting");
                }
                out.append(fallback.toJSONString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * 获取JsapiTicket
     *
     * @param url
     * @return
     */
    @PostMapping("/getJsapiTicket")
    @ApiOperation(value = "获取JsapiTicket(yk)", notes = "获取微信用户openid")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "URL", value = "url", required = true, dataType = "String")
    })
    public Result getJsapiTicket(@MultiRequestBody String url) {
        try {
            String comid = JwtTokenUtil.getComid();
            Map<String, String> sign = MessageTemplateUtils.getSign(url, comid);
            return Result.successJson(sign, "JsapiTicket获取成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.errorJson(500, "JsapiTicket获取失败");
        }
    }


    /**
     * 获取JsapiTicket
     *
     * @param url
     * @return
     */
    @PostMapping("/getJsapiTicketToCP")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "URL", value = "url", required = true, dataType = "String")
    })
    public Result getJsapiTicketToCP(@MultiRequestBody String url) {
        try {
            String comid = IpUtil.getNhwxcomid();
            Map<String, String> sign = MessageTemplateUtils.getSign(url, comid);
            return Result.successJson(sign, "JsapiTicket获取成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.errorJson(500, "JsapiTicket获取失败");
        }
    }


    /**
     * 根据userid获取用户信息
     *
     * @param
     * @return
     */
    @GetMapping("/getinfobyid/{userid}")
    @ApiOperation(value = "获取指定userid信息(yk)", notes = "获取微信用户openid")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "URL", value = "url", required = true, dataType = "String")
    })
    public Result getinfobyid(@PathVariable String userid) {
        try {
            JSONObject result = authService.getinfobyid(userid);
            if (result.get("code").equals("200")) {
                return Result.successJson(result, "获取用户信息成功");
            } else {
                return Result.errorJson(500, "获取用户信息失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Result.errorJson(500, "获取用户信息失败");
        }
    }

    /**
     * 兼容前端通过 query 参数传 userid 的调用方式
     */
    @GetMapping("/getinfobyid")
    public Result getinfobyidByParam(@RequestParam String userid) {
        return getinfobyid(userid);
    }


    /**
     * 测试，获取微信accessToken
     *
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/11/25
     */
    @GetMapping("/getAccessToken")
    @ApiIgnore
    public Result getAccessToken() throws Exception {
        String comid = JwtTokenUtil.getComid();
        return Result.successJson(MessageTemplateUtils.getAccessToken(comid));
    }


    /**
     * 测试，获取微信accessToken
     *
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/11/25
     */
    @GetMapping("/getUerInfo")
    @ApiOperation(value = "获取当前用户信息")
    public Result getUerInfo() throws Exception {
        try {
            JSONObject result = authService.getUerInfo();
            Map pictureList = smartComKjLibwService.pictureList();
            result.put("smartComModelsMobileImgs", pictureList);
            if (!result.containsKey("menulist")) {
                result.put("menulist", new ArrayList<>());
            }
            if (!result.containsKey("banner")) {
                result.put("banner", new ArrayList<>());
            }
            if (!result.containsKey("bar")) {
                result.put("bar", new ArrayList<>());
            }
            if (!result.containsKey("ossurl")) {
                result.put("ossurl", envOrDefault("OSS_URL", ""));
            }
            if (!result.containsKey("msossurl")) {
                result.put("msossurl", envOrDefault("MS_OSS_URL", ""));
            }
            if (!result.containsKey("comname")) {
                result.put("comname", envOrDefault("COM_NAME", "演示机构"));
            }
            return Result.successJson(result, "获取openid成功");
        } catch (Exception e) {
            JSONObject result = new JSONObject();
            ArrayList<Map<String, Object>> menulist = new ArrayList<>();
            menulist.add(new LinkedHashMap<String, Object>() {{
                put("code", "CS006");
                put("name", "客户挖掘");
                put("mklj", "/clientsort");
                put("iconhttp", "");
            }});
            menulist.add(new LinkedHashMap<String, Object>() {{
                put("code", "CS009");
                put("name", "热门活动");
                put("mklj", "/morermhd");
                put("iconhttp", "");
            }});
            result.put("menulist", menulist);
            result.put("ossurl", envOrDefault("OSS_URL", ""));
            result.put("msossurl", envOrDefault("MS_OSS_URL", ""));
            result.put("comname", envOrDefault("COM_NAME", "演示机构"));
            ArrayList<Map<String, Object>> banner = new ArrayList<>();
            banner.add(new LinkedHashMap<String, Object>() {{
                put("pichttp", "");
                put("urltype", "inner");
                put("urlhttp", "");
                put("title", "演示Banner");
            }});
            result.put("banner", banner);
            ArrayList<Map<String, Object>> bar = new ArrayList<>();
            bar.add(new LinkedHashMap<String, Object>() {{
                put("bancode", "CS006");
                put("banname", "客户挖掘");
                put("pichttp", "");
                put("mklj", "/clientsort");
            }});
            bar.add(new LinkedHashMap<String, Object>() {{
                put("bancode", "CS009");
                put("banname", "热门活动");
                put("pichttp", "");
                put("mklj", "/morermhd");
            }});
            result.put("bar", bar);
            LinkedHashMap<String, Object> imgs = new LinkedHashMap<>();
            imgs.put("search", "");
            imgs.put("xxicon", "");
            imgs.put("zxicon", "");
            result.put("smartComModelsMobileImgs", imgs);
            return Result.successJson(result, "兼容返回");
        }
    }

    private String envOrDefault(String key, String defaultValue) {
        String v = System.getenv(key);
        return StringUtils.isBlank(v) ? defaultValue : v;
    }
}
