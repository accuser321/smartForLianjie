/*
 * @Author: 王鹏
 * @Date: 2019-10-31 09:24:19
 * @LastEditors: 刘格优
 * @LastEditTime: 2020-04-01 10:41:01
 */

import axios from "@/lib/api.request";

// 获取token
export const getToken = data => axios.post("/auth/getToken", data);

// 登陆
export const login = data => axios.post("/auth/login", data);

// 验证码注册
export const getregisteryzm = params =>
  axios.get("/saas/auth/register", {
    params
  });

// 注册
export const register = data => axios.post("/auth/verification", data);

// 登录验证码
export const getmsgloginyzm = params =>
  axios.get("/saas/auth/msglogin", {
    params
  });

// 验证码登录
export const yzmlogin = data => axios.post("/auth/msgVerification", data);

// 刷新token接口
export const refresh = () => axios.get("/auth/refresh");

// 获取APPID
export const getWxURLInfo = () => axios.get("/auth/getWxInfo");

// 获取菜单
// export const getInit = () => axios.get("/saas/abtComSlotInvoke/init");
//获取当前用户信息
export const getUserInfo = () => axios.get("/auth/getUerInfo");

// 退出登录
export const quitlogin = () => axios.get("/auth/delOpenid");

// 首页获取访次数据
export const getNowDayCount = () =>
  axios.get("/abt/abtComKjActionRecord/getNowDayCount");

// 获取游客用户信息
export const getykinfo = params =>
  axios.get("/saas/auth/getinfobyid", {
    params
  });

// 从app跳转获取用户信息
export const access = data => axios.post("/saas/dr/access", data);

// 银行卡信息
export const selectYHByEmpno = params =>
  axios.get("/hx/abtComEmpno/selectYHByEmpno", {
    params
  });
// 佣金明细
export const selectGZByEmpno = params =>
  axios.get("/hx/abtComEmpno/selectGZByEmpno", {
    params
  });
// 个人中心信息
export const selectGRZXByEmpno = params =>
  axios.get("/hx/abtComEmpno/selectGRZXByEmpno", {
    params
  });
// 手机端考核预警接口  get 请求
export const selectKHbyEmpno = params =>
  axios.get("/core/abtComUserCard/selectKHbyEmpno", { params });
// 识别银行卡信息
export const bankCardOcr = data => axios.post("/saas/ocr/bankCardOcr", data);
// 保存银行卡信息 /hx/abtComEmpno/upYHKMessage
export const upYHKMessage = data =>
  axios.post("/hx/abtComEmpno/upYHKMessage", data);

// 建议反馈
export const addComplain = data =>
  axios.post("/abt/abtComKjComplain/addComplain", data);

// 建议意见列表
export const complainlist = params =>
  axios.get("/abt/abtComKjComplain/complainlist", { params });

// 积分明细
export const jfInfo = data => axios.post("saas/abtComEmpnoJifen/jfList", data);
// 获取登陆积分
export const getJf = () => axios.get("saas/abtComEmpnoJifen/getJF");
// 获取我的积分
export const getJfSum = () => axios.get("saas/abtComEmpnoJifen/jfSum");
// 首页智能提醒显示数据
export const statistics = () => axios.get("/core/abtComAlertmsg/statistics");
// 合同查看跳转
export const gethtck = () => axios.get("/hx/abtComEmpnoApply/getcontract");
// 热门活动列表
export const getrmhdlist = data =>
  axios.post("/abt/abtComKjActivity/selectActivity", data);
// 热门活动详情
export const getrmhddetail = params =>
  axios.get("/abt/abtComKjActivity/getActivity", {
    params
  });
// NH首页列表
export const getNHlb = () => axios.get("/hx/abtComHxCpList/ShouYelist");

// NH 我的列表
export const getEmpnoPersonal = params =>
  axios.get("/core/abtComUserCard/getEmpnoPersonal", {
    params
  });
// 修改密码
export const upEmpnoPwd = data =>
  axios.put("/core/abtComUserCard/upEmpnoPwd", data);
// 修改手机号
export const upEmpnoPhone = data =>
  axios.put("/core/abtComUserCard/upEmpnoPhone", data);
// 发送更换手机号验证码
export const changePhone = params =>
  axios.get("/saas/auth/changePhone", {
    params
  });
// 查询个人信息
export const getPersonalData = params =>
  axios.get("/core/abtComUserCard/getPersonalData", {
    params
  });
// 提现
export const upMount = data => axios.put("/core/abtComUserCard/upMount", data);
// 提现列表未审核
export const getTXRecord = data =>
  axios.post("/core/abtComUserCard/getTXRecord", data);
// 提现列表全部
export const getTXRecords = data =>
  axios.post("/core/abtComUserCard/getTXRecords", data);
// 保存个人信息
export const updatePersonalData = data =>
  axios.put("/core/abtComUserCard/updatePersonalData", data);
// 执业证信息  /{empno}  执业证 get
export const getZYZMessage = params =>
  axios.get("/core/abtComUserCard/getZYZMessage", {
    params
  });
// 是否显示推广费 /{flag}
export const isShowPromotionprice = params =>
  axios.get("/core/abtComUserCard/isShowPromotionprice", { params });
// 实名认证信息 /{empno}  身份证 get
export const getSFZMessage = params =>
  axios.get("/core/abtComUserCard/getSFZMessage", {
    params
  });
// 优惠券列表
export const selectempCouponList = params =>
  axios.get("/cpk/abtComYxhdCouponlist/selectempCouponList", {
    params
  });
// 公司通知
export const selectKFMsgList = data =>
  axios.post("/abt/abtComKjYqhSignup/selectKFMsgList", data);
// 未读消息
export const wdmsg = params =>
  axios.get("abt/abtComKjYqhSignup/selectWDKFMsg", {
    params
  });
/*
 * @Author: 李宗哲
 * @Moudle: 消息推送
 */
/* ------------------------- 消息推送------------------- */
// 消息推送
export const getxxtslist = data =>
  axios.post("/abt/abtComKjYqhSignup/selectPush", data);
/*
 * @Author: 格优
 * @Moudle: 优惠券展示
 */
/* -------------------------优惠券展示列表------------------- */
export const grantCoupon = params =>
  axios.get("/cpk/abtComYxhdCouponlist/grantCoupon", {
    params
  });
// 优惠券详情
export const useFWCoupon = params =>
  axios.get("/cpk/abtComYxhdCouponlist/useFWCoupon", {
    params
  });
