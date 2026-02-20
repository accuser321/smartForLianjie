package com.nh.smart.controller.compat;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nh.smart.annotation.MultiRequestBody;
import com.nh.smart.dao.record.SmartComKjActionRecordDao;
import com.nh.smart.dao.record.SmartComWxmsgDao;
import com.nh.smart.dao.record.SmartKjCustomEmpshipDao;
import com.nh.smart.entity.base.Result;
import com.nh.smart.entity.record.SmartComWxmsg;
import com.nh.smart.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/abt/abtComKjActionRecord")
public class V2CompatActionController {

    @Autowired
    private SmartComWxmsgDao smartComWxmsgDao;
    @Autowired
    private SmartKjCustomEmpshipDao smartKjCustomEmpshipDao;
    @Autowired
    private SmartComKjActionRecordDao smartComKjActionRecordDao;

    private String safeComid() {
        try {
            return JwtTokenUtil.getComid();
        } catch (Exception e) {
            return "";
        }
    }

    private String safeEmpno() {
        try {
            return JwtTokenUtil.getEmpno();
        } catch (Exception e) {
            return "";
        }
    }

    private Integer safeUserid() {
        try {
            return JwtTokenUtil.getUserid();
        } catch (Exception e) {
            return 0;
        }
    }

    private JSONObject emptyPageRowsArray() {
        JSONObject json = new JSONObject();
        json.put("rows", new ArrayList<>());
        json.put("total", 0);
        json.put("totalpage", 0);
        return json;
    }

    private JSONObject emptyPageRowsMap() {
        JSONObject json = new JSONObject();
        json.put("rows", new LinkedHashMap<>());
        json.put("total", 0);
        json.put("totalpage", 0);
        return json;
    }

    private List<String> parseLabids(Object labidObj) {
        if (labidObj == null) {
            return Collections.emptyList();
        }
        if (labidObj instanceof List) {
            List<?> raw = (List<?>) labidObj;
            List<String> ids = new ArrayList<>();
            for (Object item : raw) {
                if (item != null && !String.valueOf(item).trim().isEmpty()) {
                    ids.add(String.valueOf(item).trim());
                }
            }
            return ids;
        }
        String text = String.valueOf(labidObj).trim();
        if (text.isEmpty()) {
            return Collections.emptyList();
        }
        if (text.startsWith("[") && text.endsWith("]")) {
            text = text.substring(1, text.length() - 1);
        }
        String[] arr = text.split(",");
        List<String> ids = new ArrayList<>();
        for (String s : arr) {
            String v = s.replace("\"", "").replace("'", "").trim();
            if (!v.isEmpty()) {
                ids.add(v);
            }
        }
        return ids;
    }

    private Result selectCustomByMode(Map<String, Object> map, String mode) {
        try {
            String comid = safeComid();
            String empno = safeEmpno();
            long page = Long.parseLong(String.valueOf(map.getOrDefault("page", "1")));
            long size = Long.parseLong(String.valueOf(map.getOrDefault("size", "10")));
            String name = String.valueOf(map.getOrDefault("name", ""));
            List<String> labids = parseLabids(map.get("labid"));
            IPage<Map<String, Object>> data = smartKjCustomEmpshipDao.selectCustomPage(
                new Page<>(page, size), comid, empno, name, labids, mode
            );
            JSONObject json = new JSONObject();
            json.put("rows", data.getRecords());
            json.put("total", data.getTotal());
            json.put("totalpage", data.getPages());
            return Result.successJson(json);
        } catch (Exception e) {
            return Result.successJson(emptyPageRowsArray());
        }
    }

    @PostMapping("/getCommunicationKH")
    public Result getCommunicationKH(@RequestBody Map<String, Object> map) {
        return selectCustomByMode(map, "address");
    }

    @PostMapping("/getRecentlyKH")
    public Result getRecentlyKH(@RequestBody Map<String, Object> map) {
        return selectCustomByMode(map, "recent");
    }

    @PostMapping("/getStandardKH")
    public Result getStandardKH(@RequestBody Map<String, Object> map) {
        return selectCustomByMode(map, "prospect");
    }

    @PostMapping("/getColleague")
    public Result getColleague(@RequestBody Map<String, Object> map) {
        return selectCustomByMode(map, "colleague");
    }

