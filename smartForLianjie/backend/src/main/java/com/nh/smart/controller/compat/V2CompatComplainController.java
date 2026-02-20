package com.nh.smart.controller.compat;

import com.nh.smart.entity.base.Result;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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
        Map<String, Object> json = new LinkedHashMap<>();
        json.put("rows", new ArrayList<>());
        json.put("total", 0);
        json.put("totalpage", 0);
        return Result.successJson(json);
    }
}

