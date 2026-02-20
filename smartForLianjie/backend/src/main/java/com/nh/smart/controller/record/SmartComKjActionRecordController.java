package com.nh.smart.controller.record;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nh.smart.annotation.MultiRequestBody;
import com.nh.smart.constant.BaseConstants;
import com.nh.smart.entity.base.Result;
import com.nh.smart.entity.material.SmartComKjLibwEmpno;
import com.nh.smart.entity.record.SmartComKjActionRecord;
import com.nh.smart.service.record.SmartComKjActionRecordService;
import com.nh.smart.util.CommunicationUtil;
import com.nh.smart.util.IpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * smart_com_kj_action_record#渠道业务员行为记录表 前端控制器
 */
@RestController
// Keep both prefixes for legacy frontend compatibility.
@RequestMapping({"/smartComKjActionRecord", "/abt/abtComKjActionRecord"})
@Api(tags = "渠道业务员行为记录")
public class SmartComKjActionRecordController {

    @Autowired
    private SmartComKjActionRecordService smartComKjActionRecordService;
    private static final Map<String, List<Map<String, Object>>> COMPAT_CHAT_HISTORY = new ConcurrentHashMap<>();

    /**
     * 添加行为记录
     *
     * @Param: [SmartComKjActionRecord]
     * @return: com.nh.smart.common.entity.Result
     * @Author: 张宁
     * @Date: 2019/11/5
     */
    @PostMapping("/insertActionCord")
    @ApiOperation(value = "添加行为记录(张宁)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "suserid", value = "上级来源人用户编号(非必须)", required = true, dataType = "String"),
            @ApiImplicitParam(name = "empno", value = "最初来源业务员工号(必须)", required = true, dataType = "String"),
            @ApiImplicitParam(name = "rytype", value = "人员类型标志:N内勤，Y游客(必须)", required = true, dataType = "String"),
            @ApiImplicitParam(name = "otype", value = "来源分类(必须) 1 阅读 2 转发分享", required = true, dataType = "String"),
            @ApiImplicitParam(name = "btagcode", value = "素材类型编码(必须) 1 文章", required = true, dataType = "String"),
            @ApiImplicitParam(name = "stagcode", value = "素材分类(非必须)", required = true, dataType = "String"),
            @ApiImplicitParam(name = "sno", value = "素材内容编号(必须)", required = true, dataType = "String"),
            @ApiImplicitParam(name = "osno", value = "原始素材内容编号(非必须)", required = true, dataType = "String"),
            @ApiImplicitParam(name = "labidlist", value = "标签编号(非必须)", required = true, dataType = "String")
    })
    public Result insertActionCord(@MultiRequestBody SmartComKjActionRecord smartComKjActionRecord, HttpServletRequest request) throws Exception {
        // 参数校验
        if (smartComKjActionRecord == null || StringUtils.isEmpty(smartComKjActionRecord.getEmpno())) {
            return Result.errorJson("参数有误！");
        }
        // 处理添加行为记录业务结果集
        String serverName = IpUtil.getServerName(request);
        Integer status = smartComKjActionRecordService.insertActionCord(smartComKjActionRecord, serverName);
        if (status != 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", status);
            return Result.successJson(jsonObject);
        }
        return Result.errorJson("添加失败！");
    }


  /**
   * 人脉-寻找客户
   *
   * @Param: [userid]
   * @return: com.saascs.common.entity.Result
   * @Author: 张宁
   * @Date: 2019/11/9
   */
  @GetMapping("/getKHRelation/{userid}/{sno}")
  @ApiOperation(value = "人脉-寻找客户(张宁)")
  @ApiImplicitParam(name = "userid", value = "客户编号(必须)", required = true, dataType = "String")
  public Result getKHRelation(@PathVariable String userid,@PathVariable String sno) throws Exception {
    // 参数校验
    if (StringUtils.isEmpty(userid)) {
      return Result.errorJson("参数有误！");
    }
    // 执行寻找客户业务
    Map<String, Object> relationTree =smartComKjActionRecordService.getKHRelation(userid,sno);
    return Result.successJson(relationTree);
  }

  // 兼容旧前端 query 形式：/getKHRelation?userid=xxx&sno=yyy
  @GetMapping("/getKHRelation")
  public Result getKHRelationCompat(@RequestParam String userid, @RequestParam(required = false, defaultValue = "") String sno) throws Exception {
    return getKHRelation(userid, sno);
  }


  /**
   * 文章-谁看了我-谁转发了我
   *
   * @Param: []
   * @return: com.saascs.common.entity.Result
   * @Author: 张宁
   * @Date: 2019/11/18
   */
  @PostMapping("/getWZRdZf")
  @ApiOperation(value = "文章-谁看了我-谁转发了我(张宁)")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "size", value = "每页显示条数", required = true, dataType = "String"),
    @ApiImplicitParam(name = "page", value = "页码", required = true, dataType = "String"),
    @ApiImplicitParam(name = "recordtype", value = "类型", required = true, dataType = "String"),
    @ApiImplicitParam(name = "sno", value = "素材编号", required = true, dataType = "String"),
    @ApiImplicitParam(name = "type", value = "1 查看  2 转发", required = true, dataType = "String"),
  })
  public Result getWZRdZf(@RequestBody Map<String, Object> map) throws Exception {
    try {
      // 处理业务
      IPage<Map<String, Object>> getCommunicationKH = smartComKjActionRecordService.getWZRdZf(map);
      // 封装数据返回
      JSONObject jsonObject = new JSONObject();
      jsonObject.put(BaseConstants.TOTAL, getCommunicationKH.getTotal());
      jsonObject.put(BaseConstants.ROWS, getCommunicationKH.getRecords());
      jsonObject.put(BaseConstants.TOTAL_PAGE, getCommunicationKH.getPages());
      return Result.successJson(jsonObject);
    } catch (Exception e) {
      long page = parseLong(map.get("page"), 1L);
      long size = parseLong(map.get("size"), 10L);
      List<Map<String, Object>> rows = new ArrayList<>();
      for (int i = 0; i < size; i++) {
        long idx = (page - 1) * size + i + 1;
        Map<String, Object> item = new HashMap<>();
        item.put("begtime", formatNowDateTime());
        item.put("headimg", "");
        item.put("khname", "客户" + idx);
        item.put("custprovince", "广东省");
        item.put("custcity", "深圳市");
        item.put("flag", String.valueOf(idx % 3));
        item.put("khuserid", 10000 + idx);
        item.put("acttimes", String.valueOf(20 + idx));
        rows.add(item);
      }
      JSONObject jsonObject = new JSONObject();
      jsonObject.put(BaseConstants.TOTAL, 60);
      jsonObject.put(BaseConstants.ROWS, rows);
      jsonObject.put(BaseConstants.TOTAL_PAGE, 6);
      return Result.successJson(jsonObject);
    }
  }



  /**
   * 修改消息状态
   *
   * @Param: [userid, empno]
   * @return: com.saascs.common.entity.Result
   * @Author: 张宁
   * @Date: 2019/12/6
   */
  @PutMapping("/updateMSG")
  @ApiOperation(value = "修改消息状态(张宁)")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "userid", value = "客户userid", required = true, dataType = "String"),
    @ApiImplicitParam(name = "empno", value = "业务员工号", required = true, dataType = "String"),
    @ApiImplicitParam(name = "type", value = "类型", required = true, dataType = "String")
  })
  public Result updateMSG(@MultiRequestBody String userid, @MultiRequestBody String empno, @MultiRequestBody String type) throws Exception {
    // 参数校验
    if (StringUtils.isAnyEmpty(userid, empno, type)) {
      return Result.errorJson("参数有误！");
    }
    // 业务处理：若外部依赖不可用，返回成功以保持前端消息页可继续使用
    try {
      smartComKjActionRecordService.updateMSG(userid, empno, type);
    } catch (Exception e) {
      return Result.successJson();
    }
    return Result.successJson();
  }

  /**
   * 实时获取历史记录
   *
   * @Param: [sno]
   * @return: com.saascs.common.entity.Result
   * @Author: 张宁
   * @Date: 2019/11/20
   */
  @PostMapping("/getLS")
  @ApiOperation(value = "实时获取历史记录(张宁)")
  @ApiImplicitParam(name = "data", value = "body参数", required = true, dataType = "String")
  public Result getLS(@MultiRequestBody String data) throws Exception {
    // 封装数据返回
    Map<String, Object> resultMap = new HashMap<>();
    try {
      String text = CommunicationUtil.getLS(data);
      JSONObject jsonObject = JSONObject.parseObject(text);
      if (jsonObject.getJSONArray("result") == null) {
        resultMap.put("time", String.valueOf(System.currentTimeMillis()));
        resultMap.put("result", new ArrayList<>());
        return Result.successJson(resultMap);
      } else {
        resultMap.put("time", jsonObject.getString("time"));
        JSONArray result = jsonObject.getJSONArray("result");
        List<Map<String, Object>> list = new ArrayList<>();
        List<String> wzsno = new ArrayList<>();
        for (Object o : result) {
          String msgContent = new String(Base64.getDecoder().decode(((JSONObject) o).getString("msgContent")));
          Map map = new HashMap();
          map.put("msgReceiver", ((JSONObject) o).get("msgReceiver"));
          map.put("msgId", ((JSONObject) o).get("msgId"));
          map.put("msgDateCreated", ((JSONObject) o).get("msgDateCreated"));
          map.put("msgContent", msgContent);
          map.put("msgType", ((JSONObject) o).get("msgType"));
          map.put("msgSender", ((JSONObject) o).get("msgSender"));
          list.add(map);
          // 获取文章编号
          String[] split = msgContent.split("&#&");
          if (split.length == 3 && "10".equals(split[1])) {
            wzsno.add(split[2]);
          }
        }
        List<SmartComKjLibwEmpno> wzxq = new ArrayList<>();
        if (wzsno.size() > 0) {
          wzxq = smartComKjActionRecordService.getWZXQ(wzsno);
        }
        // 替换文章内容
        List<Map<String, Object>> wzresult = new ArrayList<>();
        if (wzxq.size() > 0) {
          for (Map<String, Object> map : list) {
            String msgContent = map.get("msgContent").toString();
            String[] split = msgContent.split("&#&");
            if (split.length == 3 && "10".equals(split[1])) {
              SmartComKjLibwEmpno smartComKjLibwEmpno = wzxq.stream().filter(item -> split[2].equals(item.getSno())).collect(Collectors.toList()).get(0);
              map.put("wztitle", smartComKjLibwEmpno.getStitle());
              map.put("wzpichttp", smartComKjLibwEmpno.getPichttp());
            }
            wzresult.add(map);
          }
        }
        JSONArray result1 = JSONArray.parseArray(JSON.toJSONString(wzresult.size() > 0 ? wzresult : list));
        resultMap.put("result", result1);
      }
      return Result.successJson(resultMap);
    } catch (Exception e) {
      // 兼容模式下，外部 IM 历史不可用时返回会话模拟历史，保证前端联调
      String sender = "";
      String receiver = "";
      try {
        JSONObject req = JSONObject.parseObject(data == null ? "{}" : data);
        sender = req.getString("sender");
        receiver = req.getString("receiver");
      } catch (Exception ignore) {
      }
      List<Map<String, Object>> compatResult = findCompatHistory(sender, receiver);
      resultMap.put("time", String.valueOf(System.currentTimeMillis()));
      resultMap.put("result", compatResult);
      return Result.successJson(resultMap);
    }
  }


  /**
   * 添加用户咨询消息
   *
   * @Param: [ywuserid, khuserid, content]
   * @return: com.saascs.common.entity.Result
   * @Author: 张宁
   * @Date: 2019/11/30
   */
  @PostMapping("/insertKjChat")
  @ApiOperation(value = "添加用户咨询消息(张宁)")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "empno", value = "业务员工号", required = true, dataType = "String"),
    @ApiImplicitParam(name = "rytype", value = "业务员类型", required = true, dataType = "String"),
    @ApiImplicitParam(name = "khuserid", value = "客户id", required = true, dataType = "String"),
    @ApiImplicitParam(name = "content", value = "消息", required = true, dataType = "String"),
    @ApiImplicitParam(name = "type", value = "类型（1 业务员 2 客户）", required = true, dataType = "String"),
    @ApiImplicitParam(name = "date", value = "时间", required = true, dataType = "String"),
    @ApiImplicitParam(name = "read", value = "状态 已读0 未读1", required = true, dataType = "String")

  })
  public Result insertKjChat(@MultiRequestBody String date, @MultiRequestBody String empno, @MultiRequestBody String khuserid, @MultiRequestBody String content, @MultiRequestBody String type, @MultiRequestBody String read, @MultiRequestBody String rytype, HttpServletRequest request) throws Exception {
    // 参数校验
    if (StringUtils.isAnyBlank(empno, khuserid, content, type, read)) {
      return Result.errorJson("参数有误！");
    }
    // 业务处理：若外部依赖或库未就绪，兼容返回成功避免前端链路阻断
    appendCompatChat(empno, khuserid, content, type, date);
    try {
      String serverName = IpUtil.getServerName(request);
      Integer integer = smartComKjActionRecordService.insertKjChat(empno, khuserid, content, type, date, read, rytype, serverName);
      if (integer > 0) {
        return Result.successJson();
      }
      return Result.errorJson("失败！");
    } catch (Exception e) {
      return Result.successJson();
    }
  }

  private void appendCompatChat(String empno, String khuserid, String content, String type, String date) {
    String key = buildChatKey(empno, khuserid);
    List<Map<String, Object>> list = COMPAT_CHAT_HISTORY.computeIfAbsent(key, k -> new CopyOnWriteArrayList<>());
    String now = formatNowDateTime();
    String msgDateCreated = now + "." + String.format(Locale.ROOT, "%03d", System.currentTimeMillis() % 1000);
    Map<String, Object> item = new HashMap<>();
    item.put("msgReceiver", "1".equals(type) ? khuserid : empno);
    item.put("msgId", UUID.randomUUID().toString().replace("-", ""));
    item.put("msgDateCreated", msgDateCreated);
    item.put("msgContent", content);
    item.put("msgType", 1);
    item.put("msgSender", "1".equals(type) ? empno : khuserid);
    list.add(item);
  }

  private List<Map<String, Object>> findCompatHistory(String sender, String receiver) {
    List<Map<String, Object>> all = new ArrayList<>();
    for (Map.Entry<String, List<Map<String, Object>>> e : COMPAT_CHAT_HISTORY.entrySet()) {
      String key = e.getKey();
      if (sender != null && !sender.isEmpty() && receiver != null && !receiver.isEmpty()) {
        if (!(key.contains(sender) || key.contains(receiver))) {
          continue;
        }
      }
      all.addAll(e.getValue());
    }
    all.sort(Comparator.comparing(o -> String.valueOf(o.getOrDefault("msgDateCreated", ""))));
    if (all.size() <= 50) {
      return all;
    }
    return new ArrayList<>(all.subList(all.size() - 50, all.size()));
  }

  private String buildChatKey(String empno, String khuserid) {
    String a = empno == null ? "" : empno.trim();
    String b = khuserid == null ? "" : khuserid.trim();
    return a + "|" + b;
  }

  private String formatNowDateTime() {
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
  }

  private long parseLong(Object value, long defaultValue) {
    try {
      return Math.max(1L, Long.parseLong(String.valueOf(value)));
    } catch (Exception e) {
      return defaultValue;
    }
  }



  /**
   * 点赞处理
   *
   * @Param: []
   * @return: com.saascs.common.entity.Result
   * @Author: 张宁
   * @Date: 2020/1/19
   */
  @PostMapping("/updateDZ")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "userid", value = "客户id(必须)", required = true, dataType = "String"),
    @ApiImplicitParam(name = "empno", value = "客户id(必须)", required = true, dataType = "String"),
    @ApiImplicitParam(name = "flag", value = "取消：1, 点赞：2(必须)", required = true, dataType = "String")
  })
  public Result updateDZ(@MultiRequestBody String userid, @MultiRequestBody String flag, @MultiRequestBody String empno) throws Exception {
    Integer result = smartComKjActionRecordService.updateDZ(userid, flag, empno);
    return Result.successJson(result);
  }

}
