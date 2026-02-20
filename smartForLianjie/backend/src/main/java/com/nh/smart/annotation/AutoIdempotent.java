package com.nh.smart.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @ClassName: AutoIdempotent
 * @Description: 幂等性校验,在需要保证 接口幂等性 的Controller的方法上使用此注解
 * @Author Demo
 * @DateTime 2020年1月19日 下午1:51:10
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoIdempotent {
  
}