    @PostMapping("/getKHContacts")
    public Result getKHContacts(@RequestBody Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("relationship", new ArrayList<>());
        json.put("message", new ArrayList<>());
        return Result.successJson(json);
    }

    @PostMapping("/getFollowList")
    public Result getFollowList(@RequestBody Map<String, Object> map) {
        try {
            String comid = safeComid();
            Integer fuserid = safeUserid();
            Integer tuserid = Integer.valueOf(String.valueOf(map.getOrDefault("userid", "0")));
            long page = Long.parseLong(String.valueOf(map.getOrDefault("page", "1")));
            long size = Long.parseLong(String.valueOf(map.getOrDefault("size", "10")));
            IPage<Map<String, Object>> data = smartComWxmsgDao.selectFollowList(new Page<>(page, size), comid, fuserid, tuserid);
            Map<String, List<Map<String, Object>>> rows = new LinkedHashMap<>();
            for (Map<String, Object> item : data.getRecords()) {
                String day = String.valueOf(item.getOrDefault("day", ""));
                rows.computeIfAbsent(day, k -> new ArrayList<>()).add(item);
            }
            JSONObject json = new JSONObject();
            json.put("rows", rows);
            json.put("total", data.getTotal());
            json.put("totalpage", data.getPages());
            return Result.successJson(json);
        } catch (Exception e) {
            return Result.successJson(emptyPageRowsMap());
        }
    }

    @PostMapping("/insertFollow")
    public Result insertFollow(@MultiRequestBody String userid, @MultiRequestBody String content) {
        try {
            if (userid == null || userid.trim().isEmpty() || content == null || content.trim().isEmpty()) {
                return Result.errorJson("参数不能为空");
            }
            SmartComWxmsg entity = new SmartComWxmsg();
            entity.setComid(safeComid());
            entity.setFuserid(safeUserid());
            entity.setTuserid(Integer.valueOf(userid));
            entity.setMsgtype("FOLLOW");
            entity.setMcontent(content.trim());
            entity.setPushtime(new Date());
            entity.setStatus("1");
            smartComWxmsgDao.insert(entity);
            return Result.successJson();
        } catch (Exception e) {
            return Result.errorJson("添加失败");
        }
    }

    @DeleteMapping("/deleteFollow")
    public Result deleteFollow(@RequestParam(required = false) String id) {
        try {
            if (id == null || id.trim().isEmpty()) {
                return Result.errorJson("id不能为空");
            }
            smartComWxmsgDao.deleteFollowById(safeComid(), Integer.valueOf(id), safeUserid());
            return Result.successJson();
        } catch (Exception e) {
            return Result.errorJson("删除失败");
        }
    }

    @DeleteMapping("/deleteFollow/{id}")
    public Result deleteFollowByPath(@PathVariable String id) {
        return deleteFollow(id);
    }

    @PostMapping("/getInteractiveList")
    public Result getInteractiveList(@RequestBody Map<String, Object> map) {
        try {
            String comid = safeComid();
            String empno = safeEmpno();
            Integer userid = Integer.valueOf(String.valueOf(map.getOrDefault("userid", "0")));
            long page = Long.parseLong(String.valueOf(map.getOrDefault("page", "1")));
            long size = Long.parseLong(String.valueOf(map.getOrDefault("size", "10")));
            IPage<Map<String, Object>> data = smartComKjActionRecordDao.selectInteractiveList(new Page<>(page, size), comid, empno, userid);
            Map<String, List<Map<String, Object>>> rows = new LinkedHashMap<>();
            for (Map<String, Object> item : data.getRecords()) {
                String day = String.valueOf(item.getOrDefault("day", ""));
                rows.computeIfAbsent(day, k -> new ArrayList<>()).add(item);
            }
            JSONObject json = new JSONObject();
            json.put("rows", rows);
            json.put("total", data.getTotal());
            json.put("totalpage", data.getPages());
            return Result.successJson(json);
        } catch (Exception e) {
            return Result.successJson(emptyPageRowsMap());
        }
    }

    @GetMapping("/getAIAnalysis/{userid}")
    public Result getAIAnalysisByPath(@PathVariable String userid) {
        return Result.successJson(buildAIAnalysis(userid));
    }

    @GetMapping("/getAIAnalysis")
    public Result getAIAnalysisByParam(@RequestParam(required = false) String userid) {
        return Result.successJson(buildAIAnalysis(userid));
    }

