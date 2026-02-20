package com.nh.smart.service.base.impl;
import com.nh.smart.constant.BaseConstants;
import com.nh.smart.dao.material.SmartComEmpnoDao;
import com.nh.smart.dao.record.SmartComMuserDao;
import com.nh.smart.entity.base.SecurityUserDetails;
import com.nh.smart.entity.material.SmartComEmpno;
import com.nh.smart.entity.record.SmartComMuser;
import com.nh.smart.exception.MyExceptionUtil;
import com.nh.smart.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * UserDetailService的实现类
 * 这个@Primary表示这个类所继承的接口有多个实现类，当不知道引入哪个的时候，优先使用@Primary所注解的类
 */
@Primary
@Service("SecurityUserDetailsServiceImpl")
public class SecurityUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SmartComMuserDao smartComMuserDao;

    // 外勤用户
    @Autowired
    private SmartComEmpnoDao smartComEmpnoDao;

    @Override
    public UserDetails loadUserByUsername(String openId) throws UsernameNotFoundException {
        // 获取访问路径
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String servletPath = request.getServletPath();
        SecurityUserDetails securityUserDetails;
        SmartComMuser authUser;
        Map<String, Object> map = new HashMap<>();
        // 将登陆方法传过来的工号+渠道编码拆开
        String[] split = openId.split("&");
        String userid = split[0];
        String comid = split[1];
        String type = split[2];

        authUser = smartComMuserDao.selectByUserId(userid, comid);

        String tokenEmpno = authUser.getEmpno();
        if ("/auth/getToken".equals(servletPath)) {
            securityUserDetails = new SecurityUserDetails(userid + "&" + comid + "&S", authUser.getUserid());
            map.put(BaseConstants.USER_INFO, authUser);
            redisTemplate.opsForHash().putAll(userid + authUser.getUserid() + tokenEmpno, map);
            return securityUserDetails;
        }
        // 登陆接口直接查询数据库，非登陆接口再访问缓存
        if (!("/auth/getToken").equals(servletPath)) {
            map = redisTemplate.opsForHash().entries(userid + authUser.getUserid() + tokenEmpno);
            // 如果不为空则则将用户信息返回
            if (map.get(BaseConstants.USER_INFO) != null && map.get(BaseConstants.USER_PERMISSION) != null && map.get(BaseConstants.USER_YWY) != null) {
                // 用户信息
                Object object = map.get(BaseConstants.USER_INFO);
                authUser = (SmartComMuser) object;
                String empno = "";
                String password = "";
                String state = "";
                String tjcode = "";
                boolean flag = false;
                if (authUser.getRytype().equals("N")) {
                    SmartComEmpno user = smartComEmpnoDao.selectByEmpno(authUser.getEmpno(), comid);
                    if (user != null) {
                        empno = user.getEmpno();
                        password = user.getPasswd();
                        state = user.getStatus();
                        tjcode = user.getTjcode();
                        flag = true;
                    } else {
                      throw MyExceptionUtil.mxe(userid + "工号或密码错误");
                    }
                }
                // 封装SecurityUserDetails对象
                if (flag) {
                    securityUserDetails = new SecurityUserDetails(empno, userid + "&" + comid + "&S", password, state, tjcode, authUser.getRytype(), authUser.getUserid(),null);
                    return securityUserDetails;
                }
            }
        }

        if (!ObjectUtils.isEmpty(authUser)) {
            // 根据用户工号查询用户的权限菜单路径
            if (authUser.getRytype().equals("N")) {
              SmartComEmpno user = smartComEmpnoDao.selectByEmpno(authUser.getEmpno(), comid);
              if (user == null) {
                throw MyExceptionUtil.mxe(userid + "业务员信息不存在！");
              }
              map.put(BaseConstants.USER_YWY, user);
              // 封装SecurityUserDetails对象
                // 封装SecurityUserDetails对象
                securityUserDetails = new SecurityUserDetails(user.getEmpno(), userid + "&" + comid + "&S", user.getPasswd(), user.getStatus(),null, authUser.getRytype(), authUser.getUserid(),null);
            } else {
                securityUserDetails = new SecurityUserDetails(userid + "&" + comid + "&S", authUser.getUserid());
            }
            // 将用户信息放入缓存并返回
            map.put(BaseConstants.USER_INFO, authUser);
            redisTemplate.opsForHash().putAll(userid + authUser.getUserid() + tokenEmpno, map);
            return securityUserDetails;
        }
        throw MyExceptionUtil.mxe(userid + "用户不存在");
    }


}
