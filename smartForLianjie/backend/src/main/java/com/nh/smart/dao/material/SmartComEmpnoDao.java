package com.nh.smart.dao.material;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nh.smart.entity.material.SmartComEmpno;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * smart_com_empno#渠道业务员表 Dao接口
 */
@Repository
public interface SmartComEmpnoDao extends BaseMapper<SmartComEmpno> {

    /**
     * 验证密码正确性
     *
     * @return
     */
    SmartComEmpno verify(@Param("empno") String empno, @Param("passwd") String passwd, @Param("comid") String comid);

    /**
     * 根据工号渠道查询
     *
     * @param empno
     * @param comid
     * @return
     */
    SmartComEmpno selectByEmpno(@Param("empno") String empno, @Param("comid") String comid);

    /**
     * 获取外勤用户信息
     *
     * @param empno
     * @param comid
     * @return
     */
    Map<String, Object> getEmpno(@Param("empno") String empno, @Param("comid") String comid);


    /**
     * 更新外勤业务员绑定微信信息
     * @return
     */
   int  updateWeixin(Map<String, String> map);

    /**
     * 根据手机号查询业务员信息
     *
     * @return:
     * @author: yk
     * @time: 2019/12/23 16:30
     */
    SmartComEmpno verifyPhone(@Param("phone") String phone, @Param("comid") String comid);

    /**
     * 查询业务员表的用户信息
     *
     * @param empno 工号
     * @param comid 渠道编码
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/11/19
     */
    Map<String, String> selectInfoByEmpno(@Param("empno") String empno, @Param("comid") String comid);
}