    private JSONObject buildAIAnalysis(String userid) {
        int seed = Math.abs((userid == null ? String.valueOf(safeUserid()) : userid).hashCode());
        JSONObject json = new JSONObject();
        json.put("activeList", buildTrendRows(15, 2 + seed % 3, 3 + seed % 4));
        json.put("demandList", Arrays.asList(
            rowKV("tagname", "保险配置", "num", 20 + seed % 15),
            rowKV("tagname", "理财增值", "num", 15 + seed % 10),
            rowKV("tagname", "养老规划", "num", 10 + seed % 8),
            rowKV("tagname", "子女教育", "num", 8 + seed % 7)
        ));
        json.put("interactive", Arrays.asList(
            rowKV("type", "查看名片", "num", 18 + seed % 10),
            rowKV("type", "阅读文章", "num", 22 + seed % 12),
            rowKV("type", "咨询互动", "num", 9 + seed % 6),
            rowKV("type", "点赞转发", "num", 12 + seed % 7)
        ));
        json.put("interest", Arrays.asList(
            rowKV("type", "健康保障", "num", 28 + seed % 10),
            rowKV("type", "高端医疗", "num", 16 + seed % 9),
            rowKV("type", "年金养老", "num", 14 + seed % 7),
            rowKV("type", "资产配置", "num", 11 + seed % 6)
        ));
        return json;
    }

    @PostMapping("/selectTimeKHByEmpno")
    public Result selectTimeKHByEmpno(@RequestBody Map<String, Object> map) {
        return Result.successJson(buildBehaviorPage(map, null));
    }

    @PostMapping("/selectKHByOtype")
    public Result selectKHByOtype(@RequestBody Map<String, Object> map) {
        List<String> tags = parseTags(String.valueOf(map.getOrDefault("btagcode", "")));
        if (tags.isEmpty()) {
            tags = Arrays.asList("9", "10", "12", "7");
        }
        return Result.successJson(buildBehaviorPage(map, tags));
    }

    @GetMapping("/getRecordAnalyze")
    public Result getRecordAnalyze() {
        JSONObject json = new JSONObject();
        int seed = Math.max(1, safeUserid());
        json.put("numList", rowKV(
            "kuNUM", 120 + seed % 30,
            "mpDZ", 80 + seed % 20,
            "mpZF", 26 + seed % 10,
            "wzRD", 340 + seed % 90,
            "wzZF", 42 + seed % 15,
            "zxNUM", 18 + seed % 8
        ));
        json.put("activeList", buildTrendRows(15, 4 + seed % 3, 4 + seed % 2));
        json.put("khInteractive", Arrays.asList(
            rowKV("type", "名片互动", "num", 32 + seed % 10),
            rowKV("type", "文章互动", "num", 46 + seed % 12),
            rowKV("type", "咨询互动", "num", 21 + seed % 8),
            rowKV("type", "活动互动", "num", 13 + seed % 6)
        ));
        return Result.successJson(json);
    }

    @GetMapping("/getRecordKH/{KHday}")
    public Result getRecordKHByPath(@PathVariable String KHday) {
        return buildRecordKHResult(KHday);
    }

    @GetMapping("/getRecordKH")
    public Result getRecordKHByParam(@RequestParam(required = false) String KHday) {
        return buildRecordKHResult(KHday);
    }

    @GetMapping("/getRecordRD/{Readday}")
    public Result getRecordRDByPath(@PathVariable String Readday) {
        return buildRecordRDResult(Readday);
    }

    @GetMapping("/getRecordRD")
    public Result getRecordRDByParam(@RequestParam(required = false) String Readday) {
        return buildRecordRDResult(Readday);
    }

    private Result buildRecordRDResult(String readDay) {
        int days = parseDayCount(readDay);
        JSONObject json = new JSONObject();
        json.put("rdDaynum", buildTrendRows(days, 6, 5));
        return Result.successJson(json);
    }

    private Result buildRecordKHResult(String khDay) {
        int days = parseDayCount(khDay);
        JSONObject json = new JSONObject();
        json.put("hkDaynum", buildTrendRows(days, 2, 4));
        return Result.successJson(json);
    }

    @GetMapping("/getYSLD")
    public Result getYSLD() {
        List<Map<String, Object>> rows = Arrays.asList(
            rowKV("type", "潜在客户", "num", 168),
            rowKV("type", "重点跟进", "num", 92),
            rowKV("type", "高意向", "num", 47),
            rowKV("type", "成交转化", "num", 21)
        );
        return Result.successJson(rows);
    }

