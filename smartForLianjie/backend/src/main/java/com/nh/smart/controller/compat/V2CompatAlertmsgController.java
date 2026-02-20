package com.nh.smart.controller.compat;

import com.alibaba.fastjson.JSONObject;
import com.nh.smart.entity.base.Result;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/core/abtComAlertmsg")
public class V2CompatAlertmsgController {

    @PostMapping("/selectByPage")
    public Result selectByPage(@RequestBody Map<String, Object> data) {
        JSONObject json = new JSONObject();
        json.put("rows", new ArrayList<>());
        json.put("total", 0);
        json.put("totalpage", 0);
        return Result.successJson(json);
    }

    @PostMapping("/checkDetails")
    public Result checkDetails(@RequestBody Map<String, Object> data) {
        JSONObject json = new JSONObject();
        json.put("riskconList", new ArrayList<>());
        json.put("classlist", new LinkedHashMap<>());
        return Result.successJson(json);
    }

    // basic 模块也会调用该接口
    @GetMapping("/statistics")
    public Result statistics() {
        return Result.successJson(new LinkedHashMap<String, Object>());
    }
}

