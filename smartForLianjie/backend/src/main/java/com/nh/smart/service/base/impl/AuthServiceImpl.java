package com.nh.smart.service.base.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nh.smart.annotation.MultiRequestBody;
import com.nh.smart.dao.material.SmartComEmpnoDao;
import com.nh.smart.dao.record.SmartComMuserDao;
import com.nh.smart.entity.base.Result;
import com.nh.smart.entity.material.SmartComEmpno;
import com.nh.smart.entity.record.SmartComMuser;
import com.nh.smart.exception.MyExceptionUtil;
import com.nh.smart.service.base.AuthService;
import com.nh.smart.util.DateUtil;
import com.nh.smart.util.JwtTokenUtil;
import com.nh.smart.util.SMSUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;


/**
 * smart_com_manageuser#渠道管理员表 业务逻辑接口的实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SmartComMuserDao smartComMuserDao;
    @Autowired
    private SmartComEmpnoDao smartComEmpnoDao;

    // 手机端页面地址
    public static String url;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, @Qualifier("SecurityUserDetailsServiceImpl") UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }


    /**
     * 获取token
     *
     * @param
     * @return
     */
    @Override
    public Result getToken(Map<String, String> param, String comid, String ip) throws Exception {
        try {
            Date date = new Date();
            String code = param.get("code");
            Map<String, String> map=smartComMuserDao.getWxInfo(comid);
            String appid = map.get("appid");
            String secret =map.get("appsecrt");
            // 请求获取openid和access_token
            String tokenURL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid + "&secret=" + secret + "&code=" + code + "&grant_type=authorization_code";
            RestTemplate restTemplate = new RestTemplate();
            String tokenResponse = restTemplate.getForObject(tokenURL, String.class);

            JSONObject jsonRes = JSONObject.parseObject(tokenResponse);

            // 如果有errcode说明请求失败
            if (StringUtils.isNotEmpty(jsonRes.getString("errcode"))) {
                return Result.errorJson(500, "授权请求获取失败");
            }
            // 获取access_token
            String accessTken = jsonRes.getString("access_token");
            // 获取openid
            String openId = jsonRes.getString("openid");
            // 通过access_token拉取用户信息
            String userinfoURL = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessTken + "&openid=" + openId + "&lang=zh_CN";
            String userinfoResponse = restTemplate.getForObject(userinfoURL, String.class);
            JSONObject userinfoJson = JSONObject.parseObject(userinfoResponse);

            SmartComMuser muser = smartComMuserDao.selectByUserId(openId, comid);

            // 如果是未登录过用户，添加用户信息到手机用户表
            if (muser == null) {
                muser = new SmartComMuser();
                // 如果是新用户，进行数据绑定
                // 设置用户信息
                muser.setOpenid(openId);
                muser.setRytype("Y");
                String nickname = new String(userinfoJson.getString("nickname").getBytes("ISO-8859-1"), "UTF-8");
                String headimgurl = new String(userinfoJson.getString("headimgurl").getBytes("ISO-8859-1"), "UTF-8");
                String province = new String(userinfoJson.getString("province").getBytes("ISO-8859-1"), "UTF-8");
                String city = new String(userinfoJson.getString("city").getBytes("ISO-8859-1"), "UTF-8");
                String sex = new String(userinfoJson.getString("sex").getBytes("ISO-8859-1"), "UTF-8");

                if (sex.equals("1")) {
                    sex = "2";
                } else if (sex.equals("2")) {
                    sex = "1";
                }
                muser.setNickname(nickname);
                int userid = Integer.valueOf(this.getUserId());
                muser.setUserid(userid);
                muser.setHeadimg(headimgurl);
                muser.setComid(comid);
                muser.setRegtime(date);
                muser.setNickname(nickname);
                muser.setStatus("0");
                muser.setCustprovince(province);
                muser.setCustcity(city);
                muser.setFromempno("积分商城");
                muser.setFromsctype("");
                muser.setFromsno("");
                muser.setCustsex(sex);
                muser.setLastlogintime(date);
                smartComMuserDao.insert(muser);
            } else {
                //更新登录时间字段
                QueryWrapper<SmartComMuser> upwrapper = new QueryWrapper<>();
                upwrapper.eq("userid", muser.getUserid())
                        .eq("comid", comid);
                muser.setLastlogintime(date);
                smartComMuserDao.update(muser, upwrapper);
            }

            // 生成token，以Bearer开头
            final String token = JwtTokenUtil.getToken_start() + JwtTokenUtil.generateToken(openId + "&" + comid + "&S", ip);
            // 获取token的刷新时间
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(date);
            calendar1.add(Calendar.MILLISECOND, JwtTokenUtil.getRefresh().intValue());
            Long refreshTime = calendar1.getTimeInMillis();
            // 获取token过期时间
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(date);
            calendar2.add(Calendar.MILLISECOND, JwtTokenUtil.getExpiration().intValue());
            Long expirationTime = calendar2.getTimeInMillis();

            // 封装数据返回
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("token", token);
            jsonObject.put("refreshTime", refreshTime);
            jsonObject.put("expireTime", expirationTime);

            return Result.successJson(jsonObject, "获取openid成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.errorJson(500, "获取openid异常");
        }
    }


    /**
     * 绑定用户
     *
     * @param empno    工号
     * @param password 密码
     * @param type     人员类型
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject login(@MultiRequestBody String empno, @MultiRequestBody String password, @MultiRequestBody String type) throws Exception {
        //绑定时间
        Date bdtime = new Date();
        String openid = JwtTokenUtil.getOpenid();
//      String openid="oC1620207gyHNbXC3_0nVX5SGkCw";
        //渠道编码
        String comid = JwtTokenUtil.getComid();
//      String comid="NHSX";
        //验证内外勤登录开关
        List<String> setcode = new ArrayList();
        setcode.add("userange");
        //查询是否为老用户解绑用
        SmartComMuser ywypojo;
        SmartComMuser newpojo = smartComMuserDao.selectByUserId(openid, comid);
        if (type.equals("N")) {
          //查询业务员用户
          SmartComEmpno wUser = smartComEmpnoDao.verify(empno, password, comid);
          if (wUser == null) {
            throw MyExceptionUtil.mxe("用户名或密码错误");
          } else {
            //用于手机号码登录时方便后续操作使用
            empno = wUser.getEmpno();
            //查看手机用户表是否有工号这个用户
            ywypojo = smartComMuserDao.getByEmpno(empno, comid);
            if (ywypojo == null && !newpojo.getRytype().equals("Y")) {
              throw MyExceptionUtil.mxe("请使用已绑定工号登录");
            }
            if (ywypojo != null && !ywypojo.getRytype().equals("Y")) {
              //如果有对比用户id
              if (ywypojo.getUserid().equals(newpojo.getUserid())) {
                //在对比openid是否跟当前登录人一致
                if (ywypojo.getOpenid().equals(newpojo.getOpenid())) {
                } else {
                  throw MyExceptionUtil.mxe("该工号已与其他业务员绑定");
                }
              } else {
                throw MyExceptionUtil.mxe("该工号已与其他业务员绑定");
              }
              //如果是游客才执行绑定
            } else if (ywypojo != null && ywypojo.getRytype().equals("Y") && newpojo.getRytype().equals("Y")) {
              newpojo.setRytype("N");
              QueryWrapper<SmartComMuser> upwrapper = new QueryWrapper<>();
              upwrapper.eq("userid", newpojo.getUserid())
                .eq("comid", comid);
              if (type.equals("N")) {
                Map map = new HashMap();
                map.put("comid", comid);
                map.put("empno", empno);
                map.put("nickname", newpojo.getNickname());
                map.put("headimg", newpojo.getHeadimg());
                map.put("openid", newpojo.getOpenid());
                smartComEmpnoDao.updateWeixin(map);
              }
              newpojo.setLastlogintime(bdtime);
              newpojo.setBdtime(bdtime);
              newpojo.setEmpno(empno);
              ywypojo.setEmpno("");
              QueryWrapper<SmartComMuser> ywywrapper = new QueryWrapper<>();
              ywywrapper.eq("userid", ywypojo.getUserid())
                                .eq("comid", comid);
              smartComMuserDao.update(ywypojo, ywywrapper);
              smartComMuserDao.update(newpojo, upwrapper);
            } else if (ywypojo != null && !ywypojo.getEmpno().equals(newpojo.getEmpno())) {
                throw MyExceptionUtil.mxe("请使用已绑定工号登录");
            }
          }
        } else {
            throw MyExceptionUtil.mxe("业务员类型错误");
        }
        SmartComMuser pojo = smartComMuserDao.selectByUserId(openid, comid);
        QueryWrapper<SmartComMuser> upwrapper = new QueryWrapper<>();
        upwrapper.eq("userid", pojo.getUserid())
                .eq("comid", comid);
        if ("Y".equals(pojo.getRytype())) {
            //这里执行绑定sql
            pojo.setBdtime(bdtime);
            pojo.setEmpno(empno);
            pojo.setLastlogintime(bdtime);
            pojo.setRytype(type);
            smartComMuserDao.update(pojo, upwrapper);
            //如果是外勤人员就更新业务员表中的微信相关字段
            if (type.equals("N")) {
                Map map = new HashMap();
                map.put("comid", comid);
                map.put("empno", empno);
                map.put("nickname", pojo.getNickname());
                map.put("headimg", pojo.getHeadimg());
                map.put("openid", pojo.getOpenid());
                smartComEmpnoDao.updateWeixin(map);
            }
        }
        pojo.setLastlogintime(bdtime);
        smartComMuserDao.update(pojo, upwrapper);
        JSONObject jsonObject = new JSONObject();
        return jsonObject;
    }

    /**
     * 获取用户信息
     *
     * @param user
     * @return
     */
    @Override
    public Result getUser(SmartComMuser user) throws Exception {
        if (user == null) {
            return Result.errorJson("用户信息获取失败！");
        }
        Result result = new Result<>();
        Map<String, Object> userMap;
        switch (user.getRytype()) {
            case "W":
                userMap = smartComEmpnoDao.getEmpno(user.getEmpno(), user.getComid());
                if (userMap == null) {
                    user.setRytype("Y");
                    result.setCode(200);
                    //网销后台如果没查到人员信息返回游客
                    return result.setData(user);
                }
                if(userMap.get("status").toString().equals("6") && "NHWX".equals(JwtTokenUtil.getComid())){
                    userMap.put("rytype", "M");
                }else {
                    userMap.put("rytype", user.getRytype());
                }
                userMap.put("headimg", user.getHeadimg());
                userMap.put("nickname", user.getNickname());
                userMap.put("userid", user.getUserid());
                userMap.put("salecomid", "");
                userMap.put("promotionprice", user.getPromotionprice());
                result.setData(userMap);
                break;
            default:
                result.setData(user);
                break;

        }
        result.setCode(200);
        result.setMsg("查询信息成功");
        return result;
    }

    /**
     * 刷新token
     *
     * @param
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject refreshToken(String ip) throws Exception {
        String type = JwtTokenUtil.getType();
        String s = "";

        // 微信用户根据openid刷新token
        if ("S".equals(type)) {
            s = JwtTokenUtil.getOpenid();
        }
        String comid = JwtTokenUtil.getComid();
        // 生成token，以Bearer开头
        final String token = JwtTokenUtil.getToken_start() + JwtTokenUtil.generateToken(s + "&" + comid + "&" + type, ip);

        Date date = new Date();
        // 获取token的刷新时间
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date);
        calendar1.add(Calendar.MILLISECOND, JwtTokenUtil.getRefresh().intValue());
        long refreshTime = calendar1.getTimeInMillis();

        // 获取token过期时间
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date);
        calendar2.add(Calendar.MILLISECOND, JwtTokenUtil.getExpiration().intValue());
        long expirationTime = calendar2.getTimeInMillis();
        JSONObject jsonObject = new JSONObject();
        // 封装数据返回
        jsonObject.put("token", token);
        jsonObject.put("refreshTime", refreshTime);
        jsonObject.put("expireTime", expirationTime);
        return jsonObject;
    }


    /**
     * 解绑微信
     *
     * @param
     * @throws Exception
     */
    @Override
    public void delOpenid(String openid, String comid) throws Exception {
        // 清除redis缓存
        Integer userid = JwtTokenUtil.getUserid();
        redisTemplate.delete(openid + userid);
        SmartComMuser pojo = smartComMuserDao.selectByUserId(openid, comid);
        smartComMuserDao.delOpenid(openid, comid);
        smartComMuserDao.delWeiXin(pojo.getEmpno(), comid);
    }


    /**
     * 获取微信配置
     *
     * @return
     */
    @Override
    public JSONObject getWxInfo(String comid) throws Exception {
        JSONObject jsonObject = new JSONObject();
        Map<String, String> map =smartComMuserDao.getWxInfo(comid);
        String ossurl = map.get("ossurl");
        String applogo = "NHWX/STYLEIMGS/comimg/logo/2020-03-21/QDLOGO20200321163853351071.jpg";
        String sharelogo ="NHWX/STYLEIMGS/comimg/logo/2020-03-21/QDLOGO20200321163840334178.jpg";
        String appsysname = "展业助手";
        String userange = "W";
        String comlogo = "NHWX/STYLEIMGS/comimg/logo/2020-03-21/QDLOGO20200321163900106912.jpg";
        String mplogo = "NHWX/STYLEIMGS/comimg/logo/2020-03-21/QDLOGO20200321163925587251.jpg";
        jsonObject.put("appid", map.get("appid"));
        jsonObject.put("scope", "snsapi_userinfo");
        jsonObject.put("stylenum",map.get("stylenum"));
        String leadimg = "NHSX/ICON/00516/20200323194442.png";
        jsonObject.put("ossurl",map.get("ossurl"));
        jsonObject.put("applogo", ossurl + applogo);
        jsonObject.put("leadimg", ossurl + leadimg);
        jsonObject.put("sharelogo", ossurl + sharelogo);
        jsonObject.put("comlogo", ossurl + comlogo);
        jsonObject.put("mplogo", ossurl + mplogo);
        jsonObject.put("appsysname", appsysname);
        jsonObject.put("userange", userange);
        return jsonObject;
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @Override
    public JSONObject getUerInfo() throws Exception {
        String comid = JwtTokenUtil.getComid();
        String type = JwtTokenUtil.getType();
        SmartComMuser muser = new SmartComMuser();

        // 微信用户根据openid查询用户信息
        if ("S".equals(type)) {
            String openId = JwtTokenUtil.getOpenid();
            muser = smartComMuserDao.selectByUserId(openId, comid);
        }
        JSONObject jsonObject = new JSONObject();
        Result userresult = this.getUser(muser);
        jsonObject.put("user", userresult.getData());
        jsonObject.put("comid", muser.getComid());
        return jsonObject;
    }

    /**
     * 根据用户userid
     *
     * @param userid
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject getinfobyid(String userid) throws Exception {
        JSONObject result = new JSONObject();
        SmartComMuser muser = smartComMuserDao.selectByUser(userid, JwtTokenUtil.getComid());
        if (muser == null) {
            result.put("code", "500");
            return result;
        }
        Map map = new HashMap();
        if (!muser.getRytype().equals("Y")) {
            String empno = "";
            if (muser.getRytype().equals("W")) {
                empno = muser.getEmpno();
            }
            muser.setEmpno(empno);
        } else {
            map.put("cardmobile", "");
            map.put("wxnumber", "");
        }

        Result userresult = this.getUser(muser);
        result.put("user", userresult.getData());
        result.put("code", "200");
        return result;
    }

    /**
     * 获取自增用户id
     *
     * @return
     */
    @Override
    public synchronized String getUserId() throws Exception {
        String maxEmpno = smartComMuserDao.getMaxUserId();
        String num;

        // 如果为空，给一个初始值
        if (StringUtils.isEmpty(maxEmpno)) {
            num = "10000001";
        } else {
            // 不为空则加1保存
            num = String.valueOf(Integer.parseInt(maxEmpno) + 1);
        }
        return num;
    }

    /**
     * 注册发送短信验证码
     *
     * @return
     */
    @Override
    public Result register(String phone) throws Exception {
        String openid = JwtTokenUtil.getOpenid();
        String comid = JwtTokenUtil.getComid();
        SmartComMuser muser = smartComMuserDao.selectByUserId(openid, comid);

        if (!muser.getRytype().equals("Y")) {
            return Result.errorJson(416, "您已为注册用户，请使用绑定手机登录");
        }

        String hy = smartComMuserDao.isRegister(phone, comid);

        if (!hy.equals("0")) {
            return Result.errorJson(416, "该手机号已绑定其他用户");
        }

        String ywy = smartComMuserDao.empnoIsRegister(phone, comid);

        if (!ywy.equals("0")) {
            return Result.errorJson(416, "该手机号已绑定业务员请直接登录");
        }
        return sendYZM(muser, phone, true);
    }


    /**
     * 注册验证
     *
     * @return
     */
    @Override
    public JSONObject verification(String phone, String code) throws Exception {

        JSONObject resultjson = new JSONObject();
        String openid = JwtTokenUtil.getOpenid();
        String comid = JwtTokenUtil.getComid();
        SmartComMuser muser = smartComMuserDao.selectByUserId(openid, comid);

        if (!muser.getMobile().equals(phone)) {
            resultjson.put("code", 416);
            resultjson.put("msg", "验证手机号码不一致");
            return resultjson;
        }

        String hy = smartComMuserDao.isRegister(phone, comid);

        if (!muser.getRytype().equals("Y")) {
            resultjson.put("code", 416);
            resultjson.put("msg", "您已为注册用户或渠道业务员，请使用绑定手机登录！");
            return resultjson;
        }

        if (!hy.equals("0")) {
            resultjson.put("code", 416);
            resultjson.put("msg", "该手机号已绑定其他用户");
            return resultjson;
        }

        String ywy = smartComMuserDao.empnoIsRegister(phone, comid);

        if (!ywy.equals("0")) {
            resultjson.put("code", 416);
            resultjson.put("msg", "该手机号已绑定业务员请直接登录");
            return resultjson;
        }

        Date currentTime = new Date();

        Date YzmDate = DateUtil.getAfterDate(muser.getYzmtime(), 0, 0, 0, 0, 10,
                0);// 验证码过期时间
        // 校验验证码是否过期
        if (currentTime.before(YzmDate)) {
            if (!code.equals("") || !code.equals(null)) {
                if (code.equals(muser.getYzm())) {
                    muser.setRytype("M");
                    //更新登录时间字段
                    QueryWrapper<SmartComMuser> upwrapper = new QueryWrapper<>();
                    upwrapper.eq("userid", muser.getUserid())
                            .eq("comid", comid);
                    smartComMuserDao.update(muser, upwrapper);
                    resultjson.put("code", 200);
                    resultjson.put("msg", "注册成功");
                    return resultjson;
                } else {
                    resultjson.put("code", 416);
                    resultjson.put("msg", "验证码错误");
                    return resultjson;
                }
            } else {
                resultjson.put("code", 416);
                resultjson.put("msg", "验证码不能为空");
                return resultjson;
            }
        } else {
            resultjson.put("code", 416);
            resultjson.put("msg", "验证码过期，请重新发送");
            return resultjson;
        }


    }

    /**
     * 手机登录发送短信
     *
     * @return
     */
    @Override
    public Result msglogin(String phone) throws Exception {
        String openid = JwtTokenUtil.getOpenid();
        String comid = JwtTokenUtil.getComid();
        SmartComMuser muser = smartComMuserDao.selectByUserId(openid, comid);

        //游客对应操作
        if (muser.getRytype().equals("Y")) {
            //查看是否此手机号被别人注册过
            String hy = smartComMuserDao.isRegister(phone, comid);
            if (!hy.equals("0")) {
                return Result.errorJson(416, "该手机号已绑定其他用户");
            }
            //查看此游客是否是公司内部人员
            String ywy = smartComMuserDao.empnoIsRegister(phone, comid);
            if (ywy.equals("0")) {
                return Result.errorJson(416, "未找到对应号码信息，请先注册");
            }
        } else {
            SmartComEmpno empno = smartComEmpnoDao.selectByEmpno(muser.getEmpno(), comid);
            if (empno == null) {
                return Result.errorJson(416, "未查到业务员信息");
            }
            if (!phone.equals(empno.getPhone()) && !phone.equals(muser.getMobile())) {
                return Result.errorJson(416, "请使用绑定手机号码登录");
            }
        }
        return sendYZM(muser, phone, true);
    }


    /**
     * 手机登录验证
     *
     * @return
     */
    @Override
    public JSONObject msgVerification(String phone, String code) throws Exception {
        String openid = JwtTokenUtil.getOpenid();
        String comid = JwtTokenUtil.getComid();
        SmartComMuser muser = smartComMuserDao.selectByUserId(openid, comid);
        //外勤工号
        String wmpno = "";
        //绑定时间
        Date bdtime = new Date();

        if (!phone.equals(muser.getMobile())) {
            throw MyExceptionUtil.mxe("请先发送验证码或使用绑定号码登录！");
        }

        Date currentTime = new Date();

        Date YzmDate = DateUtil.getAfterDate(muser.getYzmtime(), 0, 0, 0, 0, 10,
                0);// 验证码过期时间
        // 校验验证码是否过期
        if (currentTime.before(YzmDate)) {
            if (code != null || !code.equals("")) {
                if (code.equals(muser.getYzm())) {

                } else {
                    throw MyExceptionUtil.mxe("验证码错误");
                }
            } else {
                throw MyExceptionUtil.mxe("验证码不能为空");
            }
        } else {
            throw MyExceptionUtil.mxe("验证码过期，请重新发送");
        }

        //游客调用此接口说明已经为公司业务员进行绑定 不是跳出
        if (muser.getRytype().equals("Y")) {
            //现在是游客绑定业务员流程
            //1.查询外勤用户
            SmartComEmpno wUser = smartComEmpnoDao.verifyPhone(phone, comid);
            if (wUser == null) {
                throw MyExceptionUtil.mxe("请先注册！");
            }
            //现在是游客绑定业务员流程
            //外勤工号
            wmpno = wUser.getEmpno();
            //查看手机用户表是否有外勤工号这个用户
            SmartComMuser ywypojo = smartComMuserDao.getByEmpno(wmpno, comid);
            if (ywypojo != null && !ywypojo.getRytype().equals("Y")) {
                //如果有对比用户id
                if (ywypojo.getUserid().equals(muser.getUserid())) {
                    //在对比openid是否跟当前登录人一致
                    if (ywypojo.getOpenid().equals(muser.getOpenid())) {
                    } else {
                        throw MyExceptionUtil.mxe("该工号已与其他业务员绑定");
                    }
                } else {
                    throw MyExceptionUtil.mxe("该工号已与其他业务员绑定");
                }
                //如果是游客才执行绑定
            } else if (ywypojo != null && ywypojo.getRytype().equals("Y") && muser.getRytype().equals("Y")) {
                muser.setRytype("W");
                QueryWrapper<SmartComMuser> upwrapper = new QueryWrapper<>();
                upwrapper.eq("userid", muser.getUserid())
                        .eq("comid", comid);
                Map map = new HashMap();
                map.put("comid", comid);
                map.put("empno", wmpno);
                map.put("nickname", muser.getNickname());
                map.put("headimg", muser.getHeadimg());
                map.put("openid", muser.getOpenid());
                smartComEmpnoDao.updateWeixin(map);
                muser.setLastlogintime(bdtime);
                muser.setBdtime(bdtime);
                muser.setEmpno(wmpno);
                ywypojo.setEmpno("");
                QueryWrapper<SmartComMuser> ywywrapper = new QueryWrapper<>();
                ywywrapper.eq("userid", ywypojo.getUserid())
                        .eq("comid", comid);
                smartComMuserDao.update(ywypojo, ywywrapper);
                smartComMuserDao.update(muser, upwrapper);

            } else if (ywypojo != null && !ywypojo.getEmpno().equals(muser.getEmpno())) {
                throw MyExceptionUtil.mxe("请使用已绑定工号登录");
            }
            QueryWrapper<SmartComMuser> upwrapper = new QueryWrapper<>();
            upwrapper.eq("userid", muser.getUserid())
                    .eq("comid", comid);
            if (muser.getRytype().equals("Y")) {
                //这里执行绑定sql
                muser.setBdtime(bdtime);
                muser.setEmpno(wmpno);
                muser.setLastlogintime(bdtime);
                muser.setRytype("W");
                smartComMuserDao.update(muser, upwrapper);
                //如果是外勤人员就更新业务员表中的微信相关字段
                Map map = new HashMap();
                map.put("comid", comid);
                map.put("empno", wmpno);
                map.put("nickname", muser.getNickname());
                map.put("headimg", muser.getHeadimg());
                map.put("openid", muser.getOpenid());
                smartComEmpnoDao.updateWeixin(map);

            }
            muser.setLastlogintime(bdtime);
            smartComMuserDao.update(muser, upwrapper);
        }

        return null;
    }


    /**
     * 更换手机号发送验证码
     *
     * @Param: [phone]
     * @return: com.saascs.common.entity.Result
     * @Author: 张宁
     * @Date: 2020/2/28
     */
    @Override
    public Result changePhone(String phone) throws Exception {
        String openid = JwtTokenUtil.getOpenid();
        String comid = JwtTokenUtil.getComid();
        QueryWrapper<SmartComMuser> smartComMuserQueryWrapper = new QueryWrapper<>();
        smartComMuserQueryWrapper.eq("comid", comid).ne("rytype", "Y").eq("openid", openid).eq("status", "0");
        SmartComMuser muser = smartComMuserDao.selectOne(smartComMuserQueryWrapper);

        if (phone.equals(muser.getMobile())) {
            return Result.errorJson(416, "不可更换为当前使用的手机号");
        }

        String hy = smartComMuserDao.isRegister(phone, comid);
        if (!"0".equals(hy)) {
            return Result.errorJson(416, "该手机号已被使用");
        }

        String ywy = smartComMuserDao.empnoIsRegister(phone, comid);

        if (!"0".equals(ywy)) {
            return Result.errorJson(416, "该手机号为业务员手机号，不可使用");
        }

        return sendYZM(muser, phone, false);
    }


    /**
     * 发送/更新验证码
     *
     * @param muser
     * @param phone 手机号
     * @param flag  是否更换手机号
     * @return
     * @throws Exception
     * @author 杨康
     * @date 2020/03/18
     */
    private Result sendYZM(SmartComMuser muser, String phone, Boolean flag) throws Exception {
        String comid = JwtTokenUtil.getComid();
        Date yzmtime = muser.getYzmtime();

        Date currentTime = new Date();
        // 验证码过期时间
        Date YzmDate = DateUtil.getAfterDate(yzmtime, 0, 0, 0, 0, 1, 0);
        if (YzmDate.before(currentTime) || yzmtime == null) {
            // 生成4位随机验证码
            String code = SMSUtils.createNum();
            if (flag) {
                muser.setMobile(phone);
            }
            muser.setYzm(code);
            muser.setYzmtime(new Date());
            String ssm = SMSUtils.getSsm(phone, code);
            // 发送短信
            if (ssm == null || "".equals(ssm)) {
                return Result.errorJson(416, "短信发送失败");
            }
            //更新登录时间字段
            QueryWrapper<SmartComMuser> upwrapper = new QueryWrapper<>();
            upwrapper.eq("userid", muser.getUserid()).eq("comid", comid);
            smartComMuserDao.update(muser, upwrapper);
            return Result.successJson("短信发送成功");
        }
        return Result.errorJson(416, "1分钟后可再次请求发送短信");
    }
}
