package com.nh.smart.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局常量值类
 */
@Configuration
public class BaseConstants {

    // 分页的总条数
    public static final String TOTAL = "total";

    // 分页的数据
    public static final String ROWS = "rows";

    // 分页的总页数
    public static final String TOTAL_PAGE = "totalpage";

    // redis中用户map中的个人信息
    public static final String USER_INFO = "info";

    // redis中用户map中的对应业务员信息
    public static final String USER_YWY = "ywy";

    // redis中用户map中的权限信息
    public static final String USER_PERMISSION = "permission";

    // 管理平台地址
    public static String SAASMS_URL;

    // 手机端页面地址
    public static String SAASCV_URL;

    // 管理平台OSS路径地址，渠道平台OSS路径使用SlotInvokeDao中的方法查询，请勿使用此路径
    public static String MSOSS_URL;

    // 同步管理平台素材约定密钥
    public static final String QDTB_SC_KEY = "saastb123456";

    // 产品投保参数签名
    public static final String CP_TB_SIGN = "nhwx!@#$1234";

    // 同步管理平台素材加密字符串参数key值
    public static final String TB_SC_SIGN = "sign";

    // 同步管理平台素材加密字符串参数type值
    public static final String TB_SC_TYPE = "type";

    // 名片照片对应的枚举值编码
    public static final String CARD_ENUM = "808";

    // 客户活跃度时间区间
    public static final Integer KH_DAY = 15;

    // 文本消息
    public static final String MESSAGE_TEXT = "text";

    // 图片消息
    public static final String MESSAGE_IMAGE = "image";

    // 语音消息
    public static final String MESSAGE_VOICE = "voice";

    // 视频
    public static final String MESSAGE_VIDEO = "video";

    // 小视屏
    public static final String MESSAGE_SHORTVIDEO = "shortvideo";

    // 地理位置
    public static final String MESSAGE_LOCATION = "location";

    // 链接
    public static final String MESSAGE_LINK = "link";

    // 推送
    public static final String MESSAGE_EVENT = "event";

    // 事件类型：关注
    public static final String MESSAGE_SUBSCRIBE = "subscribe";

    // 事件类型：取消关注
    public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";

    // 事件类型：点击
    public static final String MESSAGE_CLICK = "CLICK";

    // 事件类型：自定义菜单url视图
    public static final String MESSAGE_VIEW = "VIEW";

    //渠道编码
    public static final String QD_COMID = "comid";

    // 上传图片路径集合
    public static final Map<String, String> ossmap;

    // 优惠券条件集合
    public static final Map<String, String> conditionmap;

    // 静态代码块，将前缀与对应的OSS文件目录放入静态map中
    static {
        ossmap = new HashMap<>();
        // ----------------------系统----------------------
        // 公司logo
        ossmap.put("logo", "Sys_Source/logo");
        // 手机端功能图片
        ossmap.put("mobileicon", "Sys_Source/icon/Mobile_Icon");
        // 管理端功能图片
        ossmap.put("manageicon", "Sys_Source/icon/Manage_Icon");
        // 增员背景图
        ossmap.put("zyimg", "Sys_Source/zyBackground");
        // 微官网
        ossmap.put("website", "Sys_Source/webSite");
        // 默认风采照片
        ossmap.put("defaultFcPhotp", "Sys_Source/defaultFcPhotp");
        // 帮助文档
        ossmap.put("helpmenu", "Sys_Source/helpmenu");
        // 样式图标
        ossmap.put("BMicon", "ICON");
        // 系统样式图片
        ossmap.put("styleimg", "STYLEIMGS/comimg");
        // 渠道手机端与管理端logo
        ossmap.put("qdlogo", "STYLEIMGS/comimg/logo");

        // -----------------------爱保单-----------------------
        // 条款
        ossmap.put("TK", "BD_Source/XZ");
        // 保单图片
        ossmap.put("policy", "BD_Source/BD");
        // 增加客户二维码
        ossmap.put("bdqrcode", "BD_Source/qrcode");

        // -----------------------客经-----------------------
        // 文章
        ossmap.put("WZ", "KJ_Source/WZ");
        // 贺卡背景图
        ossmap.put("HK", "KJ_Source/HK/BackgroundImg");
        // 贺卡语音
        ossmap.put("HKYY", "KJ_Source/HK/Voice");
        // 海报
        ossmap.put("HB", "KJ_Source/HB");
        // 邀请函
        ossmap.put("YQH", "KJ_Source/YQH");
        // 业务员名片风采照片
        ossmap.put("mp", "KJ_Source/CARD/fcphoto");
        // 业务员名片二维码
        ossmap.put("mpqrcode", "KJ_Source/CARD/qrcode");
        // 业务员名片头像
        ossmap.put("mptx", "KJ_Source/CARD/headimg");
        // 投诉建议图片
        ossmap.put("tsjy", "KJ_Source/complain");

        // ----------------------核心----------------------
        // 一寸免冠照
        ossmap.put("headone", "Emp_Source/headone");
        // 身份证/证件
        ossmap.put("idcard", "Emp_Source/idcard");
        // 学历
        ossmap.put("xueli", "Emp_Source/edu");
        // 银行卡
        ossmap.put("bank", "Emp_Source/bank");
        // 合同
        ossmap.put("temp", "Emp_Source/contract");
        // 展业证/执业证
        ossmap.put("cert", "Emp_Source/cert");
        // 增员二维码
        ossmap.put("qrcode", "Emp_Source/qrcode");
        // 签名
        ossmap.put("autograph", "Emp_Source/autograph");
        // 保单保全理赔附件
        ossmap.put("enclosure", "Emp_Source/enclosure");

        // ---------------------保险公司---------------------
        // 保险公司协议
        ossmap.put("xy", "Sup_Source/agreement");
        // 保险公司logo
        ossmap.put("bxlogo", "Sup_Source/logo");

        // ----------------------产品库----------------------
        // 产品图标
        ossmap.put("icon", "CP_Source/icon");
        // 产品素材html地址
        ossmap.put("html", "CP_Source/html");

        // ----------------------杂项----------------------
        ossmap.put("zx", "Mixed_Source");

        // --------------------临时文件--------------------
        ossmap.put("TMP", "TMP");

        // 方法与枚举值对应
        conditionmap = new HashMap<>();
        // 入司-入司优惠券
        conditionmap.put("RS", "FF1");
    }

    public void setmsossurl(String msossurl) {
        BaseConstants.MSOSS_URL = "https://saasms-test.oss-cn-shenzhen.aliyuncs.com/";
    }

    public void setsaasmsurl(String saasmsurl) {
        BaseConstants.SAASMS_URL = "http://saasms.abtpt.com";
    }

    public void setsaascvurl(String saascvurl) {
        BaseConstants.SAASCV_URL = "http://@.nhcv.abtpt.com";
    }
}
