package com.nh.smart.configuration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nh.smart.constant.ResponseResult;
import com.nh.smart.exception.ServiceException;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName: GlobalExceptionHandler
 * @Description: 处理自定义的业务异常
 * @Author Demo
 * @DateTime 2020年1月19日 上午11:40:08
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ServiceException.class)  
    @ResponseBody  
    public  ResponseResult bizExceptionHandler(HttpServletRequest req, ServiceException e){
        
        return ResponseResult.build(e.getCode(),e.getMsg());
    }
    
    
    
}
