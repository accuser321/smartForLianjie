package com.nh.smart.dao.material;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nh.smart.entity.material.SmartComMuserCard;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * smart_com_muser_card#渠道业务员名片表 Dao接口
 */
@Repository
public interface SmartComMuserCardDao extends BaseMapper<SmartComMuserCard> {

    /**
     * 查询业务员名片
     *
     * @param empno 工号
     * @param comid 渠道编码
     * @param type  用户身份标识，W外勤，N内勤
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/11/6
     */
    SmartComMuserCard selectUserCard(@Param("empno") String empno, @Param("comid") String comid, @Param("type") String type);


    /**
     * 名片编辑
     *
     * @param smartComMuserCard 名片对象
     * @param empno           工号
     * @param comid           渠道编码
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/11/6
     */
    void updateCard(@Param("item") SmartComMuserCard smartComMuserCard, @Param("empno") String empno, @Param("comid") String comid);


    /**
     * 修改头像信息
     *
     * @param userid  用户编码
     * @param headimg 头像地址
     * @param comid   渠道编码
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/11/11
     */
    void updateHeadimg(@Param("userid") Integer userid, @Param("comid") String comid, @Param("headimg") String headimg);


    /**
     * 查询业务员名片信息
     *
     * @param empno 工号
     * @param comid 渠道编码
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/11/19
     */
    SmartComMuserCard selectCardInfo(@Param("comid") String comid, @Param("empno") String empno);


    /**
     * 查询默认渠道默认风采照片和微官网
     *
     * @param comid 渠道编码
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/11/29
     */
    List<Map<String, String>> getDefaultPhotoAndWebSite(@Param("comid") String comid);


    /**
     *  根据empno和人员类型查询用户userid、微信头像、openid
     *
     * @param comid 渠道编码
     * @param type  人员类型
     * @param empno 工号
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/11/29
     */
    Map<String, Object> getUserInfo(@Param("comid") String comid, @Param("type") String type, @Param("empno") String empno);
    /**
     * 查询名片照片
     *
     * @param empno   工号
     * @param comid   渠道编码
     * @param soucode 名片照片的枚举值编码
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/11/6
     */
    List<String> selectPhotoList(@Param("empno") String empno, @Param("comid") String comid, @Param("soucode") String soucode);
}
