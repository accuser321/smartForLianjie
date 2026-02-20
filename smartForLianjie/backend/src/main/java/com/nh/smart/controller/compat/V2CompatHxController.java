package com.nh.smart.controller.compat;

import com.nh.smart.entity.base.Result;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
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
        data.put("rows", java.util.Collections.emptyList());
        data.put("total", 0);
        data.put("totalpage", 0);
        return Result.successJson(data);
    }

    @GetMapping("/hx/abtComEmpno/selectGRZXByEmpno")
    public Result selectGRZXByEmpno(@RequestParam Map<String, String> params) {
        return Result.successJson(new LinkedHashMap<String, Object>());
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
}

