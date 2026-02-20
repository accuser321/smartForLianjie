package com.nh.smart.controller.compat;

import com.nh.smart.entity.base.Result;
import com.nh.smart.entity.material.SmartComMuserCard;
import com.nh.smart.service.material.SmartComKjLibwService;
import com.nh.smart.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/abt/abtComMuserCard")
public class V2CompatMuserCardController {

    @Autowired
    private SmartComKjLibwService smartComKjLibwService;

    @GetMapping("/selectUserCard")
    public Result selectUserCard(@RequestParam String empno,
                                 @RequestParam(defaultValue = "0") String flag,
                                 @RequestParam(defaultValue = "N") String type,
                                 HttpServletRequest request) {
        try {
            String serverName = IpUtil.getServerName(request);
            SmartComMuserCard data = smartComKjLibwService.selectUserCard(empno, flag, type, serverName);
            return Result.successJson(data);
        } catch (Exception e) {
            return Result.successJson(new SmartComMuserCard());
        }
    }

    @PostMapping("/uploadByMediaId")
    public Result uploadByMediaId(@RequestBody(required = false) Map<String, Object> data) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("path", "compat/img-placeholder.jpg");
        return Result.successJson(result);
    }
}

