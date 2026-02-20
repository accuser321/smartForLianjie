package com.nh.smart.controller.compat;

import com.nh.smart.entity.base.Result;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/core/abtComUserCard")
public class V2CompatUserCardController {

    @GetMapping("/selectKHbyEmpno")
    public Result selectKHbyEmpno(@RequestParam Map<String, String> params) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("js", sampleKhyjRows("晋升"));
        data.put("wc", sampleKhyjRows("维持"));
        data.put("rows", new ArrayList<>());
        data.put("total", 0);
        data.put("totalpage", 0);
        return Result.successJson(data);
    }

    @GetMapping("/getEmpnoPersonal")
    public Result getEmpnoPersonal(@RequestParam Map<String, String> params) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("empno", params.getOrDefault("empno", ""));
        data.put("empname", "演示顾问");
        data.put("phone", "13800000000");
        data.put("headimg", "");
        data.put("zyzstatus", "0");
        data.put("sfzstatus", "1");
        data.put("wjs", "0.00");
        data.put("jsz", "0.00");
        data.put("yjs", "0.00");
        data.put("jfnum", 0);
        data.put("yhnum", 0);
        return Result.successJson(data);
    }

    @PutMapping("/upEmpnoPwd")
    public Result upEmpnoPwd(@RequestBody Map<String, Object> data) {
        return Result.successJson();
    }

    @PutMapping("/upEmpnoPhone")
    public Result upEmpnoPhone(@RequestBody Map<String, Object> data) {
        return Result.successJson();
    }

    @GetMapping("/getPersonalData")
    public Result getPersonalData(@RequestParam Map<String, String> params) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("sex", "1");
        data.put("email", "");
        data.put("address", "");
        data.put("remark", "");
        return Result.successJson(data);
    }

    @PutMapping("/upMount")
    public Result upMount(@RequestBody Map<String, Object> data) {
        return Result.successJson();
    }

    @PostMapping("/getTXRecord")
    public Result getTXRecord(@RequestBody Map<String, Object> data) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("rows", sampleTxRows(3));
        result.put("blankmessage", rowKV("balance", "0.00", "bankname", "未绑定银行卡"));
        result.put("total", 3);
        result.put("totalpage", 1);
        return Result.successJson(result);
    }

    @PostMapping("/getTXRecords")
    public Result getTXRecords(@RequestBody Map<String, Object> data) {
        long page = parseLong(data.get("page"), 1L);
        long size = parseLong(data.get("size"), 10L);
        List<Map<String, Object>> all = sampleTxRows(12);
        long start = Math.max(0, (page - 1) * size);
        long end = Math.min(all.size(), start + size);
        List<Map<String, Object>> rows = start >= all.size()
            ? new ArrayList<>()
            : new ArrayList<>(all.subList((int) start, (int) end));
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("rows", rows);
        result.put("total", all.size());
        result.put("totalpage", (all.size() + size - 1) / size);
        return Result.successJson(result);
    }

    @PutMapping("/updatePersonalData")
    public Result updatePersonalData(@RequestBody Map<String, Object> data) {
        return Result.successJson();
    }

    @GetMapping("/getZYZMessage")
    public Result getZYZMessage(@RequestParam Map<String, String> params) {
        return Result.successJson(new LinkedHashMap<String, Object>());
    }

    @GetMapping("/isShowPromotionprice")
    public Result isShowPromotionprice(@RequestParam Map<String, String> params) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("flag", "0");
        return Result.successJson(data);
    }

    @GetMapping("/getSFZMessage")
    public Result getSFZMessage(@RequestParam Map<String, String> params) {
        return Result.successJson(new LinkedHashMap<String, Object>());
    }

    private List<Map<String, Object>> sampleTxRows(int count) {
        List<Map<String, Object>> rows = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            rows.add(rowKV(
                "id", i + 1,
                "mount", String.format(java.util.Locale.ROOT, "%.2f", 100 + i * 12.5),
                "txno", "TX20260220" + String.format(java.util.Locale.ROOT, "%03d", i + 1),
                "qrstatus", (i % 4 == 0 ? "2" : (i % 3 == 0 ? "1" : "0")),
                "remark", (i % 4 == 0 ? "银行卡信息不完整" : "")
            ));
        }
        return rows;
    }

    private List<Map<String, Object>> sampleKhyjRows(String salenamePrefix) {
        List<Map<String, Object>> rows = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            List<Map<String, Object>> zb = new ArrayList<>();
            zb.add(rowKV("name", "月度保费", "value", "0.00/5000"));
            zb.add(rowKV("name", "活动客户数", "value", "0/10"));
            rows.add(rowKV(
                "salename", salenamePrefix + "指标" + (i + 1),
                "value", "0",
                "zb", zb
            ));
        }
        return rows;
    }

    private long parseLong(Object value, long defaultValue) {
        try {
            return Math.max(1L, Long.parseLong(String.valueOf(value)));
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
