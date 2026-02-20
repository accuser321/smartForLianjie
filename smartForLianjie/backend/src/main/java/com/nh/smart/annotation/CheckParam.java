package com.nh.smart.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.nh.smart.constant.Check;

/**
 * 
 * @ClassName: CheckParam
 * @Description: 接口参数校验 注解
 * @Author Demo
 * @DateTime 2020年2月1日 下午3:59:39
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckParam {

	/**
	 * 参数校验调用方法
	 * @return
	 */
	Check value() default Check.NotNull;

	/**
	 * 多个值逗号隔开
	 * @return
	 */
	String express() default "";
	
	/**
	 * 参数名称用.表示层级，最多支持2级如： entity.userName
	 * @return
	 */
	String argName();
	
	/**
     * 参数类型取值举例：
     * java.lang.String
     */
    String argType() default "";
    

	/**
	 * 自定义错误提示信息
	 * @return
	 */
	String msg() default "";

}
