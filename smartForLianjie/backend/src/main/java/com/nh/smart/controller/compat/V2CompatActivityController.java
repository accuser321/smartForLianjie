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
        String activityno = params.getOrDefault("activityno", "A20260220001");
        String title;
        String body;
        if ("A20260220002".equals(activityno)) {
            title = "保险配置公开课";
            body = "围绕家庭保障、资产配置、健康险与年金产品进行案例分享。";
        } else {
            title = "客户经营实战沙龙";
            body = "聚焦客户画像、内容触达与转化跟进，提供标准化实战模板。";
        }
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("activityno", activityno);
        data.put("text", "<h3>" + title + "</h3><p>" + body + "</p><p>（当前为联调演示内容）</p>");
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
