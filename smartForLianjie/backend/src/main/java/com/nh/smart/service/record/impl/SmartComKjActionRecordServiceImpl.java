package com.nh.smart.service.record.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nh.smart.dao.material.SmartComKjLibwDao;
import com.nh.smart.dao.material.SmartComKjLibwEmpnoDao;
import com.nh.smart.dao.record.*;
import com.nh.smart.entity.material.SmartComKjLibwEmpno;
import com.nh.smart.entity.record.*;
import com.nh.smart.entity.vo.UserAction;
import com.nh.smart.service.material.SmartComKjLibwService;
import com.nh.smart.service.record.SmartComKjActionRecordService;
import com.nh.smart.util.JwtTokenUtil;
import com.nh.smart.util.MessageTemplateUtils;
import com.nh.smart.util.RecordTreeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


/**
 * smart_com_kj_action_record#渠道业务员行为记录表 业务逻辑接口的实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SmartComKjActionRecordServiceImpl extends ServiceImpl<SmartComKjActionRecordDao, SmartComKjActionRecord> implements SmartComKjActionRecordService {

    @Autowired
    private SmartComKjActionRecordDao smartComKjActionRecordDao;

    // 客户与关系dao
    @Autowired
    private SmartKjCustomEmpshipDao smartKjCustomEmpshipDao;

    // 业务员行为次数dao
    @Autowired
    private SmartComKjActionCountDao smartComKjActionCountDao;

    // 内容标签行为
    @Autowired
    private SmartComKjActionLabelDao smartComKjActionLabelDao;

    // 每日统计
    @Autowired
    private SmartComKjActionEmpcountDao smartComKjActionEmpcountDao;

    @Autowired
    private SmartComKjLibwService smartComKjLibwService;

    @Autowired
    private SmartComKjLibwEmpnoDao smartComKjLibwEmpnoDao;

    @Autowired
    private SmartComKjLibwDao smartComKjLibwDao;

    // 消息记录统计
    @Autowired
    private SmartComWxmsgDao smartComWxmsgDao;
    /**
     * 添加行为记录
     *
     * @Param: [smartComKjActionRecord]
     * @return: java.lang.Integer
     * @Author: 张宁
     * @Date: 2019/11/5
     */
    @Override
    public synchronized Integer insertActionCord(SmartComKjActionRecord smartComKjActionRecord, String serverName) throws Exception {
        String url = serverName + "/article/looked";
        // 业务成功标志
        Integer flag = 1;
        // 获取comid
        String comid = JwtTokenUtil.getComid();
//        String comid="NHSX";
//        String openid="oC1620207gyHNbXC3_0nVX5SGkCw";
        smartComKjActionRecord.setComid(comid);
        String openid = JwtTokenUtil.getOpenid();
        // 获取查看人用户信息
        Map<String, Object> opidMap = smartComKjActionRecordDao.selectUseridByOpenid(openid, null, smartComKjActionRecord.getComid(), null);
        String headimg = (String) opidMap.get("headimg");
        Integer userid = (Integer) opidMap.get("userid");
        String empno = null;
        String rytype = (String) opidMap.get("rytype");
        // 声明客户与关系实体
        SmartKjCustomEmpship smartComKjCustomEmpship = null;
        smartComKjCustomEmpship = new SmartKjCustomEmpship();
        // 设置同事关系
        smartComKjCustomEmpship.setTsflag("0");
        // 设置comid
        smartComKjCustomEmpship.setComid(smartComKjActionRecord.getComid());
        // 设置业务员工号
        smartComKjCustomEmpship.setEmpno(smartComKjActionRecord.getEmpno());
        // 设置初始刷新时间
        smartComKjCustomEmpship.setFlushtime(new Date());
        // 设置建立时间
        smartComKjCustomEmpship.setIntime(new Date());
        // 设置创建来源
        smartComKjCustomEmpship.setFromsctype(smartComKjActionRecord.getOtype());
        // 设置来源素材编号
        smartComKjCustomEmpship.setFromsno(smartComKjActionRecord.getSno());
        // 判断是否为准客户
        smartComKjCustomEmpship.setZkhflag("0");
        //是否是点赞
        if ("4".equals(smartComKjActionRecord.getOtype())) {
          smartComKjCustomEmpship.setPraiseflag("1");
        } else {
          smartComKjCustomEmpship.setPraiseflag("0");
        }
        if (smartComKjActionRecordDao.selectCustomLabel(smartComKjActionRecord.getComid(), smartComKjActionRecord.getEmpno(), userid, "01") > 0) {
            smartComKjCustomEmpship.setZkhflag("1");
        }
        if (!"Y".equals(rytype)) {
            empno = (String) opidMap.get("empno");
            // 判断是否是自己查看
            if (!StringUtils.isEmpty(empno)) {
                // 设置同事关系
                smartComKjCustomEmpship.setTsflag("1");
            }
        }
        // 设置查看人userid
        smartComKjActionRecord.setUserid(userid);
        // 设置用户userid
        smartComKjCustomEmpship.setUserid(userid);
        // 声明业务员行为次数实体
        SmartComKjActionCount smartComKjActionCount = SmartComKjActionCount.builder().comid(smartComKjActionRecord.getComid()).empno(smartComKjActionRecord.getEmpno()).userid(userid).btagcode(smartComKjActionRecord.getBtagcode()).otype(smartComKjActionRecord.getOtype()).num(1).lasttime(new Date()).build();
        // 获取当前业务员userid
        opidMap = smartComKjActionRecordDao.selectUseridByOpenid(null, smartComKjActionRecord.getEmpno(), smartComKjActionRecord.getComid(), smartComKjActionRecord.getRytype());
        userid = (Integer) opidMap.get("userid");
        // 判断素材是否是第一次被转发查看
        if (null == smartComKjActionRecord.getSuserid() || smartComKjActionRecord.getSuserid().equals("")) {
            // 设置上级来源人用户
            smartComKjActionRecord.setSuserid(userid);
        } else {
            if (smartComKjActionRecord.getUserid().equals(smartComKjActionRecord.getSuserid()) && !smartComKjActionRecord.getSuserid().equals(userid)) {
                return -1;
            }
        }

        // 更新素材阅读次数与分享次数
        if (!StringUtils.isEmpty(smartComKjActionRecord.getSno())) {
            if ("2".equals(smartComKjActionRecord.getOtype())) {
              //素材库与业务员素材库区别？
              //新素材与老素材相同时取业务员素材表否则素材库
                if (smartComKjActionRecord.getSno().equals(smartComKjActionRecord.getOsno())) {
                    smartComKjLibwEmpnoDao.updateSharenum(comid, smartComKjActionRecord.getSno());
                } else {
                    if ("1".equals(smartComKjActionRecord.getBtagcode())) {
                        smartComKjLibwDao.updateSharenum(comid, smartComKjActionRecord.getOsno());
                    } else if ("10".equals(smartComKjActionRecord.getBtagcode())) {
                        smartComKjLibwDao.updateSharenum(comid, smartComKjActionRecord.getOsno());
                        smartComKjLibwEmpnoDao.updateSharenum(comid, smartComKjActionRecord.getSno());
                    }
                }
            }
        }

        // 查询文章是否已经推送过
        int num = 0;
        if ("1".equals(smartComKjActionRecord.getBtagcode()) || "10".equals(smartComKjActionRecord.getBtagcode())) {
            num = smartComKjActionRecordDao.selectWZread(smartComKjActionRecord);
        }
        // 添加用户行为记录
        // 设置行为记录开始时间
        smartComKjActionRecord.setBegtime(new Date());
        flag *= smartComKjActionRecordDao.insert(smartComKjActionRecord) < 1 ? 0 : 1;
        smartComKjActionRecord.setHashnum(System.currentTimeMillis() + smartComKjActionRecord.getComid() + smartComKjActionRecord.getUserid() + smartComKjActionRecord.getEmpno() + smartComKjActionRecord.getOtype());
        smartComKjActionRecordDao.updateActionCordTime(UserAction.builder().hashnum(smartComKjActionRecord.getHashnum()).id(smartComKjActionRecord.getId()).begin(smartComKjActionRecord.getBegtime()).build());
        // 添加更新业务员行为次数
        flag *= smartComKjActionCountDao.insertActionCount(smartComKjActionCount) < 1 ? 0 : 1;
        Map<String, Object> messageMap = new HashMap<>();
        List<String> labidlist = null;
        if (!smartComKjActionRecord.getSuserid().equals(smartComKjActionRecord.getUserid()) && !smartComKjActionRecord.getUserid().equals(userid)) {
            String title = null;
            if (smartComKjActionRecord.getSno() != null && (smartComKjActionRecord.getBtagcode().equals("1") || smartComKjActionRecord.getBtagcode().equals("10"))) {
                String sno=smartComKjActionRecord.getSno();
                String btagcode="10".equals(smartComKjActionRecord.getBtagcode()) ? "1" : smartComKjActionRecord.getBtagcode();
                SmartComKjLibwEmpno smartComKjLibwEmpno = smartComKjLibwService.selectOneBySno(sno, btagcode, 3);
                title = smartComKjLibwEmpno.getStitle();
                // 获取素材标签
                labidlist = smartComKjLibwDao.selectBQ(smartComKjActionRecord.getComid(), smartComKjActionRecord.getOsno());
                smartComKjActionRecord.setLabidlist(labidlist);
            }
            // 添加消息提醒map
            if (!StringUtils.isEmpty(openid)) {
                messageMap.put("empno", smartComKjActionRecord.getEmpno());
                messageMap.put("btagcodes", smartComKjActionRecord.getBtagcode());
                messageMap.put("emotype", smartComKjActionRecord.getRytype());
                messageMap.put("headimg", headimg);
                if (num <=0&&smartComKjActionRecord.getBtagcode().equals("10")) {
                  messageMap.put("url", url + "?recordtype=10&sno=" + smartComKjActionRecord.getSno());
                  if ("1".equals(smartComKjActionRecord.getOtype())) {
                    // 浏览
                    messageMap.put("otype", "1");
                    messageMap.put("btagcode", "5");
                    messageMap.put("stitle", title);
                    messageMap.put("sno", smartComKjActionRecord.getSno());
                    remindMessage(messageMap);
                  }
                }else if(smartComKjActionRecord.getBtagcode().equals("9")){
                  messageMap.put("url", url + "?recordtype=9&empno=" + smartComKjActionRecord.getEmpno());
                  if ("1".equals(smartComKjActionRecord.getOtype())) {
                    // 浏览
                    messageMap.put("otype", "1");
                    messageMap.put("btagcode", "1");
                    remindMessage(messageMap);
                  } else if ("2".equals(smartComKjActionRecord.getOtype())) {
                    // 转发
                    messageMap.put("otype", "3");
                    messageMap.put("btagcode", "1");
                    remindMessage(messageMap);
                  } else if ("7".equals(smartComKjActionRecord.getOtype())) {
                    //保存电话
                    messageMap.put("btagcode", "2");
                    remindMessage(messageMap);
                  } else if ("6".equals(smartComKjActionRecord.getOtype())) {
                    //保存电话
                    messageMap.put("btagcode", "8");
                    remindMessage(messageMap);
                  }
                }
            }
            // 添加客户与业务员关系
            flag *= smartKjCustomEmpshipDao.insertCustomEmpship(smartComKjCustomEmpship) < 1 ? 0 : 1;
            // 添加渠道客户内容标签行为关系
            labidlist = smartComKjActionRecord.getLabidlist();
            if (labidlist != null && labidlist.size() > 0) {
                flag *= smartComKjActionLabelDao.insertActionLabel(smartComKjActionRecord, labidlist) < 1 ? 0 : 1;
            }
        }
        if (smartComKjActionRecord.getSuserid().equals(smartComKjActionRecord.getUserid()) || smartComKjActionRecord.getSuserid().equals(userid)) {
            if (("1".equals(smartComKjActionRecord.getBtagcode())
                    || "10".equals(smartComKjActionRecord.getBtagcode())
              || ("9".equals(smartComKjActionRecord.getBtagcode()) && !StringUtils.isEmpty(smartComKjActionRecord.getSno())))
                    && "1".equals(smartComKjActionRecord.getOtype()) && smartComKjActionRecord.getSuserid().equals(userid)) {
                // 查询该文章是否转发过
                int kh = 0;
                String useZF = smartComKjActionRecordDao.selectZF(smartComKjActionRecord);
                if ((Integer.valueOf(useZF) < 2)) {
                    kh = 1;
                }
              // 进行文章素材二转更新
              if (!"2".equals(smartComKjActionRecord.getOtype())) {
                //ydnum  名片浏览？
                smartComKjActionRecordDao.updateSCNUM(0, 0, kh, smartComKjActionRecord, "9".equals(smartComKjActionRecord.getBtagcode()) ? 1 : null);
              }
            }
            // 业务员每日行为统计业务
            // 新增获客数
            int xzkhnum = smartKjCustomEmpshipDao.selectCustomEmpship(smartComKjActionRecord.getComid(), smartComKjActionRecord.getEmpno());
            // 被咨询数量
            int zxnum = 0;
            // 文章转发数
            int wzzfnum = 0;
            // 文章二次转发数
            int wzzfsnum = 0;
            // 文章浏览数
            int wzllnum = 0;
            // 名片转发数
            int mpzfnum = 0;
            // 名片二次转发数
            int mpzfsnum = 0;
            // 名片浏览数
            int mpllnum = 0;
            // 名片被点赞
            int mpdznum = 0;
            // 转发人数
            int khnum = 0;
            switch (smartComKjActionRecord.getOtype()) {
                case "1":
                    if ("10".equals(smartComKjActionRecord.getBtagcode()) || "1".equals(smartComKjActionRecord.getBtagcode())) {
                        if (smartComKjActionRecord.getUserid().equals(smartComKjActionRecord.getSuserid())) {
                            wzllnum = 1;
                        }
                    } else if ("9".equals(smartComKjActionRecord.getBtagcode())) {
                      if (smartComKjActionRecord.getUserid().equals(smartComKjActionRecord.getSuserid())) {
                        mpllnum = 1;
                      }
                    }
                    break;
                case "2":
                    if ("10".equals(smartComKjActionRecord.getBtagcode()) || "1".equals(smartComKjActionRecord.getBtagcode())) {
                        if (smartComKjActionRecord.getSuserid().equals(userid) && smartComKjActionRecord.getUserid().equals(userid)) {
                            wzzfnum = 1;
                        } else {
                            wzzfsnum = 1;
                            // 查询该文章是否转发过
                            String useZF = smartComKjActionRecordDao.selectZF(smartComKjActionRecord);
                            if ((Integer.valueOf(useZF) < 2)) {
                                khnum = 1;
                            }
                            // 进行文章素材二转更新
                            smartComKjActionRecordDao.updateSCNUM(khnum, wzzfsnum, null, smartComKjActionRecord, null);

                        }
                    }else if ("9".equals(smartComKjActionRecord.getBtagcode())) {
                      if (smartComKjActionRecord.getSuserid().equals(userid) && smartComKjActionRecord.getUserid().equals(userid)) {
                        mpzfnum = 1;
                      } else {
                        mpzfsnum = 1;
                        if (!StringUtils.isEmpty(smartComKjActionRecord.getSno())) {
                          // 查询该名片是否转发过
                          String useZF = smartComKjActionRecordDao.selectZF(smartComKjActionRecord);
                          if (Integer.valueOf(useZF) < 2) {
                            khnum = 1;
                          }
                          // 进行海报素材二转更新
                          smartComKjActionRecordDao.updateSCNUM(khnum, mpzfsnum, null, smartComKjActionRecord, null);
                        }
                      }
                    }
                    break;
                case "4":
                  mpdznum = 1;
                  break;
                case "8":
                  zxnum = 1;
                  break;
            }
            // 添加每日统计
            flag *= smartComKjActionEmpcountDao.insertActionEmpno(smartComKjActionRecord, xzkhnum, wzzfnum, wzzfsnum, wzllnum, mpzfnum, mpzfsnum, mpllnum, mpdznum, zxnum) < 1 ? 0 : 1;
        }
        return flag > 0 ? smartComKjActionRecord.getId() : 0;
    }

    /**
     * 更新行为记录浏览时间
     *
     * @Param: [userAction]
     * @return: java.lang.Integer
     * @Author: 张宁
     * @Date: 2019/11/6
     */
    @Override
    public Integer updateActionCordTime(UserAction userAction) {
        SmartComKjActionRecord smartComKjActionRecord = smartComKjActionRecordDao.selectById(userAction.getId());
        userAction.setHashnum(System.currentTimeMillis() + smartComKjActionRecord.getComid() + smartComKjActionRecord.getUserid() + smartComKjActionRecord.getEmpno() + smartComKjActionRecord.getOtype());
        return smartComKjActionRecordDao.updateActionCordTime(userAction);
    }

    /**
     * 发送消息提醒
     *
     * @Param: [smartComKjActionRecord]
     * @return: com.saascs.common.entity.Result
     * @Author: 张宁
     * @Date: 2019/11/8
     */
    @Override
    public void remindMessage(Map<String, Object> map) throws Exception {
        // 获取当前客户的微信昵称与微信头像
        String openid = JwtTokenUtil.getOpenid();
        String comid = JwtTokenUtil.getComid();
        Map<String, Object> usermap = smartComKjActionRecordDao.selectUseridByOpenid(openid, null, comid, null);
        // 微信昵称
        String nickname = (String) usermap.get("nickname");
        // 微信头像
        String headimg = (String) usermap.get("headimg");
        // 客户userid
        Integer userid = (Integer) usermap.get("userid");
        // 消息跳转页面
        String url = (String) map.get("url");
        // 消息类型
        String otype = (String) map.get("otype");
        // 素材类型
        String btagcode = (String) map.get("btagcode");
        // 业务员编号
        String empno = (String) map.get("empno");
        // 业务员类型
        String emotype = (String) map.get("emotype");
        // 素材标题
        String stitle = null;
        // 素材编号
        String sno = null;
        // 获取业务员opeid
        Map<String, Object> empnoMap = smartComKjActionRecordDao.selectUseridByOpenid(null, empno, comid, emotype);
        String empnoOpenid = (String) empnoMap.get("openid");
        Integer empnoUserid = (Integer) empnoMap.get("userid");
        // 根据消息提醒类型调用不同接口
        JSONObject jsonObject = null;
        // 素材标题
        stitle = (String) map.get("stitle");
        sno = (String) map.get("sno");
        jsonObject = MessageTemplateUtils.sendKFMessage(empnoOpenid, "用户【" + nickname + "】浏览文章提醒", nickname + "正在查看您分享的【" + stitle + "】文章，看来TA对此感兴趣", url, headimg, comid);
    }


    /**
     * 人脉-寻找客户
     *
     * @Param: [userid]
     * @return: java.util.List<com.nh.smart.entity.record.SmarttComKjActionRecord>
     * @Author: 张宁
     * @Date: 2019/11/9
     */
    @Override
    public Map<String, Object> getKHRelation(String userid, String sno) throws Exception {
      // 获取当前用户的openID与comID
      String comid = JwtTokenUtil.getComid();
      String openid = JwtTokenUtil.getOpenid();
      String empno = JwtTokenUtil.getEmpno();
      // 获取查看人用户信息
      Map<String, Object> opidMap = smartComKjActionRecordDao.selectUseridByOpenid(openid, null, comid, null);
      Integer empnoUserid = (Integer) opidMap.get("userid");
      String headimg = (String) opidMap.get("headimg");
      String nickname = (String) opidMap.get("nickname");
      // 获取业务员关系列表
      List<SmartComKjActionRecord> recordList = smartComKjActionRecordDao.getKHRelation(comid, empno, empnoUserid,sno);
      List<Map<String, Object>> relationship = new ArrayList<>();
      List<Map<String, Object>> message = new ArrayList<>();
      // 树形结构
      Map<String, Object> result = new HashMap<>();
      result.put("relationship", relationship);
      result.put("message", message);
      Map<String, Object> map = RecordTreeUtil.buildTree(result, recordList, empnoUserid, Integer.valueOf(userid));
      List<Map<String, Object>> messageList = ((List<Map<String, Object>>) map.get("message"));
      relationship = ((List<Map<String, Object>>) map.get("relationship"));
      Map<String, Object> empnoMap = new HashMap<>();
      empnoMap.put("userid", empnoUserid);
      empnoMap.put("name", nickname);
      empnoMap.put("headimg", headimg);
      messageList.add(empnoMap);
      result.put("relationship", relationship.stream().distinct().collect(Collectors.toList()));
      result.put("message", messageList.stream().distinct().collect(Collectors.toList()));
      result.remove("treelist");
      return result;
    }
    /**
     * 文章-谁看了我-谁转发了我
     *
     * @Param: [map]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<java.util.Map < java.lang.String, java.lang.Object>>
     * @Author: 张宁
     * @Date: 2019/11/18
     */
    @Override
    public IPage<Map<String, Object>> getWZRdZf(Map<String, Object> map) throws Exception {
      // 获取当前用户的openID与comID
      String comid = JwtTokenUtil.getComid();
      String empno = JwtTokenUtil.getEmpno();
      Page<Map<String, Object>> pageModel = new Page<>((Integer) (map.get("page")), (Integer) (map.get("size")));
      return smartComKjActionRecordDao.getWZRdZf(pageModel, comid, empno, (String) map.get("recordtype"), (String) map.get("sno"), (String) map.get("type"), Integer.valueOf((String) map.get("userid")));
    }


    /**
     * 修改消息状态
     *
     * @Param: [userid, empno, type]
     * @return: void
     * @Author: 张宁
     * @Date: 2019/12/6
     */
    @Override
    public void updateMSG(String userid, String empno, String type) throws Exception {
      String comid = JwtTokenUtil.getComid();
      smartComWxmsgDao.updateMSG(comid, Integer.valueOf(userid), empno, type);
    }



    /**
     * 根据文章编号查询文章详情
     *
     * @Param: [comid, sno]
     * @return: java.util.List<com.nh.smart.entity.material.SmarttComKjLibw>
     * @Author: 张宁
     * @Date: 2019/12/25
     */
    @Override
    public List<SmartComKjLibwEmpno> getWZXQ(List<String> wzlist) throws Exception {
      List<SmartComKjLibwEmpno> smartComKjLibws = smartComKjLibwDao.selectWzList(JwtTokenUtil.getComid(), wzlist);
      return smartComKjLibws;
    }

  /**
   * 添加用户咨询消息
   *
   * @Param: [ywuserid, khuserid, content]
   * @return: java.lang.Integer
   * @Author: 张宁
   * @Date: 2019/11/30
   */
  @Override
  public Integer insertKjChat(String empno, String khuserid, String content, String type, String date, String read, String rytype, String serverName) throws Exception {
    // 查询业务员信息
    String comid = JwtTokenUtil.getComid();
    List<Map<String, Object>> kjChat = smartComWxmsgDao.getKjChat(comid, empno, Integer.valueOf(khuserid), type);
    String url = serverName + "/article/Consult?askuserid=" + khuserid + "&empno=" + empno + "&orytype=" + rytype;
    if (kjChat == null || kjChat.isEmpty()) {
      if ("1".equals(type)) {
        url = url + "&news=1";
        Map<String, Object> KHmap = smartComKjActionRecordDao.selectUseridByUserid(comid, Integer.valueOf(khuserid));
        Map<String, Object> YWmap = smartComKjActionRecordDao.selectUseridByOpenid(null, empno, comid, rytype);
        String openid = (String) KHmap.get("openid");
        String ywheadimg = (String) YWmap.get("headimg");
        String ywnickname = (String) YWmap.get("nickname");
        MessageTemplateUtils.sendKFMessage(openid, "您有一条私信", "【" + ywnickname + "】给您发送了一条私信，请查阅！", url, ywheadimg, comid);
      } else {
        Map<String, Object> YWmap = smartComKjActionRecordDao.selectUseridByOpenid(null, empno, comid, rytype);
        Map<String, Object> KHmap = smartComKjActionRecordDao.selectUseridByUserid(comid, Integer.valueOf(khuserid));
        String openid = (String) YWmap.get("openid");
        String kuheadimg = (String) KHmap.get("headimg");
        String kunickname = (String) KHmap.get("nickname");
        MessageTemplateUtils.sendKFMessage(openid, "您有一条私信", "【" + kunickname + "】给您发送了一条私信，请查阅！", url, kuheadimg, comid);
      }
    } else if (!kjChat.isEmpty() && "1".equals(read)) {
      Map<String, Object> map = kjChat.get(0);
      Date intime = (Date) map.get("intime");
      String readflag = (String) map.get("readflag");
      if ("0".equals(readflag)) {
        if (((System.currentTimeMillis() - intime.getTime()) / 1000 / 60) >= 30) {
          if ("1".equals(type)) {
            url = url + "&news=1";
            Map<String, Object> KHmap = smartComKjActionRecordDao.selectUseridByUserid(comid, Integer.valueOf(khuserid));
            Map<String, Object> YWmap = smartComKjActionRecordDao.selectUseridByOpenid(null, empno, comid, rytype);
            String openid = (String) KHmap.get("openid");
            String ywheadimg = (String) YWmap.get("headimg");
            String ywnickname = (String) YWmap.get("nickname");
            MessageTemplateUtils.sendKFMessage(openid, "您有一条私信", "【" + ywnickname + "】给您发送了一条私信，请查阅！", url, ywheadimg, comid);
          } else {
            Map<String, Object> YWmap = smartComKjActionRecordDao.selectUseridByOpenid(null, empno, comid, rytype);
            Map<String, Object> KHmap = smartComKjActionRecordDao.selectUseridByUserid(comid, Integer.valueOf(khuserid));
            String openid = (String) YWmap.get("openid");
            String kuheadimg = (String) KHmap.get("headimg");
            String kunickname = (String) KHmap.get("nickname");
            MessageTemplateUtils.sendKFMessage(openid, "您有一条私信", "【" + kunickname + "】给您发送了一条私信，请查阅！", url, kuheadimg, comid);
          }
        }
      }
    }
    return smartComWxmsgDao.insertKjChat(comid, empno, Integer.valueOf(khuserid), content, "1".equals(read) ? "0" : "1", type, date);
  }

  /**
   * 点赞处理
   *
   * @Param: [userid, flag]
   * @return: java.lang.Integer
   * @Author: 张宁
   * @Date: 2020/1/19
   */
  @Override
  public Integer updateDZ(String userid, String flag, String empno) throws Exception {
    // 获取当前用户的openID与comID
    String comid = JwtTokenUtil.getComid();
    Integer integer = smartComKjActionRecordDao.updateDZ(comid, empno, Integer.valueOf(userid), flag);
    return 1;
  }

}
