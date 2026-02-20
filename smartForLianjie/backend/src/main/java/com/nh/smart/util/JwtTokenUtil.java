package com.nh.smart.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.nh.smart.entity.base.SecurityUserDetails;
import com.nh.smart.exception.MyExceptionUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 *
 * @ClassName: JwtTokenUtil
 * @Description: JWT的工具类
 * @Author Demo
 * @DateTime 2020年5月6日 上午11:41:43
 */
@Configuration
public class JwtTokenUtil {

	private static String secret;

	private static Long expiration;

	private static String header;

	private static String token_start;

	private static Long refresh;

	/**
	 *
	 * @Title: generateToken
	 * @Description: 从数据声明生成令牌
	 * @Author Demo
	 * @DateTime 2020年5月6日 上午11:50:24
	 * @param claims
	 * @return
	 */
	private static String generateToken(Map<String, Object> claims) {
		Date expirationDate = new Date(System.currentTimeMillis() + expiration);
		return Jwts.builder().setClaims(claims).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}

	/**
	 *
	 * @Title: getClaimsFromToken
	 * @Description: 从令牌中获取数据声明
	 * @Author Demo
	 * @DateTime 2020年5月6日 上午11:50:08
	 * @param token
	 * @return
	 */
	private static Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	/**
	 *
	 * @Title: generateToken
	 * @Description: 生成令牌
	 * @Author Demo
	 * @DateTime 2020年5月6日 上午11:49:46
	 * @param userName 渠道编码(comId)
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	public static String generateToken(String userName, String ip) throws Exception {
		Map<String, Object> claims = new HashMap<>(3);
		claims.put("sub", userName);
		claims.put("created", new Date());
		// ip加密保存
		ip = MD5Util.MD5EncodeForHex(ip);
		claims.put("ip", ip);
		return generateToken(claims);
	}

	/**
	 *
	 * @Title: getUsernameFromToken
	 * @Description: 从令牌中获取渠道编码(comId)
	 * @Author Demo
	 * @DateTime 2020年5月6日 上午11:51:31
	 * @param token
	 * @return
	 */
	public static String getUsernameFromToken(String token) {
        String userid;
        try {
            Claims claims = getClaimsFromToken(token);
            userid = claims.getSubject();
        } catch (Exception e) {
            userid = null;
        }
        return userid;
    }

	/**
	 *
	 * @Title: getIpFromToken
	 * @Description: 从令牌中获取ip
	 * @Author Demo
	 * @DateTime 2020年5月6日 下午1:52:47
	 * @param token
	 * @return
	 */
	public static String getIpFromToken(String token) {
        String ip;
        try {
            Claims claims = getClaimsFromToken(token);
            ip = claims.get("ip").toString();
        } catch (Exception e) {
            ip = null;
        }
        return ip;
    }

