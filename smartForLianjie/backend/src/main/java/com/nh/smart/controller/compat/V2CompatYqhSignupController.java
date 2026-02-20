package com.nh.smart.controller.compat;

import com.nh.smart.entity.base.Result;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/abt/abtComKjYqhSignup")
public class V2CompatYqhSignupController {

    @PostMapping("/selectKFMsgList")
    public Result selectKFMsgList(@RequestBody Map<String, Object> data) {
        List<Map<String, Object>> rows = new ArrayList<>();
        rows.add(rowKV("id", 1, "title", "系统公告", "content", "欢迎使用V2版本", "pushtime", "2026-02-20 10:00:00", "status", "1"));
        rows.add(rowKV("id", 2, "title", "服务通知", "content", "本期功能持续更新中", "pushtime", "2026-02-19 14:30:00", "status", "0"));
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("rows", rows);
        result.put("total", rows.size());
        result.put("totalpage", 1);
        return Result.successJson(result);
    }

    @PostMapping("/selectPush")
    public Result selectPush(@RequestBody Map<String, Object> data) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("ptitle", "系统消息");
        result.put("stagcode", "4");
        result.put("pcontent", "compat/push-demo.html");
        return Result.successJson(result);
    }

    @GetMapping("/selectWDKFMsg")
    public Result selectWDKFMsg(@RequestParam Map<String, String> params) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("num", 0);
        return Result.successJson(data);
    }

    private Map<String, Object> rowKV(Object... values) {
        Map<String, Object> map = new LinkedHashMap<>();
        for (int i = 0; i + 1 < values.length; i += 2) {
            map.put(String.valueOf(values[i]), values[i + 1]);
        }
        return map;
    }
}