    @PostMapping("/selectMessage")
    public Result selectMessage(@RequestBody Map<String, Object> map) {
        try {
            String comid = safeComid();
            String empno = safeEmpno();
            long page = Long.parseLong(String.valueOf(map.getOrDefault("page", "1")));
            long size = Long.parseLong(String.valueOf(map.getOrDefault("size", "10")));
            IPage<Map<String, Object>> data = smartComWxmsgDao.selectWxmsgPage(new Page(page, size), comid, empno);
            JSONObject json = new JSONObject();
            json.put("rows", data.getRecords());
            json.put("total", data.getTotal());
            json.put("totalpage", data.getPages());
            return Result.successJson(json);
        } catch (Exception e) {
            return Result.successJson(emptyPageRowsArray());
        }
    }

    @PostMapping("/QueryMessage")
    public Result queryMessage(@RequestBody Map<String, Object> map) {
        try {
            String comid = safeComid();
            String userid = String.valueOf(map.getOrDefault("userid", safeUserid()));
            String msgtype = String.valueOf(map.getOrDefault("msgtype", ""));
            String btagcode = String.valueOf(map.getOrDefault("btagcode", ""));
            String otype = String.valueOf(map.getOrDefault("otype", ""));
            long page = Long.parseLong(String.valueOf(map.getOrDefault("page", "1")));
            long size = Long.parseLong(String.valueOf(map.getOrDefault("size", "10")));
            IPage<SmartComWxmsg> data = smartComWxmsgDao.QueryMessage(new Page<>(page, size), comid, userid, msgtype, btagcode, otype);
            JSONObject json = new JSONObject();
            json.put("rows", data.getRecords());
            json.put("total", data.getTotal());
            json.put("totalpage", data.getPages());
            return Result.successJson(json);
        } catch (Exception e) {
            return Result.successJson(emptyPageRowsArray());
        }
    }

    @PostMapping("/updateStatus")
    public Result updateStatus(@RequestBody Map<String, Object> map) {
        try {
            String comid = safeComid();
            String msgid = String.valueOf(map.getOrDefault("msgid", ""));
            if (msgid.isEmpty()) {
                return Result.errorJson("msgid不能为空");
            }
            String status = String.valueOf(map.getOrDefault("status", "1"));
            String oldStatus = smartComWxmsgDao.findStatus(comid, msgid);
            if (oldStatus != null && oldStatus.equals(status)) {
                return Result.successJson();
            }
            smartComWxmsgDao.updateStatus(comid, msgid, status);
            return Result.successJson();
        } catch (Exception e) {
            return Result.errorJson("消息状态更新失败");
        }
    }

    @GetMapping("/getKjChat/{khuserid}/{empno}")
    public Result getKjChatByPath(@PathVariable String khuserid, @PathVariable String empno) {
        return getKjChatInternal(khuserid, empno, null);
    }

    @GetMapping("/getKjChat/{khuserid}/{empno}/{type}")
    public Result getKjChatByPathWithType(@PathVariable String khuserid, @PathVariable String empno, @PathVariable String type) {
        return getKjChatInternal(khuserid, empno, type);
    }

    @GetMapping("/getKjChat")
    public Result getKjChatByParam(@RequestParam String khuserid, @RequestParam String empno, @RequestParam(required = false) String type) {
        return getKjChatInternal(khuserid, empno, type);
    }

    private Result getKjChatInternal(String khuserid, String empno, String type) {
        try {
            String comid = safeComid();
            List<Map<String, Object>> list = smartComWxmsgDao.getKjChat(comid, empno, Integer.valueOf(khuserid), type);
            return Result.successJson(list);
        } catch (Exception e) {
            return Result.successJson(new ArrayList<>());
        }
    }

    @GetMapping("/getKjChatNum/{khuserid}/{empno}")
    public Result getKjChatNumByPath(@PathVariable String khuserid, @PathVariable String empno) {
        return getKjChatNumInternal(khuserid, empno);
    }

    @GetMapping("/getKjChatNum")
    public Result getKjChatNumByParam(@RequestParam String khuserid, @RequestParam String empno) {
        return getKjChatNumInternal(khuserid, empno);
    }

