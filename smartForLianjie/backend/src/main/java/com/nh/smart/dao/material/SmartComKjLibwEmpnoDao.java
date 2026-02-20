package com.nh.smart.dao.material;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nh.smart.entity.material.SmartComKjLibwEmpno;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * smart_com_kj_libw_empno#渠道业务员素材库 Dao接口
 */
@Repository
public interface SmartComKjLibwEmpnoDao extends BaseMapper<SmartComKjLibwEmpno> {

    /**
     * 批量保存标签素材关系
     *
     * @param list 对象集合
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/11/7
     */
    void batchSave(@Param("item") List<HashMap<String, Object>> list);


    /**
     * 根据素材编号查询业务员素材表的文章信息和素材标签信息
     *
     * @param sno    素材编号
     * @param comid  渠道编码
     * @param oempno 原始业务员工号
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/11/8
     */
    SmartComKjLibwEmpno selectWZInfoFromE(@Param("comid") String comid, @Param("sno") String sno, @Param("oempno") String oempno);


    /**
     * 业务员素材表素材列表查询
     *
     * @param pageEntity 分页实体
     * @param stagcode   类型
     * @param btagcode   分类
     * @param stitle     搜索条件标题
     * @param comid      渠道id
     * @param empno      业务员工号
     * @return
     * @author 王名渤
     * @date 2019/11/5
     */
    IPage<SmartComKjLibwEmpno> selectPageByBtagcode(@Param("pageEntity") Page pageEntity, @Param("stagcode") String stagcode, @Param("btagcode") String btagcode,
                                                  @Param("stitle") String stitle, @Param("comid") String comid, @Param("empno") String empno);


    /**
     * 业务员素材表素材详情
     *
     * @param sno      素材编号
     * @param btagcode 素材类型编码
     * @param comid    渠道编码
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/11/8
     */
    SmartComKjLibwEmpno selectOneBySno(@Param("comid") String comid, @Param("sno") String sno, @Param("btagcode") String btagcode);


    /**
     * 我的素材列表删除
     *
     * @param sno 素材编号
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/11/8
     */
    void deleteBySno(@Param("sno") String sno, @Param("comid") String comid, @Param("empno") String empno);


    /**
     * 查询素材对应的标签集合
     *
     * @param comid 渠道编码
     * @param sno   素材编号
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/11/11
     */
    List<String> selectTagcodeBySno(@Param("comid") String comid, @Param("sno") String sno);


    /**
     * 删除渠道素材标签表
     *
     * @param comid 渠道编码
     * @param sno   素材编号
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/11/13
     */
    void deleteBQBySno(@Param("comid") String comid, @Param("sno") String sno);


    /**
     * 更新业务员素材的分享次数
     *
     * @param sno   素材编号
     * @param comid 渠道编码
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/11/15
     */
    void updateSharenum(@Param("comid") String comid, @Param("sno") String sno);


    /**
     * 更新业务员素材的阅读次数
     *
     * @param sno   素材编号
     * @param comid 渠道编码
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/11/18
     */
    void updateYdnum(@Param("comid") String comid, @Param("sno") String sno);


    /**
     * 根据渠道编码和openid查询用户userid、工号和内外勤标识
     *
     * @param openid
     * @param comid  渠道编码
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/11/19
     */
    Map<String, Object> selectUseridByOpenid(@Param("comid") String comid, @Param("openid") String openid);
}
