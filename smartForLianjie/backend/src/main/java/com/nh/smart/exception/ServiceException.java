package com.nh.smart.exception;

/**
 * 
 * @ClassName: ServiceException
 * @Description: 自定义业务异常类
 * @Author Demo
 * @DateTime 2020年1月9日 下午5:17:12
 */
public class ServiceException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	private Integer code;
	
    private String msg;

    public ServiceException() {
    }

    public ServiceException(String msg) {
        this.msg = msg;
    }

    public ServiceException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    
    public ServiceException(String msg, Throwable throwable) {
    	super(msg, throwable);
    }
    
    public ServiceException(Throwable throwable) {
        super(throwable);
    }

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
