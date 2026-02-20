package com.nh.smart.controller.compat;

import com.nh.smart.entity.base.Result;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/saas/abtComEmpnoJifen")
public class V2CompatJifenController {

    @PostMapping("/jfList")
    public Result jfList(@RequestBody Map<String, Object> data) {
        List<Map<String, Object>> rows = new ArrayList<>();
        rows.add(rowKV("date", "2026-02-20", "remark", "每日登录", "jf", 5));
        rows.add(rowKV("date", "2026-02-19", "remark", "分享素材", "jf", 2));
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("rows", rows);
        result.put("total", rows.size());
        result.put("totalpage", 1);
        return Result.successJson(result);
    }

    @GetMapping("/getJF")
    public Result getJF() {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("JF", 5);
        result.put("state", 0);
        return Result.successJson(result);
    }

    @GetMapping("/jfSum")
    public Result jfSum() {
        return Result.successJson(0);
    }

    private Map<String, Object> rowKV(Object... values) {
        Map<String, Object> map = new LinkedHashMap<>();
        for (int i = 0; i + 1 < values.length; i += 2) {
            map.put(String.valueOf(values[i]), values[i + 1]);
        }
        return map;
    }
}

