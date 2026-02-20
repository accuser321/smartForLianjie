package com.nh.smart.controller.compat;

import com.nh.smart.entity.base.Result;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/abt/abtComKjCustomBasemsg")
public class V2CompatCustomBasemsgController {

    @PostMapping("/insertCustomBasemsg")
    public Result insertCustomBasemsg(@RequestBody Map<String, Object> map) {
        return Result.successJson();
    }

    @PostMapping("/insertCustomLabel")
    public Result insertCustomLabel(@RequestBody Map<String, Object> map) {
        return Result.successJson();
    }

    @PostMapping("/insertKHLabel")
    public Result insertKHLabel(@RequestBody Map<String, Object> map) {
        return Result.successJson();
    }
}

