package com.nh.smart.controller.compat;

import com.nh.smart.entity.base.Result;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
public class V2CompatHxController {

    @GetMapping("/hx/abtComEmpno/selectYHByEmpno")
    public Result selectYHByEmpno(@RequestParam Map<String, String> params) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("bankzh", "");
        data.put("souhttp", "");
        data.put("bankcode", "");
        data.put("bankno", "");
        data.put("empno", params.getOrDefault("empno", ""));
        return Result.successJson(data);
    }

    @GetMapping("/hx/abtComEmpno/selectGZByEmpno")
    public Result selectGZByEmpno(@RequestParam Map<String, String> params) {
        Map<String, Object> data = new LinkedHashMap<>();
        List<Map<String, Object>> result = new ArrayList<>();
        result.add(rowKV("label", "基本工资", "value", "0.00"));
        result.add(rowKV("label", "绩效工资", "value", "0.00"));
        result.add(rowKV("label", "推广奖励", "value", "0.00"));
        data.put("result", result);
        return Result.successJson(data);
    }

    @GetMapping("/hx/abtComEmpno/selectGRZXByEmpno")
    public Result selectGRZXByEmpno(@RequestParam Map<String, String> params) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("empno", params.getOrDefault("empno", ""));
        data.put("zb", new ArrayList<>());
        List<Map<String, Object>> gzlist = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            LocalDate d = LocalDate.now().minusMonths(i);
            gzlist.add(rowKV(
                "date", d.toString(),
                "yearmon", d.getYear() + "-" + String.format(java.util.Locale.ROOT, "%02d", d.getMonthValue()),
                "hjsale", "0.00",
                "value", "0"
            ));
        }
        data.put("gzlist", gzlist);
        return Result.successJson(data);
    }

    @PostMapping("/hx/abtComEmpno/upYHKMessage")
    public Result upYHKMessage(@RequestBody Map<String, Object> data) {
        return Result.successJson();
    }

    @GetMapping("/hx/abtComEmpnoApply/getcontract")
    public Result getcontract() {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("url", "");
        return Result.successJson(data);
    }

    @GetMapping("/hx/abtComHxCpList/ShouYelist")
    public Result ShouYelist() {
        return Result.successJson(java.util.Collections.emptyList());
    }

    private Map<String, Object> rowKV(Object... values) {
        Map<String, Object> map = new LinkedHashMap<>();
        for (int i = 0; i + 1 < values.length; i += 2) {
            map.put(String.valueOf(values[i]), values[i + 1]);
        }
        return map;
    }
}
