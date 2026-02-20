package com.nh.smart.controller.compat;

import com.nh.smart.entity.base.Result;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/cpk/abtComYxhdCouponlist")
public class V2CompatCouponController {

    @GetMapping("/selectempCouponList")
    public Result selectempCouponList(@RequestParam Map<String, String> params) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("rows", new ArrayList<>());
        result.put("total", 0);
        result.put("totalpage", 0);
        return Result.successJson(result);
    }

    @GetMapping("/grantCoupon")
    public Result grantCoupon(@RequestParam Map<String, String> params) {
        return Result.successJson(new ArrayList<>());
    }

    @GetMapping("/useFWCoupon")
    public Result useFWCoupon(@RequestParam Map<String, String> params) {
        return Result.successJson(new LinkedHashMap<String, Object>());
    }
}
