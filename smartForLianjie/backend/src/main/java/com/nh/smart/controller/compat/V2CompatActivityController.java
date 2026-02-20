package com.nh.smart.controller.compat;

import com.nh.smart.entity.base.Result;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/abt/abtComKjActivity")
public class V2CompatActivityController {

    @PostMapping("/selectActivity")
    public Result selectActivity(@RequestBody Map<String, Object> data) {
        List<Map<String, Object>> rows = Arrays.asList(
            rowKV("activityno", "A20260220001", "title", "客户经营实战沙龙", "img", "", "urltype", "inner", "url", ""),
            rowKV("activityno", "A20260220002", "title", "保险配置公开课", "img", "", "urltype", "inner", "url", "")
        );
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("rows", rows);
        result.put("total", rows.size());
        result.put("totalpage", 1);
        return Result.successJson(result);
    }

    @GetMapping("/getActivity")
    public Result getActivity(@RequestParam Map<String, String> params) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("text", "<h3>活动详情（演示）</h3><p>当前为接口联调占位内容，后续将替换为真实活动详情。</p>");
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