	/**
	 *
	 * @Title: isTokenExpired
	 * @Description: 判断令牌是否过期
	 * @Author Demo
	 * @DateTime 2020年5月6日 下午1:53:46
	 * @param token
	 * @return
	 */
	public static Boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            if (claims == null) {
                return false;
            }
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            throw MyExceptionUtil.mxe(e);
        }
    }

	/**
	 *
	 * @Title: validateToken
	 * @Description: 验证令牌
	 * @Author Demo
	 * @DateTime 2020年5月6日 下午2:04:29
	 * @param token 令牌
	 * @param userDetails 自定义安全用户
	 * @param ip
	 * @return
	 */
	public static Boolean validateToken(String token, UserDetails userDetails, String ip) {
		 SecurityUserDetails user = (SecurityUserDetails) userDetails;
		 String username = getUsernameFromToken(token);
		 String tokenip = getIpFromToken(token);
		 try {
	            // 将ip加密与token中的ip进行比较
	            ip = MD5Util.MD5EncodeForHex(ip);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		// && ip.equals(tokenip)  由于开发时，token只能在线上环境获取，线上的token拿到本地不能访问，所以先将ip校验注释调
	    return (username.equals(user.getUsername()) && !isTokenExpired(token) && ip.equals(tokenip));
//		return true;
	}

	/**
	 *
	 * @Title: getOpenid
	 * @Description: 从SecurityContextHolder中获取openid
	 * @Author Demo
	 * @DateTime 2020年5月6日 下午2:39:47
	 * @return
	 * @throws Exception
	 */
	public static String getOpenid() throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();

		//将对象转为map
		Map map = JsonUtil.obj2map(principal);
		 // 从map中获取openid
        String info = (String) map.get("username");
		return info.split("&")[0];
	}

	/**
	 *
	 * @Title: getComid
	 * @Description: 从SecurityContextHolder中获取comid(渠道编码)
	 * @Author Demo
	 * @DateTime 2020年5月6日 下午2:50:39
	 * @return
	 * @throws Exception
	 */
	public static String getComid() throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		//将对象转为map
        Map map = JsonUtil.obj2map(principal);
        // 从map中获取渠道编码
        String info = (String) map.get("username");
        return info.split("&")[1];
	}

	/**
	 *
	 * @Title: getType
	 * @Description: 从SecurityContextHolder中获取用户标识，P：APP用户，S：微信用户
	 * @Author Demo
	 * @DateTime 2020年5月6日 下午2:54:24
	 * @return
	 * @throws Exception
	 */
	public static String getType() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        //将对象转为map
        Map map = JsonUtil.obj2map(principal);
        // 从map中获取渠道编码
        String info = (String) map.get("username");
        return info.split("&")[2];
    }

	/**
	 *
	 * @Title: getUserid
	 * @Description: 从SecurityContextHolder中获取userid(用户编码)
	 * @Author Demo
	 * @DateTime 2020年5月6日 下午2:56:07
	 * @return
	 * @throws Exception
	 */
	public static Integer getUserid() throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        //将对象转为map
        Map map = JsonUtil.obj2map(principal);
        // 从map中获取渠道编码
        Integer info = Integer.parseInt(map.get("userid").toString());
        return info;
	}

	/**
	 *
	 * @Title: getEmpno
	 * @Description: 从SecurityContextHolder中获取empno(用户工号)
	 * @Author Demo
	 * @DateTime 2020年5月6日 下午2:59:04
	 * @return
	 * @throws Exception
	 */
	public static String getEmpno() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        //将对象转为map
        Map map = JsonUtil.obj2map(principal);
        // 从map中获取渠道编码
        String info = (String) map.get("empno");
        return info;
    }

	/**
	 *
	 * @Title: getTjCode
	 * @Description: 从SecurityContextHolder中获取tjcode(机构代码)
	 * @Author Demo
	 * @DateTime 2020年5月6日 下午3:00:51
	 * @return
	 * @throws Exception
	 */
	public static String getTjCode() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        //将对象转为map
        Map map = JsonUtil.obj2map(principal);
        // 从map中获取渠道编码
        String info = (String) map.get("tjcode");
        return info;
    }

	/**
	 *
	 * @Title: getIsSaleCom
	 * @Description: 从SecurityContextHolder中获取是否是二级渠道登陆
	 * @Author Demo
	 * @DateTime 2020年5月6日 下午3:01:28
	 * @return
	 * @throws Exception
	 */
	public static boolean getIsSaleCom() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        //将对象转为map
        Map map = JsonUtil.obj2map(principal);
        // 从map中获取管理员标识
        boolean info = Boolean.parseBoolean(map.get("isSaleCom").toString());
        return info;
    }

	@Value("${jwt.secret}")
    public void setSecret(String secret) {
        JwtTokenUtil.secret = secret;
    }

    @Value("${jwt.expiration}")
    public void setExpiration(Long expiration) {
        JwtTokenUtil.expiration = expiration;
    }

    @Value("${jwt.header}")
    public void setHeader(String header) {
        JwtTokenUtil.header = header;
    }

    @Value("${jwt.token_start}")
    public void setToken_start(String token_start) {
        JwtTokenUtil.token_start = token_start;
    }

    @Value("${jwt.refresh}")
    public void setRefresh(Long refresh) {
        JwtTokenUtil.refresh = refresh;
    }

    public static Long getRefresh() {
        return refresh;
    }

    public static String getSecret() {
        return secret;
    }

    public static Long getExpiration() {
        return expiration;
    }

    public static String getHeader() {
        return header;
    }

    public static String getToken_start() {
        return token_start;
    }

}
