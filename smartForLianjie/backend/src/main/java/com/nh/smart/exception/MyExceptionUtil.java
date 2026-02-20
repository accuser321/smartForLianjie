package com.nh.smart.exception;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

/**
 * 异常工具类
 *
 * @author Demo
 * @date 2019-09-10 11:31
 */
public class MyExceptionUtil {

    public MyExceptionUtil() {
    }

    public static ServiceException mxe(String msg, Throwable t, Object... params) {
        return new ServiceException(StringUtils.format(msg, params), t);
    }

    public static ServiceException mxe(String msg, Object... params) {
        return new ServiceException(StringUtils.format(msg, params));
    }

    public static ServiceException mxe(Throwable t) {
        return new ServiceException(t);
    }

}
