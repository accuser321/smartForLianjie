package com.nh.smart.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Configuration;

import com.nh.smart.exception.MyExceptionUtil;

/**
 *
 * @Title: IpUtil
 * @Description: 获取客户端真实ip地址
 * @Author Demo
 * @DateTime 2020年1月9日 下午5:58:04
 * @param request
 * @return
 */
@Configuration
public class IpUtil {

	private static String nhwxcomid="NHWX";

	private static String nhsxcomid="NHSX";

	private static String hcdlcomid="HCDL";

	/**
	 *
	 * @Title: getComid
	 * @Description: 获取主体comid
	 * @Author Demo
	 * @DateTime 2020年5月7日 下午5:00:07
	 * @param serverName
	 * @return
	 */
	public static String getComid(String serverName) {
		if ("nhwx".equals(serverName)) {
			return nhwxcomid;
		}
		if ("nhsx".equals(serverName)) {
			return nhsxcomid;
		}
		if ("hcdl".equals(serverName)) {
			return hcdlcomid;
		}
		if ("qd".equals(serverName)) {
			return nhwxcomid;
		}
		throw MyExceptionUtil.mxe("");
	}

	public static String getNhwxcomid() {
		return nhwxcomid;
	}

	public static String getNhsxcomid() {
		return nhsxcomid;
	}

	public static String getHcdlcomid() {
		return hcdlcomid;
	}

	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		return ip;
	}

	/**
	 *
	 * @Title: getIpAddr
	 * @Description: 获取当前请求用户的ip地址
	 * @Author Demo
	 * @DateTime 2020年5月7日 下午4:54:14
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ipAddress;
		try {
			ipAddress = request.getHeader("x-forwarded-for");
			if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("Proxy-Client-IP");
			}
			if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getRemoteAddr();
				if ("127.0.0.1".equals(ipAddress)) {
					// 根据网卡取本机配置的IP
					InetAddress inet = null;
					try {
						inet = InetAddress.getLocalHost();
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}
					ipAddress = inet.getHostAddress();
				}
			}
			// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
			if (ipAddress != null && ipAddress.length() > 15) {
				if (ipAddress.indexOf(",") > 0) {
					ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
				}
			}
		} catch (Exception e) {
			ipAddress = "";
		}
		return ipAddress;
	}

	/**
	 *
	 * @Title: getServerName
	 * @Description: 获取当前请求用户的域名
	 * @Author Demo
	 * @DateTime 2020年5月7日 下午4:56:39
	 * @param request
	 * @return
	 */
	public static String getServerName(HttpServletRequest request) {
		return request.getHeader("Origin");
	}

	/**
	 * 获取当前请求用户的域名头
	 *
	 * @param request
	 * @return
	 * @author Demo
	 * @date 2019/11/30
	 */
	public static String getServerNameHeader(HttpServletRequest request) {

		/*
		 * String serverName = getServerName(request); serverName =
		 * serverName.replace("http://", "").replace("https://", ""); String[] split2 =
		 * serverName.split("\\."); serverName = split2[0]; return serverName;
		 */

		return "nhsx";
	}

}
