package com.nh.smart.service.material;

import com.alibaba.fastjson.JSONObject;
import com.nh.smart.entity.material.SmartComKjLibwEmpno;
import com.nh.smart.entity.material.SmartComMuserCard;

import java.util.List;
import java.util.Map;

/**
 * smart_com_kj_libw#素材库 业务逻辑的接口类
 */
public interface SmartComKjLibwService {

    /**
     * 素材列表查询
     *
     * @param map 参数集合
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/11/5
     */
    JSONObject selectPage(Map<String, Object> map) throws Exception;


    /**
     * 素材详情
     *
     * @param sno      素材编号
     * @param btagcode 素材类型编码
     * @param flag     查询详情标识，0查询素材库，1查询业务员素材表
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/11/7
     */
    SmartComKjLibwEmpno selectOneBySno(String sno, String btagcode, Integer flag) throws Exception;


    /**
     * 文章导入
     *
     * @param url   素材编号
     * @param bq    文章标签编码集合
     * @param comid 渠道编码
     * @param empno 业务员工号
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/11/7
     */
    JSONObject importWZ(String url, List<String> bq, String comid, String empno) throws Exception;


    /**
     * 文章制作
     *
     * @param title 文章标题
     * @param text  文章内容文本
     * @param bq    文章标签编码集合
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/11/7
     */
    void insertWZ(String title, String text, List<String> bq) throws Exception;


    /**
     * 文章编辑
     *
     * @param sno   文章编号
     * @param osno  原始文章编号
     * @param title 文章标题
     * @param text  文章内容文本
     * @param bq    文章标签编码集合
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/11/7
     */
    void updateWZ(String sno, String osno, String title, String text, List<String> bq) throws Exception;


    /**
     * 文库文章转发
     *
     * @param sno   文章编号
     * @param osno  原始文章编号
     * @param empno 工号
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/11/8
     */
    SmartComKjLibwEmpno ForwardWZ(String sno, String osno, String empno) throws Exception;


    /**
     * 变成我的
     *
     * @param sno   文章编号
     * @param osno  原始文章编号
     * @param empno 原始业务员编号
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/11/12
     */
    String BecomeWZ(String sno, String osno, String empno) throws Exception;


    /**
     * 我的素材列表删除
     *
     * @param sno 素材编号
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/11/8
     */
    void delete(String sno) throws Exception;


    /**
     * 保存渠道素材标签表
     *
     * @param comid 渠道编码
     * @param empno 工号
     * @param sno   素材编号
     * @param bq    文章标签编码集合
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/11/7
     */
    void saveSCAndBQ(String comid, String empno, String sno, List<String> bq) throws Exception;
    /**
     *@Author ww
     * @Description //TODO 获取文章分类
     * @Date
     * @Param
     * @return
    **/
    List<Map<String, Object>> selectKJFL(String tagcode) throws Exception;

    /**
     * 查询业务员名片
     *
     * @param empno      用户工号
     * @param flag       是否需要查询风采照片，0不查询代表在文章里面调用名片接口，1查询代表访问的是AI名片页面
     * @param type       用户身份标识，W外勤，N内勤
     * @param serverName 当前访问的域名
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/11/6
     */
    SmartComMuserCard selectUserCard(String empno, String flag, String type, String serverName) throws Exception;
    /**
     *@Author ww
     * @Description //TODO 获取移动端图片资源
     * @Date
     * @Param
     * @return
    **/
    Map pictureList() throws Exception;
}
