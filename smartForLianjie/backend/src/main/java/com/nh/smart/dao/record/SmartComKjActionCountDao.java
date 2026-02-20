package com.nh.smart.dao.record;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nh.smart.entity.record.SmartComKjActionCount;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * smart_com_kj_action_count#渠道业务员行为次数表 Dao接口
 */
@Repository
public interface SmartComKjActionCountDao extends BaseMapper<SmartComKjActionCount> {

    /**
     * 添加更新业务员行为次数
     *
     * @Param: [smartComKjActionCount]
     * @return: java.lang.Integer
     * @Author: 张宁
     * @Date: 2019/11/5
     */
    Integer insertActionCount(@Param("smartComKjActionCount") SmartComKjActionCount smartComKjActionCount);

  /**
   * 计算名片或赞数
   *
   * @Param: [empno]
   * @return: int
   * @Author: 张宁
   * @Date: 2019/11/20
   */
  int selectMpDzByEmpno(@Param("comid") String comid, @Param("empno") String empno, @Param("userid") Integer userid);

}
