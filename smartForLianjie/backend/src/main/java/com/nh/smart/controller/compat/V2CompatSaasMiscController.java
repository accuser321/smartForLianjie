package com.nh.smart.controller.compat;

import com.nh.smart.entity.base.Result;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class V2CompatSaasMiscController {

    @GetMapping("/saas/abtComSlotInvoke/init")
    public Result init() {
        return Result.successJson(new ArrayList<>());
    }

    @PostMapping("/saas/dr/access")
    public Result access(@RequestBody Map<String, Object> data) {
        return Result.successJson(new LinkedHashMap<String, Object>());
    }

    @PostMapping("/saas/ocr/bankCardOcr")
    public Result bankCardOcr(@RequestParam(required = false) Map<String, String> params) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("bank_name", "");
        data.put("bank_card_number", "");
        return Result.successJson(data);
    }
}

