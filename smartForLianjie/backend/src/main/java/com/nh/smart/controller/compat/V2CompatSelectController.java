package com.nh.smart.controller.compat;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nh.smart.annotation.MultiRequestBody;
import com.nh.smart.entity.base.Result;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/saas/select")
public class V2CompatSelectController {

    @GetMapping("/selectKHBQ")
    public Result selectKHBQ() {
        return Result.successJson(new ArrayList<>());
    }

    @PostMapping("/selectMJ")
    public Result selectMJ(@MultiRequestBody(required = false) List<String> list) {
        JSONArray result = new JSONArray();
        if (list != null && !list.isEmpty()) {
            for (String code : list) {
                JSONObject item = new JSONObject();
                item.put("code", code);
                item.put("value", new ArrayList<>());
                result.add(item);
            }
        } else {
            JSONObject item = new JSONObject();
            item.put("value", new ArrayList<>());
            result.add(item);
        }
        return Result.successJson(result);
    }
}

