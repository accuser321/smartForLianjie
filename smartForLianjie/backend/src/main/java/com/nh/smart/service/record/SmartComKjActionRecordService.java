package com.nh.smart.service.record;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nh.smart.annotation.MultiRequestBody;
import com.nh.smart.entity.material.SmartComKjLibwEmpno;
import com.nh.smart.entity.record.SmartComKjActionRecord;
import com.nh.smart.entity.vo.UserAction;

import java.util.List;
import java.util.Map;

/**
 * smart_com_kj_action_record#渠道业务员行为记录表 业务逻辑的接口类
 */
public interface SmartComKjActionRecordService {

    /**
     * 添加行为记录
     *
     * @Param: [smartComKjActionRecord]
     * @return: java.lang.Integer
     * @Author: 张宁
     * @Date: 2019/11/5
     */
    Integer insertActionCord(SmartComKjActionRecord smartComKjActionRecord, String serverName) throws Exception;

    /**
     * 更新行为记录浏览时间
     *
     * @Param: [userAction]
     * @return: java.lang.Integer
     * @Author: 张宁
     * @Date: 2019/11/6
     */
    Integer updateActionCordTime(UserAction userAction);

    /**
     * 发送消息提醒
     *
     * @Param: [smartComKjActionRecord]
     * @return: com.saascs.common.entity.Result
     * @Author: 张宁
     * @Date: 2019/11/8
     */
    void remindMessage(Map<String, Object> map) throws Exception;

    Map<String, Object> getKHRelation(String userid,String labid) throws Exception;
    /**
     * 文章-谁看了我-谁转发了我
     *
     * @Param: [map]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<java.util.Map < java.lang.String, java.lang.Object>>
     * @Author: 张宁
     * @Date: 2019/11/18
     */
    IPage<Map<String, Object>> getWZRdZf(Map<String, Object> map) throws Exception;


    void updateMSG(String userid, String empno, String type) throws Exception;

    /**
     * 根据文章编号查询文章详情
     *
     * @Param: [comid, sno]
     * @return: java.util.List<com.nh.smart.entity.material.SmartComKjLibw>
     * @Author: 张宁
     * @Date: 2019/12/25
     */
    List<SmartComKjLibwEmpno> getWZXQ(List<String> wzlist) throws Exception;
    /**
     * 添加用户咨询消息
     *
     * @Param: [ywuserid, khuserid, content]
     * @return: java.lang.Integer
     * @Author: 张宁
     * @Date: 2019/11/30
     */
    Integer insertKjChat(String empno, String khuserid, String content, String type, String date, String read, String rytype, String serverName) throws Exception;

    /**
     * 点赞处理
     *
     * @Param: [userid, flag]
     * @return: java.lang.Integer
     * @Author: 张宁
     * @Date: 2020/1/19
     */
    Integer updateDZ(String userid, String flag, String empno) throws Exception;
}
