package com.nh.smart.controller.compat;

import com.nh.smart.entity.base.Result;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/abt/abtComKjYqhSignup")
public class V2CompatYqhSignupController {

    @PostMapping("/selectKFMsgList")
    public Result selectKFMsgList(@RequestBody Map<String, Object> data) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("rows", new ArrayList<>());
        result.put("total", 0);
        result.put("totalpage", 0);
        return Result.successJson(result);
    }

    @PostMapping("/selectPush")
    public Result selectPush(@RequestBody Map<String, Object> data) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("rows", new ArrayList<>());
        result.put("total", 0);
        result.put("totalpage", 0);
        return Result.successJson(result);
    }

    @GetMapping("/selectWDKFMsg")
    public Result selectWDKFMsg(@RequestParam Map<String, String> params) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("num", 0);
        return Result.successJson(data);
    }
}