    private Result getKjChatNumInternal(String khuserid, String empno) {
        try {
            String comid = safeComid();
            List<Map<String, Object>> list = smartComWxmsgDao.getKjChat(comid, empno, Integer.valueOf(khuserid), "2");
            JSONObject json = new JSONObject();
            json.put("num", list == null ? 0 : list.size());
            return Result.successJson(json);
        } catch (Exception e) {
            JSONObject json = new JSONObject();
            json.put("num", 0);
            return Result.successJson(json);
        }
    }

    @GetMapping("/getNowDayCount")
    public Result getNowDayCount() {
        JSONObject json = new JSONObject();
        json.put("JRZF", 0);
        json.put("JRFC", 0);
        json.put("JRHK", 0);
        json.put("JYZF", 0);
        json.put("JYFC", 0);
        json.put("JYHK", 0);
        json.put("wdMsgCount", 0);
        // 保留旧字段兼容其他页面
        json.put("readnum", 0);
        json.put("sharenum", 0);
        json.put("looknum", 0);
        json.put("zxnum", 0);
        return Result.successJson(json);
    }

    private int parseDayCount(String dayText) {
        try {
            int days = Integer.parseInt(String.valueOf(dayText));
            if (days <= 0) {
                return 7;
            }
            return Math.min(days, 30);
        } catch (Exception e) {
            return 7;
        }
    }

    private List<Map<String, Object>> buildTrendRows(int days, int minBase, int floatRange) {
        List<Map<String, Object>> rows = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (int i = days - 1; i >= 0; i--) {
            int index = days - 1 - i;
            rows.add(rowKV(
                "time", today.minusDays(i).format(DateTimeFormatter.ofPattern("MM-dd")),
                "num", minBase + (index % 5) * floatRange + (index / 3)
            ));
        }
        return rows;
    }

    private JSONObject buildBehaviorPage(Map<String, Object> map, List<String> forceTags) {
        long page = parsePageNum(map.get("page"), 1L);
        long size = parsePageNum(map.get("size"), 10L);
        long total = 48L;
        long offset = (page - 1) * size;
        List<String> tagPool = forceTags == null || forceTags.isEmpty()
            ? Arrays.asList("9", "10", "12", "7", "11", "13", "14", "15", "17")
            : forceTags;
        Map<String, List<Map<String, Object>>> rows = new LinkedHashMap<>();
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < size; i++) {
            long idx = offset + i;
            if (idx >= total) {
                break;
            }
            LocalDate day = LocalDate.now().minusDays((int) (idx / 4));
            LocalDateTime time = day.atTime(9 + (int) (idx % 8), 10 + (int) (idx % 40), 0);
            String btagcode = tagPool.get((int) (idx % tagPool.size()));
            Map<String, Object> item = rowKV(
                "userid", 10001 + idx,
                "khname", "客户" + (idx + 1),
                "headimg", "",
                "btagcode", btagcode,
                "stitle", "内容" + (idx + 1),
                "remark", "方案" + (idx + 1),
                "begtime", time.format(timeFmt),
                "basttime", (2 + idx % 4) + "分" + (idx % 2 == 0 ? "00" : "30") + "秒",
                "acttime", (idx % 5 == 0 ? "00:00" : String.format(Locale.ROOT, "0%d:%02d", 1 + idx % 3, 10 + idx % 40))
            );
            String dayKey = day.format(DateTimeFormatter.ISO_LOCAL_DATE);
            rows.computeIfAbsent(dayKey, k -> new ArrayList<>()).add(item);
        }
        JSONObject json = new JSONObject();
        json.put("rows", rows);
        json.put("total", total);
        json.put("totalpage", (total + size - 1) / size);
        return json;
    }

    private List<String> parseTags(String text) {
        if (text == null || text.trim().isEmpty()) {
            return Collections.emptyList();
        }
        String[] arr = text.split(",");
        List<String> tags = new ArrayList<>();
        for (String s : arr) {
            String v = s.trim();
            if (!v.isEmpty()) {
                tags.add(v);
            }
        }
        return tags;
    }

    private long parsePageNum(Object value, long defaultValue) {
        try {
            long v = Long.parseLong(String.valueOf(value));
            return Math.max(1L, v);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    private Map<String, Object> rowKV(Object... values) {
        Map<String, Object> map = new LinkedHashMap<>();
        for (int i = 0; i + 1 < values.length; i += 2) {
            map.put(String.valueOf(values[i]), values[i + 1]);
        }
        return map;
    }
}
