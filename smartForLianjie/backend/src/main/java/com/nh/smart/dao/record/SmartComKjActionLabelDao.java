package com.nh.smart.dao.record;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nh.smart.entity.record.SmartComKjActionLabel;
import com.nh.smart.entity.record.SmartComKjActionRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * smart_com_kj_action_label#渠道客户内容标签行为关系 Dao接口
 */
@Repository
public interface SmartComKjActionLabelDao extends BaseMapper<SmartComKjActionLabel> {

    /**
     * 添加标签行为
     *
     * @Param: [SmartComKjActionRecord, labidlist]
     * @return: java.lang.Integer
     * @Author: 张宁
     * @Date: 2019/11/5
     */
    Integer insertActionLabel(@Param("smartComKjActionRecord") SmartComKjActionRecord smartComKjActionRecord, @Param("labidlist") List<String> labidlist);

}
