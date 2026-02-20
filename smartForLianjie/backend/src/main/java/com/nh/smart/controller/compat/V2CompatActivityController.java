package com.nh.smart.controller.compat;

import com.nh.smart.entity.base.Result;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/abt/abtComKjActivity")
public class V2CompatActivityController {

    @PostMapping("/selectActivity")
    public Result selectActivity(@RequestBody Map<String, Object> data) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("rows", new ArrayList<>());
        result.put("total", 0);
        result.put("totalpage", 0);
        return Result.successJson(result);
    }

    @GetMapping("/getActivity")
    public Result getActivity(@RequestParam Map<String, String> params) {
        return Result.successJson(new LinkedHashMap<String, Object>());
    }
}

