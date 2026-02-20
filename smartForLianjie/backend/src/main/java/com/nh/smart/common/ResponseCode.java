package com.nh.smart.common;

/**
 * 
 * @ClassName: ResponseCode
 * @Description: 响应状态码
 * @Author Demo
 * @DateTime 2020年1月9日 下午5:22:20
 */
public enum ResponseCode {

    // 系统模块
    SYSTEM_ERROR(500, "服务器系统异常"),
    SUCCESS(200, "操作成功"),
    
    
    ERROR(201, "操作失败"),		//201为统称失败,此201状态码,在CheckParamAop.java中，耦合了两个地方(L38,L53)
    INSERT_ERROR(202,"插入数据有误"),
    

    // 通用模块 1xxxx
    ILLEGAL_ARGUMENT(10000, "参数不合法"),
    REPETITIVE_OPERATION(10001, "请勿重复操作"),
    ACCESS_LIMIT(10002, "请求太频繁, 请稍后再试"),
    MAIL_SEND_SUCCESS(10003, "邮件发送成功"),
    NO_OPERATION_AUTHORITY(10004,"无操作权限"),

    // 业务模块，枚举依次类推...
    BUSINESS(300, "业务操作成功"),
    BUSINESS_ERROR(301, "业务操作失败"),		//301为统称业务失败
    NAME_NOTEMPTY(302, "名字不能为空"),
    AGE_NOTEMPTY(303, "年龄不能为空"),
    SEX_NOTEMPTY(304, "性别不能为空"),
    USER_NOT_FOUND(304, "用户不存在"),
    USER_LOCKED(305, "无效账号"),			//(用户被锁)
    USER_NOT_AUTHEN(306,"您的账号或密码输入错误"),
    USERORPASSWOAD_NOTEMPTY(307,"您的账号或密码输入不能为空"),
    PASSWORD_NOT_SIX(308,"密码不能小于6位")
    ;
	
	ResponseCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;

    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
