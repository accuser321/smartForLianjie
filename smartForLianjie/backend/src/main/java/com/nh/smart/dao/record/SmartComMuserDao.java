package com.nh.smart.dao.record;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nh.smart.entity.record.SmartComMuser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Dao接口
 */
@Repository
public interface SmartComMuserDao extends BaseMapper<SmartComMuser> {

    /**
     * 获取最大用户id
     *
     * @return
     */
    String getMaxUserId();


    /**
     * 根据openid获取用户信息
     *
     * @param openid
     * @return
     */
    SmartComMuser selectByUserId(@Param("openid") String openid, @Param("comid") String comid);


    /**
     * 根据openid获取用户信息
     *
     * @param
     * @return
     */
    SmartComMuser selectByUser(@Param("userid") String userid, @Param("comid") String comid);


    /**
     * 解绑微信
     *
     * @param userid
     * @param comid
     */
    void delOpenid(@Param("userid") String userid, @Param("comid") String comid);

    /**
     * 删除业务员微信相关信息
     * @param empno
     * @param comid
     * @return
     */
    int delWeiXin(@Param("empno") String empno,@Param("comid") String comid);

    /**
     * 获取是否存在绑定过用户
     *
     * @param empno
     * @return
     */
    SmartComMuser getByEmpno(@Param("empno") String empno, @Param("comid") String comid);

    /**
     * 获取用户名片信息
     *
     * @param pojo
     * @return
     */
    Map<String, String> getCard(SmartComMuser pojo);

    /**
     * 注册判断此手机号是否已用
     *
     * @return:
     * @author: yk
     * @time: 2020/1/13 15:36
     */
    String isRegister(@Param("phone") String phone, @Param("comid") String comid);

    /**
     * 注册判断此手机号是否已为业务员
     *
     * @return:
     * @author: yk
     * @time: 2020/1/13 15:36
     */
    String empnoIsRegister(@Param("phone") String phone, @Param("comid") String comid);
    /**
     * 获取微信配置
     *
     * @param comid
     * @return
     */
    Map<String, String> getWxInfo(String comid);
}
