package com.nh.smart.controller.compat;

import com.alibaba.fastjson.JSONObject;
import com.nh.smart.entity.base.Result;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/core/abtComAlertmsg")
public class V2CompatAlertmsgController {

    @PostMapping("/selectByPage")
    public Result selectByPage(@RequestBody Map<String, Object> data) {
        long currentPage = parseLong(data.get("currentPage"), 1L);
        long pageSize = parseLong(data.get("pageSize"), 5L);
        String type = String.valueOf(data.getOrDefault("type", ""));
        String name = String.valueOf(data.getOrDefault("name", "")).trim();
        List<Map<String, Object>> all = buildMockRows();
        List<Map<String, Object>> filtered = new ArrayList<>();
        for (Map<String, Object> item : all) {
            boolean typeOk = type.isEmpty() || type.equals(String.valueOf(item.get("alerttype")));
            boolean nameOk = name.isEmpty() || String.valueOf(item.get("pname")).contains(name);
            if (typeOk && nameOk) {
                filtered.add(item);
            }
        }
        long start = Math.max(0, (currentPage - 1) * pageSize);
        long end = Math.min(filtered.size(), start + pageSize);
        List<Map<String, Object>> pageRows = start >= filtered.size()
            ? new ArrayList<>()
            : new ArrayList<>(filtered.subList((int) start, (int) end));
        JSONObject json = new JSONObject();
        json.put("rows", pageRows);
        json.put("total", filtered.size());
        json.put("totalpage", pageSize == 0 ? 0 : (filtered.size() + pageSize - 1) / pageSize);
        return Result.successJson(json);
    }

    @PostMapping("/checkDetails")
    public Result checkDetails(@RequestBody Map<String, Object> data) {
        String type = String.valueOf(data.getOrDefault("type", "1"));
        JSONObject json = new JSONObject();
        json.put("dayNumber", "5");
        json.put("age", 36);
        json.put("riskconList", rowKV(
            "basename", "平安保险",
            "policyno", String.valueOf(data.getOrDefault("policyno", "PA20260220001")),
            "appdate", LocalDate.now().minusYears(2).format(DateTimeFormatter.ISO_LOCAL_DATE),
            "sxdate", LocalDate.now().minusYears(2).plusDays(1).format(DateTimeFormatter.ISO_LOCAL_DATE),
            "cbdate", LocalDate.now().minusYears(2).plusDays(3).format(DateTimeFormatter.ISO_LOCAL_DATE),
            "polistname", "有效"
        ));
        Map<String, Object> classlist = new LinkedHashMap<>();
        if ("3".equals(type) || "4".equals(type)) {
            classlist.put("0", rowKV("classname", "重疾险", "yearnum", "20年", "mount", "3680"));
            classlist.put("1", rowKV("classname", "医疗险", "yearnum", "1年", "mount", "1260"));
        }
        json.put("classlist", classlist);
        return Result.successJson(json);
    }

    // basic 模块也会调用该接口
    @GetMapping("/statistics")
    public Result statistics() {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("num", 3);
        data.put("data", rowKV("alerttype", "3", "pname", "张女士"));
        return Result.successJson(data);
    }

    private List<Map<String, Object>> buildMockRows() {
        List<Map<String, Object>> rows = new ArrayList<>();
        LocalDate today = LocalDate.now();
        rows.add(rowKV("alerttype", "1", "enumname", "生日提醒", "pname", "王先生", "birthdate", "1990-03-10", "pdate", today.plusDays(5).toString(), "tel", "13800000001", "status", "0", "policyno", ""));
        rows.add(rowKV("alerttype", "3", "enumname", "续费提醒", "pname", "李女士", "birthdate", "", "pdate", today.plusDays(12).toString(), "tel", "13800000002", "status", "1", "policyno", "BD20260001"));
        rows.add(rowKV("alerttype", "4", "enumname", "满期提醒", "pname", "陈先生", "birthdate", "", "pdate", today.plusDays(18).toString(), "tel", "13800000003", "status", "0", "policyno", "BD20260002"));
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
