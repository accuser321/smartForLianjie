package com.nh.smart.controller.compat;

import com.nh.smart.entity.base.Result;
import org.apache.commons.lang3.StringUtils;
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
        String appurl = String.valueOf(data == null ? "" : data.getOrDefault("appurl", "")).trim();
        if (StringUtils.isBlank(appurl)) {
            return Result.errorJson("参数缺失：appurl不能为空");
        }
        String function = "home";
        int q = appurl.indexOf('?');
        String path = q >= 0 ? appurl.substring(0, q) : appurl;
        if (path.contains("/")) {
            String[] arr = path.split("/");
            String tail = arr[arr.length - 1].trim();
            if (!tail.isEmpty()) {
                function = tail;
            }
        }
        Map<String, Object> user = new LinkedHashMap<>();
        user.put("userid", 10001);
        user.put("comid", "COMPAT");
        user.put("empno", "E10001");
        user.put("empname", "演示用户");
        user.put("rytype", "W");
        user.put("headimg", "");
        user.put("jglower", "JG001");
        user.put("tjcode", "TJ001");
        Map<String, Object> wrap = new LinkedHashMap<>();
        wrap.put("user", user);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("function", function);
        result.put("data", wrap);
        return Result.successJson(result);
    }

    @PostMapping("/saas/ocr/bankCardOcr")
    public Result bankCardOcr(@RequestParam(required = false) Map<String, String> params) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("bank_name", "");
        data.put("bank_card_number", "");
        return Result.successJson(data);
    }
}
