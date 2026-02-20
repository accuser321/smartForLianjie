package com.nh.smart.dao.material;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nh.smart.entity.material.SmartComKjLibw;
import com.nh.smart.entity.material.SmartComKjLibwEmpno;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * smart_com_kj_libw#素材库 Dao接口
 */
@Repository
public interface SmartComKjLibwDao extends BaseMapper<SmartComKjLibw> {

    /**
     * 素材库素材列表查询
     *
     * @param pageEntity 分页实体
     * @param stagcode   类型
     * @param btagcode   分类
     * @param stitle     搜索条件标题
     * @param comid      渠道id
     * @return
     * @author 王名渤
     * @date 2019/11/5
     */
    IPage<SmartComKjLibw> selectPageByBtagcode(@Param("pageEntity") Page pageEntity, @Param("stagcode") String stagcode, @Param("btagcode") String btagcode,
                                             @Param("stitle") String stitle, @Param("comid") String comid, @Param("dt") String dt);


    /**
     * 素材库素材详情
     *
     * @param sno      素材编号
     * @param btagcode 素材类型编码
     * @param comid    渠道编码
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/11/7
     */
    SmartComKjLibwEmpno selectOneBySno(@Param("comid") String comid, @Param("sno") String sno, @Param("btagcode") String btagcode);


    /**
     * 根据素材编号查询素材库的文章信息和素材标签信息
     *
     * @param sno   素材编号
     * @param comid 渠道编码
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/11/8
     */
    SmartComKjLibw selectWZInfoFromK(@Param("comid") String comid, @Param("sno") String sno);


    /**
     * 更新文库素材的分享次数
     *
     * @param osno  素材编号
     * @param comid 渠道编码
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/11/13
     */
    void updateSharenum(@Param("comid") String comid, @Param("osno") String osno);


    /**
     * 根据标签名称查询标签编码
     *
     * @param tagname   标签名称
     * @param uptagcode 上级标签编码
     * @param comid     渠道编码
     * @return
     * @throws Exception
     * @author 孙晓洁
     * @date 2019/11/14
     */
    String selectTagcodeByTagname(@Param("comid") String comid, @Param("tagname") String tagname, @Param("uptagcode") String uptagcode);


    /**
     * 更新文库素材的阅读次数
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
     * 获取素材标签
     *
     * @Param: [comid, osno]
     * @return: java.util.List<java.lang.String>
     * @Author: 张宁
     * @Date: 2019/12/17
     */
    List<String> selectBQ(@Param("comid") String comid, @Param("osno") String osno);

    /**
     * 根据文章编号查询文章详情
     *
     * @Param: [comid, sno]
     * @return: java.util.List<com.nh.smart.entity.material.smartComKjLibw>
     * @Author: 张宁
     * @Date: 2019/12/25
     */
    List<SmartComKjLibwEmpno> selectWzList(@Param("comid") String comid, @Param("snolist") List<String> snolist);

    List<Map<String, Object>> selectKJFL(String tagcode, String comid);
}
