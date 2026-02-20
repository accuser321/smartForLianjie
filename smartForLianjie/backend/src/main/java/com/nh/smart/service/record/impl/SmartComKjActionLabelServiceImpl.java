package com.nh.smart.service.record.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nh.smart.dao.record.SmartComKjActionLabelDao;
import com.nh.smart.entity.record.SmartComKjActionLabel;
import com.nh.smart.service.record.SmartComKjActionLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * smart_com_kj_action_label#渠道客户内容标签行为关系 业务逻辑接口的实现类
 */
@Service
public class SmartComKjActionLabelServiceImpl extends ServiceImpl<SmartComKjActionLabelDao, SmartComKjActionLabel> implements SmartComKjActionLabelService {

    @Autowired
    private SmartComKjActionLabelDao smartComKjActionLabelDao;
}
