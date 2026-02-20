package com.nh.smart.controller.compat;

import com.nh.smart.entity.base.Result;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/core/abtComUserCard")
public class V2CompatUserCardController {

    @GetMapping("/selectKHbyEmpno")
    public Result selectKHbyEmpno(@RequestParam Map<String, String> params) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("rows", new ArrayList<>());
        data.put("total", 0);
        data.put("totalpage", 0);
        return Result.successJson(data);
    }

    @GetMapping("/getEmpnoPersonal")
    public Result getEmpnoPersonal(@RequestParam Map<String, String> params) {
        return Result.successJson(new LinkedHashMap<String, Object>());
    }

    @PutMapping("/upEmpnoPwd")
    public Result upEmpnoPwd(@RequestBody Map<String, Object> data) {
        return Result.successJson();
    }

    @PutMapping("/upEmpnoPhone")
    public Result upEmpnoPhone(@RequestBody Map<String, Object> data) {
        return Result.successJson();
    }

    @GetMapping("/getPersonalData")
    public Result getPersonalData(@RequestParam Map<String, String> params) {
        return Result.successJson(new LinkedHashMap<String, Object>());
    }

    @PutMapping("/upMount")
    public Result upMount(@RequestBody Map<String, Object> data) {
        return Result.successJson();
    }

    @PostMapping("/getTXRecord")
    public Result getTXRecord(@RequestBody Map<String, Object> data) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("rows", new ArrayList<>());
        result.put("total", 0);
        result.put("totalpage", 0);
        return Result.successJson(result);
    }

    @PostMapping("/getTXRecords")
    public Result getTXRecords(@RequestBody Map<String, Object> data) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("rows", new ArrayList<>());
        result.put("total", 0);
        result.put("totalpage", 0);
        return Result.successJson(result);
    }

    @PutMapping("/updatePersonalData")
    public Result updatePersonalData(@RequestBody Map<String, Object> data) {
        return Result.successJson();
    }

    @GetMapping("/getZYZMessage")
    public Result getZYZMessage(@RequestParam Map<String, String> params) {
        return Result.successJson(new LinkedHashMap<String, Object>());
    }

    @GetMapping("/isShowPromotionprice")
    public Result isShowPromotionprice(@RequestParam Map<String, String> params) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("flag", "0");
        return Result.successJson(data);
    }

    @GetMapping("/getSFZMessage")
    public Result getSFZMessage(@RequestParam Map<String, String> params) {
        return Result.successJson(new LinkedHashMap<String, Object>());
    }
}

