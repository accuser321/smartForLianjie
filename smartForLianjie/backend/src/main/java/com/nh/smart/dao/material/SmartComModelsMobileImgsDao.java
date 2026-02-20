package com.nh.smart.dao.material;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nh.smart.entity.material.SmartComModelsMobileImgs;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* smart_com_models_mobile_imgs#渠道图片设置表 Dao接口
*/
@Repository
public interface SmartComModelsMobileImgsDao extends BaseMapper<SmartComModelsMobileImgs> {


    /**
     * 手机端图片列表
     *
     * @return: List<SmartComModelsMobileImgs>
     * @Author: 刘阳光
     * @Date: 2020/01/19
     */
    List<SmartComModelsMobileImgs> pictureList(String comid);
}
