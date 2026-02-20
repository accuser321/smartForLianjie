package com.nh.smart.dao.record;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nh.smart.entity.record.SmartComKjActionEmpcount;
import com.nh.smart.entity.record.SmartComKjActionRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * smart_com_kj_action_empcount#渠道业务员每日行为统计 Dao接口
 */
@Repository
public interface SmartComKjActionEmpcountDao extends BaseMapper<SmartComKjActionEmpcount> {

    /**
     * 添加业务员每日行为统计
     *
     * @Param: [smartComKjActionRecord, xzkhnum, wzzfnum, wzllnum, mpzfnum, mpllnum, mpdznum, zxnum]
     * @return: java.lang.Integer
     * @Author: 张宁
     * @Date: 2019/11/5
     */
    Integer insertActionEmpno(@Param("smartComKjActionRecord") SmartComKjActionRecord SmartComKjActionRecord, @Param("xzkhnum") Integer xzkhnum, @Param("wzzfnum") Integer wzzfnum, @Param("wzzfsnum") Integer wzzfsnum, @Param("wzllnum") Integer wzllnum, @Param("mpzfnum") Integer mpzfnum, @Param("mpzfsnum") Integer mpzfsnum, @Param("mpllnum") Integer mpllnum, @Param("mpdznum") Integer mpdznum, @Param("zxnum") Integer zxnum);

}
