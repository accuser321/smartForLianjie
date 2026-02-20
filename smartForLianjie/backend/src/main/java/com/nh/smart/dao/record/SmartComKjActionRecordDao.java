package com.nh.smart.dao.record;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nh.smart.entity.record.SmartComKjActionRecord;
import com.nh.smart.entity.vo.UserAction;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * smart_com_kj_action_record#渠道业务员行为记录表 Dao接口
 */
@Repository
public interface SmartComKjActionRecordDao extends BaseMapper<SmartComKjActionRecord> {

    /**
     * 根据用户openid或者empno查询用户userid
     *
     * @Param: [openid]
     * @return: java.lang.String
     * @Author: 张宁
     * @Date: 2019/11/5
     */
    Map<String, Object> selectUseridByOpenid(@Param("openid") String openid, @Param("empno") String empno, @Param("comid") String comid, @Param("rytype") String rytype);

    /**
     * 查询客户是否为准客户
     *
     * @Param: [comid, empno, userid, labid]
     * @return: java.lang.Integer
     * @Author: 张宁
     * @Date: 2019/11/5
     */
    Integer selectCustomLabel(@Param("comid") String comid, @Param("empno") String empno, @Param("userid") Integer userid, @Param("labid") String labid);

    /**
     * 更新行为记录浏览时间
     *
     * @Param: [userAction]
     * @return: java.lang.Integer
     * @Author: 张宁
     * @Date: 2019/11/6
     */
    Integer updateActionCordTime(@Param("userAction") UserAction userAction);

    /**
     * 获取业务员行为记录
     *
     * @Param: [comid, empno]
     * @return: java.util.List<com.saascs.Smart.entity.record.SmartComKjActionRecord>
     * @Author: 张宁
     * @Date: 2019/11/9
     */
    List<SmartComKjActionRecord> getKHRelation(@Param("comid") String comid, @Param("empno") String empno, @Param("userid") Integer userid, String sno);

    /**
     * 文章-谁看了我-谁转发了我
     *
     * @Param: [pageModel, comid, empno, sno, type]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<java.util.Map < java.lang.String, java.lang.Object>>
     * @Author: 张宁
     * @Date: 2019/11/18
     */
    IPage<Map<String, Object>> getWZRdZf(Page pageModel, @Param("comid") String comid, @Param("empno") String empno, @Param("recordtype") String recordtype, @Param("sno") String sno, @Param("type") String type, @Param("userid") Integer userid);

    /**
     * 查询文章是否已经推送过
     *
     * @Param: [SmartComKjActionRecord]
     * @return: int
     * @Author: 张宁
     * @Date: 2019/12/5
     */
    int selectWZread(SmartComKjActionRecord smartComKjActionRecord);

    /**
     * 查询此用户文章是否转发过
     *
     * @Param: [SmartComKjActionRecord]
     * @return: int
     * @Author: 张宁
     * @Date: 2020/2/24
     */
    String selectZF(@Param("smartComKjActionRecord") SmartComKjActionRecord smartComKjActionRecord);

    /**
     * 修改素材二转数
     *
     * @Param: [khnum, wzzfsnum]
     * @return: void
     * @Author: 张宁
     * @Date: 2020/2/24
     */
    void updateSCNUM(@Param("khnum") Integer khnum, @Param("wzzfsnum") Integer wzzfsnum, @Param("kullnum") Integer kullnum, @Param("smartComKjActionRecord") SmartComKjActionRecord SmartComKjActionRecord, @Param("llnum") Integer llnum);
    /**
     *@Author ww
     * @Description //TODO
     * @Date
     * @Param
     * @return
    **/
    Map<String, Object> selectUseridByUserid(@Param("comid")String comid,  @Param("ywuserid")Integer ywuserid);


    /**
     * 计算名片或赞数
     *
     * @Param: [empno]
     * @return: int
     * @Author: 张宁
     * @Date: 2019/11/20
     */
    int selectDZFlag(@Param("comid") String comid, @Param("empno") String empno, @Param("userid") Integer userid);

    /**
     * 统计多少个人浏览过
     *
     * @Param: [pageModel, comid, empno]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<java.util.Map < java.lang.String, java.lang.Object>>
     * @Author: 张宁
     * @Date: 2019/11/20
     */
    IPage<Map<String, Object>> selectCardKH(Page pageModel, @Param("comid") String comid, @Param("empno") String empno);

    /**
     * 点赞处理
     *
     * @Param: [comid, empno, userid, flag]
     * @return: java.lang.Integer
     * @Author: 张宁
     * @Date: 2020/1/19
     */
    Integer updateDZ(@Param("comid") String comid, @Param("empno") String empno, @Param("userid") Integer userid, @Param("flag") String flag);

    /**
     * 客户互动记录列表
     */
    IPage<Map<String, Object>> selectInteractiveList(Page pageModel, @Param("comid") String comid, @Param("empno") String empno, @Param("userid") Integer userid);
}
