package com.nh.smart.controller.compat;

import com.nh.smart.entity.base.Result;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/abt/abtComKjComplain")
public class V2CompatComplainController {

    @PostMapping({"/insertComplain", "/addComplain"})
    public Result insertComplain(@RequestBody Map<String, Object> data) {
        return Result.successJson();
    }

    @GetMapping("/complainlist")
    public Result complainlist(@RequestParam Map<String, String> params) {
        List<Map<String, Object>> rows = new ArrayList<>();
        rows.add(rowKV("id", 1, "tsreason", "建议优化提醒页搜索体验", "status", "1"));
        rows.add(rowKV("id", 2, "tsreason", "建议增加客户筛选标签", "status", "0"));
        return Result.successJson(rows);
    }

    private Map<String, Object> rowKV(Object... values) {
        Map<String, Object> map = new LinkedHashMap<>();
        for (int i = 0; i + 1 < values.length; i += 2) {
            map.put(String.valueOf(values[i]), values[i + 1]);
        }
        return map;
    }
}
