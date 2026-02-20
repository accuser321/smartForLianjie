package com.nh.smart.dao.record;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nh.smart.entity.record.SmartComWxmsg;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * smart_com_wxmsg#消息记录表 Dao接口
 */
@Repository
public interface SmartComWxmsgDao extends BaseMapper<SmartComWxmsg> {

    /**
     * 查询业务员被咨询数
     *
     * @Param: [comid, tuserid]
     * @return: int
     * @Author: 张宁
     * @Date: 2019/11/8
     */
    int selectWxmsgByUserd(@Param("comid") String comid, @Param("tuserid") Integer tuserid);

    /**
     * 查询消息列表
     *
     * @Param: [page, comid, userid]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.nh.smart.entity.record.SmartComWxmsg>
     * @Author: 张宁
     * @Date: 2019/11/8
     */
    IPage<Map<String, Object>> selectWxmsgPage(Page page, @Param("comid") String comid, @Param("empno") String empno);

    /**
     * 统计咨询数
     *
     * @Param: [comid, ywUserid]
     * @return: java.util.Map<java.lang.String ,   j   a  v a . l  a   n  g . O b ject>
     * @Author: 张宁
     * @Date: 2019/11/18
     */
    Map<String, Object> selectSumZX(@Param("comid") String comid, @Param("empno") String empno, @Param("ywUserid") Integer ywUserid);

    /**
     * 添加更新咨询聊天记录
     *
     * @Param: [empno, khuserid, toString]
     * @return: java.lang.Integer
     * @Author: 张宁
     * @Date: 2019/11/30
     */
    Integer insertKjChat(@Param("comid") String comid, @Param("empno") String empno, @Param("khuserid") Integer khuserid, @Param("content") String content, @Param("read") String read, @Param("type") String type, @Param("date") String date);

    /**
     * 查询历史消息
     *
     * @Param: [comid, empno, khuserid]
     * @return: java.lang.String
     * @Author: 张宁
     * @Date: 2019/11/30
     */
    List<Map<String, Object>> getKjChat(@Param("comid") String comid, @Param("empno") String empno, @Param("khuserid") Integer khuserid, @Param("type") String type);

    /**
     * 更新消息状态
     *
     * @Param: [comid, empno, valueOf]
     * @return: void
     * @Author: 张宁
     * @Date: 2019/12/5
     */
    void updateRead(@Param("comid") String comid, @Param("empno") String empno, @Param("userid") Integer userid);

    /**
     * 修改消息状态
     *
     * @Param: [userid, empno, type]
     * @return: void
     * @Author: 张宁
     * @Date: 2019/12/6
     */
    void updateMSG(@Param("comid") String comid, @Param("userid") Integer userid, @Param("empno") String empno, @Param("type") String type);

    /**
     * 查询消息记录
     *
     * @param comid
     * @param userid
     * @param msgtype
     * @param btagcode
     * @param otype
     * @return
     * @Author: 张晓峰
     * @Date: 2019/12/23
     */
    IPage<SmartComWxmsg> QueryMessage(Page pageEntity, @Param("comid") String comid, @Param("userid") String userid, @Param("msgtype") String msgtype, @Param("btagcode") String btagcode, @Param("otype") String otype);

    /**
     * 查询消息状态
     *
     * @param comid
     * @param msgid
     * @return
     */
    String findStatus(@Param("comid") String comid, @Param("msgid") String msgid);

    /**
     * 修改消息状态
     *
     * @return
     * @Param: msgid 消息id
     * @Author: 张晓峰
     * @Date: 2019/12/24
     */
    void updateStatus(@Param("comid") String comid, @Param("msgid") String msgid, @Param("status") String status);


    /**
     * 查询消息中心未读条数
     *
     * @param comid  渠道编码
     * @param userid 用户id
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2020/1/11
     */
    Integer selectCountWDMessage(@Param("comid") String comid, @Param("userid") Integer userid);
}
