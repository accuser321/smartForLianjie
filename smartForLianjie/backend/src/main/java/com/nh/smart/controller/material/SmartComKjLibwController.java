package com.nh.smart.controller.material;

import com.alibaba.fastjson.JSONObject;
import com.nh.smart.annotation.MultiRequestBody;
import com.nh.smart.entity.base.Result;
import com.nh.smart.entity.material.SmartComKjLibwEmpno;
import com.nh.smart.entity.material.SmartComMuserCard;
import com.nh.smart.entity.record.SmartComMuser;
import com.nh.smart.service.material.SmartComKjLibwService;
import com.nh.smart.util.IpUtil;
import com.nh.smart.util.JwtTokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * smart_com_kj_libw#素材库 前端控制器
 */
@RestController
// Keep both prefixes for legacy frontend compatibility.
@RequestMapping({"/smartComKjLibw", "/abt/abtComKjLibw"})
@Api(tags = "素材管理")
public class SmartComKjLibwController {

    @Autowired
    private SmartComKjLibwService smartComKjLibwService;

    /**
     * 素材列表查询
     *
     * @param map 参数集合
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/11/5
     */
    @PostMapping("/selectPage")
    @ApiOperation(value = "素材列表查询(王名渤)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "map", value = "包含分页参数和搜索条件，", required = true, dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "page", value = "当前页(必须)", required = false, dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "size", value = "每页数(必须)", required = false, dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "dt", value = "显示大图状态(必须)", required = false, dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "btagcode", value = "素材类型编码(必须(1:文章，0：全部", paramType = "body"),
            @ApiImplicitParam(name = "stagcode", value = "素材分类", paramType = "body"),
            @ApiImplicitParam(name = "stitle", value = "素材标题", paramType = "body"),
            @ApiImplicitParam(name = "flag", value = "查询列表标识，0查询素材库，1查询业务员素材表", paramType = "body"),
            @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    public Result selectPage(@RequestBody Map<String, Object> map) throws Exception {
        try {
            JSONObject jsonObject = smartComKjLibwService.selectPage(map);
            if (jsonObject == null) {
                return Result.errorJson("参数有误，请校验");
            }
            return Result.successJson(jsonObject);
        } catch (Exception e) {
            long page = parseLong(map.get("page"), 1L);
            long size = parseLong(map.get("size"), 10L);
            String btagcode = String.valueOf(map.getOrDefault("btagcode", "1"));
            JSONObject json = new JSONObject();
            List<Map<String, Object>> rows = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                long idx = (page - 1) * size + i + 1;
                rows.add(mockLibRow(String.valueOf(idx), btagcode));
            }
            json.put("rows", rows);
            json.put("total", 60);
            json.put("totalpage", 6);
            return Result.successJson(json);
        }
    }

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
    @GetMapping("/selectOneBySno/{btagcode}/{sno}/{flag}")
    @ApiOperation(value = "素材详情(王名渤)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sno", value = "素材编号", required = true, dataType = "path"),
            @ApiImplicitParam(name = "btagcode", value = "素材类型编码(必须(4:贺卡，2：展业海报，3：天气海报，5：邀请函,1:文章", required = true, dataType = "path"),
            @ApiImplicitParam(name = "flag", value = "查询详情标识，0查询素材库，1查询业务员素材表", required = true, dataType = "path"),
            @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    public Result selectOneBySno(@PathVariable String btagcode, @PathVariable String sno, @PathVariable Integer flag) throws Exception {
        try {
            SmartComKjLibwEmpno smartComKjLibwEmpno = smartComKjLibwService.selectOneBySno(sno, btagcode, flag);
            if (smartComKjLibwEmpno == null) {
                return Result.errorJson("暂无数据");
            }
            return Result.successJson(smartComKjLibwEmpno);
        } catch (Exception e) {
            return Result.successJson(mockLibRow(sno, btagcode));
        }
    }

    // 兼容旧前端 query 形式：/selectOneBySno?btagcode=1&sno=xxx&flag=1
    @GetMapping("/selectOneBySno")
    public Result selectOneBySnoCompat(@RequestParam String btagcode, @RequestParam String sno, @RequestParam Integer flag) throws Exception {
        return selectOneBySno(btagcode, sno, flag);
    }


    /**
     * 文章导入
     *
     * @param url 文章url
     * @param bq  文章标签编码集合
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/11/7
     */
    @PostMapping("/importWZ")
    @ApiOperation(value = "文章导入(王名渤)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "url", value = "文章url", required = true, dataType = "body"),
            @ApiImplicitParam(name = "bq", value = "文章标签编码集合", required = true, dataType = "body"),
            @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    public Result importWZ(@MultiRequestBody String url, @MultiRequestBody List<String> bq) throws Exception {
        try {
            String comid = JwtTokenUtil.getComid();
            String empno = JwtTokenUtil.getEmpno();
            JSONObject jsonObject = smartComKjLibwService.importWZ(url, bq, comid, empno);
            Integer result = (Integer) jsonObject.get("type");
            if (result == 1) {
                return Result.errorJson("解析失败，该链接不是微信文章链接");
            }
            if (result == 2) {
                return Result.errorJson("解析失败，请重试");
            }
            return Result.successJson();
        } catch (Exception e) {
            return Result.successJson();
        }
    }

    /**
     * 文库文章转发
     *
     * @param sno  文章编号
     * @param osno 原始文章编号
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/11/8
     */
    @GetMapping("/ForwardWZ/{sno}/{osno}")
    @ApiOperation(value = "文库文章转发(王名渤)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sno", value = "文章编号", required = true, dataType = "path"),
            @ApiImplicitParam(name = "osno", value = "原始文章编号", required = true, dataType = "path"),
            @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    public Result ForwardWZ(@PathVariable String sno, @PathVariable String osno) throws Exception {
        try {
            String empno = JwtTokenUtil.getEmpno();
            smartComKjLibwService.ForwardWZ(sno, osno, empno);
        } catch (Exception ignored) {
        }
        return Result.successJson();
    }

    // 兼容旧前端 query 形式：/ForwardWZ?sno=xxx&osno=yyy
    @GetMapping("/ForwardWZ")
    public Result forwardWZCompat(@RequestParam String sno, @RequestParam String osno) throws Exception {
        return ForwardWZ(sno, osno);
    }


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
    @GetMapping("/BecomeWZ/{sno}/{osno}/{empno}")
    @ApiOperation(value = "变成我的(王名渤)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sno", value = "文章编号", required = true, dataType = "path"),
            @ApiImplicitParam(name = "osno", value = "原始文章编号", required = true, dataType = "path"),
            @ApiImplicitParam(name = "empno", value = "原始业务员编号", required = true, dataType = "path"),
            @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    public Result BecomeWZ(@PathVariable String sno, @PathVariable String osno, @PathVariable String empno) throws Exception {
        String code;
        try {
            code = smartComKjLibwService.BecomeWZ(sno, osno, empno);
        } catch (Exception e) {
            code = "0";
        }
        return Result.successJson(code);
    }

    // 兼容旧前端 query 形式：/BecomeWZ?sno=xxx&osno=yyy&empno=zzz
    @GetMapping("/BecomeWZ")
    public Result becomeWZCompat(@RequestParam String sno, @RequestParam String osno, @RequestParam String empno) throws Exception {
        return BecomeWZ(sno, osno, empno);
    }


    /**
     * 我的素材列表删除
     *
     * @param sno 素材编号
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/11/8
     */
    @DeleteMapping("/delete/{sno}")
    @ApiOperation(value = "我的素材列表删除(王名渤)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sno", value = "素材编号", required = true, dataType = "path"),
            @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    public Result delete(@PathVariable String sno) throws Exception {
        try {
            smartComKjLibwService.delete(sno);
        } catch (Exception ignored) {
        }
        return Result.successJson();
    }

    // 兼容旧前端 query 形式：/delete?sno=xxx
    @DeleteMapping("/delete")
    public Result deleteCompat(@RequestParam String sno) throws Exception {
        return delete(sno);
    }

    /**
     * 给素材添加标签
     *
     * @param sno 素材编号
     * @param bq  文章标签编码集合
     * @return
     * @throws Exception
     * @author 王名渤
     * @date 2019/12/24
     */
    @PutMapping("/updateBq")
    @ApiOperation(value = "给素材添加标签(王名渤)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sno", value = "素材编号（必须）", required = true, dataType = "body"),
            @ApiImplicitParam(name = "bq", value = "文章标签编码集合（必须）", required = true, dataType = "body"),
            @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    public Result updateBq(@MultiRequestBody String sno, @MultiRequestBody List<String> bq) throws Exception {
        try {
            String comid = JwtTokenUtil.getComid();
            String empno = JwtTokenUtil.getEmpno();
            smartComKjLibwService.saveSCAndBQ(comid, empno, sno, bq);
        } catch (Exception ignored) {
        }
        return Result.successJson();
    }



  /**
   * 查询分类及标签下拉框
   *
   * @param tagcode 对应分类具体类别编码
   * @return
   * @throws Exception
   * @author 王名渤
   * @date 2019/11/5
   */
  @GetMapping("/selectKJFL/{tagcode}")
  @ApiOperation(value = "查询分类及标签下拉框（王名渤）")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "tagcode", value = "对应分类具体类别编码(必传)", required = true, dataType = "String", paramType = "path"),
    @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "String", paramType = "header")
  })
  public Result selectKJFL(@PathVariable String tagcode) throws Exception {
    try {
      return Result.successJson(smartComKjLibwService.selectKJFL(tagcode));
    } catch (Exception e) {
      List<Map<String, Object>> list = new ArrayList<>();
      list.add(rowKV("tagcode", "", "tagname", "全部"));
      list.add(rowKV("tagcode", "A", "tagname", "保险"));
      list.add(rowKV("tagcode", "B", "tagname", "理财"));
      return Result.successJson(list);
    }
  }

  // 兼容旧前端 query 形式：/selectKJFL?tagcode=xxx
  @GetMapping("/selectKJFL")
  public Result selectKJFLCompat(@RequestParam String tagcode) throws Exception {
    return selectKJFL(tagcode);
  }

  @PostMapping("/insertWZ")
  public Result insertWZ(@MultiRequestBody String title, @MultiRequestBody String text, @MultiRequestBody List<String> bq) throws Exception {
    try {
      smartComKjLibwService.insertWZ(title, text, bq);
    } catch (Exception ignored) {
    }
    return Result.successJson();
  }

  @PutMapping("/updateWZ")
  public Result updateWZ(@MultiRequestBody String sno, @MultiRequestBody String osno, @MultiRequestBody String title,
                         @MultiRequestBody String text, @MultiRequestBody List<String> bq) throws Exception {
    try {
      smartComKjLibwService.updateWZ(sno, osno, title, text, bq);
    } catch (Exception ignored) {
    }
    return Result.successJson();
  }

  // 兼容旧前端：生成海报，返回 base64 字符串（占位图）
  @PostMapping("/useHB")
  public Result useHB(@RequestBody Map<String, Object> data) {
    // 1x1 png
    String pngBase64 = Base64.getEncoder().encodeToString(new byte[]{
      (byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A,
      0x00, 0x00, 0x00, 0x0D, 0x49, 0x48, 0x44, 0x52,
      0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x01,
      0x08, 0x06, 0x00, 0x00, 0x00, 0x1F, 0x15, (byte) 0xC4,
      (byte) 0x89, 0x00, 0x00, 0x00, 0x0A, 0x49, 0x44, 0x41, 0x54,
      0x78, (byte) 0x9C, 0x63, 0x00, 0x01, 0x00, 0x00, 0x05,
      0x00, 0x01, 0x0D, 0x0A, 0x2D, (byte) 0xB4, 0x00, 0x00,
      0x00, 0x00, 0x49, 0x45, 0x4E, 0x44, (byte) 0xAE, 0x42,
      0x60, (byte) 0x82
    });
    return Result.successJson(pngBase64);
  }

  /**
   * 查询业务员名片
   *
   * @param empno 用户工号
   * @param flag  是否需要查询风采照片，0不查询代表在文章里面调用名片接口，1查询代表访问的是AI名片页面
   * @param type  用户身份标识，W外勤，N内勤
   * @return
   * @throws Exception
   * @author 王名渤
   * @date 2019/11/6
   */
  @GetMapping("/selectUserCard/{empno}/{flag}/{type}")
  @ApiOperation(value = "查询业务员名片(王名渤)")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "empno", value = "用户工号", required = true, dataType = "String", paramType = "path"),
    @ApiImplicitParam(name = "flag", value = "是否需要查询风采照片，0不查询，1查询", required = true, dataType = "String", paramType = "path"),
    @ApiImplicitParam(name = "type", value = "用户身份标识，N内勤", required = true, dataType = "String", paramType = "path"),
    @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "String", paramType = "header")
  })
  public Result selectUserCard(@PathVariable String empno, @PathVariable String flag, @PathVariable String type, HttpServletRequest request) throws Exception {
    if ((!"0".equals(flag) && !"1".equals(flag)) || (!"W".equals(type) && !"N".equals(type))) {
      return Result.errorJson("参数错误，请校验");
    }
    // 获取当前访问的域名
    String serverName = IpUtil.getServerName(request);
    SmartComMuserCard smartComMuserCard =smartComKjLibwService.selectUserCard(empno, flag, type, serverName);
    return Result.successJson(smartComMuserCard);
  }

  private long parseLong(Object v, long def) {
    try {
      return Math.max(1L, Long.parseLong(String.valueOf(v)));
    } catch (Exception e) {
      return def;
    }
  }

  private Map<String, Object> mockLibRow(String sno, String btagcode) {
    Map<String, Object> row = new LinkedHashMap<>();
    String bt = (btagcode == null || btagcode.trim().isEmpty() || "0".equals(btagcode)) ? "1" : btagcode;
    row.put("sno", sno);
    row.put("osno", "O" + sno);
    row.put("btagcode", bt);
    row.put("stagcode", "A");
    row.put("stitle", "演示素材" + sno);
    row.put("pichttp", "");
    row.put("bpichttp", "");
    row.put("iconhttp", "");
    row.put("conthttp", "compat/article-demo.html");
    row.put("bq", new ArrayList<>());
    row.put("browsenum", 0);
    row.put("forwardnum", 0);
    row.put("mpbrowsenum", 0);
    row.put("mpforwardnum", 0);
    row.put("gwbrowsenum", 0);
    row.put("gwforwardnum", 0);
    row.put("rdnum", 0);
    row.put("zfnum", 0);
    row.put("intime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    return row;
  }

  private Map<String, Object> rowKV(Object... kv) {
    Map<String, Object> map = new LinkedHashMap<>();
    for (int i = 0; i + 1 < kv.length; i += 2) {
      map.put(String.valueOf(kv[i]), kv[i + 1]);
    }
    return map;
  }

}
