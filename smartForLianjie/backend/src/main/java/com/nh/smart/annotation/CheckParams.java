package com.nh.smart.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.swagger.annotations.ApiImplicitParam;

/**
 * 
 * @ClassName: CheckParams
 * @Description: 接口参数校验 注解
 * @Author Demo
 * @DateTime 2020年2月1日 下午4:05:45
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckParams {
    /**
     * A list of {@link ApiImplicitParam}s available to the API operation.
     * 多个CheckParam，由上往下判断
     */
	CheckParam[] value();
}
