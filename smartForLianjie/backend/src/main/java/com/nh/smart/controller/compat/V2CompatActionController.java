package com.nh.smart.controller.compat;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nh.smart.annotation.MultiRequestBody;
import com.nh.smart.dao.record.SmartComWxmsgDao;
import com.nh.smart.entity.base.Result;
import com.nh.smart.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/abt/abtComKjActionRecord")
public class V2CompatActionController {

    @Autowired
    private SmartComWxmsgDao smartComWxmsgDao;

    private String safeComid() {
        try {
            return JwtTokenUtil.getComid();
        } catch (Exception e) {
            return "";
        }
    }

    private String safeEmpno() {
        try {
            return JwtTokenUtil.getEmpno();
        } catch (Exception e) {
            return "";
        }
    }

    private JSONObject emptyPageRowsArray() {
        JSONObject json = new JSONObject();
        json.put("rows", new ArrayList<>());
        json.put("total", 0);
        json.put("totalpage", 0);
        return json;
    }

    private JSONObject emptyPageRowsMap() {
        JSONObject json = new JSONObject();
        json.put("rows", new LinkedHashMap<>());
        json.put("total", 0);
        json.put("totalpage", 0);
        return json;
    }

    @PostMapping("/getCommunicationKH")
    public Result getCommunicationKH(@RequestBody Map<String, Object> map) {
        return Result.successJson(emptyPageRowsArray());
    }

    @PostMapping("/getRecentlyKH")
    public Result getRecentlyKH(@RequestBody Map<String, Object> map) {
        return Result.successJson(emptyPageRowsArray());
    }

    @PostMapping("/getStandardKH")
    public Result getStandardKH(@RequestBody Map<String, Object> map) {
        return Result.successJson(emptyPageRowsArray());
    }

    @PostMapping("/getColleague")
    public Result getColleague(@RequestBody Map<String, Object> map) {
        return Result.successJson(emptyPageRowsArray());
    }

    @PostMapping("/getKHContacts")
    public Result getKHContacts(@RequestBody Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("relationship", new ArrayList<>());
        json.put("message", new ArrayList<>());
        return Result.successJson(json);
    }

    @PostMapping("/getFollowList")
    public Result getFollowList(@RequestBody Map<String, Object> map) {
        return Result.successJson(emptyPageRowsMap());
    }

    @PostMapping("/insertFollow")
    public Result insertFollow(@MultiRequestBody String userid, @MultiRequestBody String content) {
        return Result.successJson();
    }

    @DeleteMapping("/deleteFollow")
    public Result deleteFollow(@RequestParam(required = false) String id) {
        return Result.successJson();
    }

    @DeleteMapping("/deleteFollow/{id}")
    public Result deleteFollowByPath(@PathVariable String id) {
        return Result.successJson();
    }

    @PostMapping("/getInteractiveList")
    public Result getInteractiveList(@RequestBody Map<String, Object> map) {
        return Result.successJson(emptyPageRowsMap());
    }

    @GetMapping("/getAIAnalysis/{userid}")
    public Result getAIAnalysisByPath(@PathVariable String userid) {
        return Result.successJson(buildAIAnalysis());
    }

    @GetMapping("/getAIAnalysis")
    public Result getAIAnalysisByParam(@RequestParam(required = false) String userid) {
        return Result.successJson(buildAIAnalysis());
    }

    private JSONObject buildAIAnalysis() {
        JSONObject json = new JSONObject();
        json.put("activeList", new ArrayList<>());
        json.put("demandList", new ArrayList<>());
        json.put("interactive", new ArrayList<>());
        json.put("interest", new ArrayList<>());
        return json;
    }

    @PostMapping("/selectTimeKHByEmpno")
    public Result selectTimeKHByEmpno(@RequestBody Map<String, Object> map) {
        return Result.successJson(emptyPageRowsMap());
    }

    @PostMapping("/selectKHByOtype")
    public Result selectKHByOtype(@RequestBody Map<String, Object> map) {
        return Result.successJson(emptyPageRowsMap());
    }

    @GetMapping("/getRecordAnalyze")
    public Result getRecordAnalyze() {
        JSONObject json = new JSONObject();
        json.put("numList", new ArrayList<>());
        json.put("activeList", new ArrayList<>());
        json.put("khInteractive", new ArrayList<>());
        return Result.successJson(json);
    }

    @GetMapping("/getRecordKH/{KHday}")
    public Result getRecordKHByPath(@PathVariable String KHday) {
        JSONObject json = new JSONObject();
        json.put("hkDaynum", new ArrayList<>());
        return Result.successJson(json);
    }

    @GetMapping("/getRecordKH")
    public Result getRecordKHByParam(@RequestParam(required = false) String KHday) {
        JSONObject json = new JSONObject();
        json.put("hkDaynum", new ArrayList<>());
        return Result.successJson(json);
    }

    @GetMapping("/getRecordRD/{Readday}")
    public Result getRecordRDByPath(@PathVariable String Readday) {
        JSONObject json = new JSONObject();
        json.put("rdDaynum", new ArrayList<>());
        return Result.successJson(json);
    }

    @GetMapping("/getRecordRD")
    public Result getRecordRDByParam(@RequestParam(required = false) String Readday) {
        JSONObject json = new JSONObject();
        json.put("rdDaynum", new ArrayList<>());
        return Result.successJson(json);
    }

    @GetMapping("/getYSLD")
    public Result getYSLD() {
        return Result.successJson(new ArrayList<>());
    }

    @PostMapping("/selectMessage")
    public Result selectMessage(@RequestBody Map<String, Object> map) {
        try {
            String comid = safeComid();
            String empno = safeEmpno();
            long page = Long.parseLong(String.valueOf(map.getOrDefault("page", "1")));
            long size = Long.parseLong(String.valueOf(map.getOrDefault("size", "10")));
            IPage<Map<String, Object>> data = smartComWxmsgDao.selectWxmsgPage(new Page(page, size), comid, empno);
            JSONObject json = new JSONObject();
            json.put("rows", data.getRecords());
            json.put("total", data.getTotal());
            json.put("totalpage", data.getPages());
            return Result.successJson(json);
        } catch (Exception e) {
            return Result.successJson(emptyPageRowsArray());
        }
    }

    @GetMapping("/getKjChat/{khuserid}/{empno}")
    public Result getKjChatByPath(@PathVariable String khuserid, @PathVariable String empno) {
        return getKjChatInternal(khuserid, empno, null);
    }

    @GetMapping("/getKjChat/{khuserid}/{empno}/{type}")
    public Result getKjChatByPathWithType(@PathVariable String khuserid, @PathVariable String empno, @PathVariable String type) {
        return getKjChatInternal(khuserid, empno, type);
    }

    @GetMapping("/getKjChat")
    public Result getKjChatByParam(@RequestParam String khuserid, @RequestParam String empno, @RequestParam(required = false) String type) {
        return getKjChatInternal(khuserid, empno, type);
    }

    private Result getKjChatInternal(String khuserid, String empno, String type) {
        try {
            String comid = safeComid();
            List<Map<String, Object>> list = smartComWxmsgDao.getKjChat(comid, empno, Integer.valueOf(khuserid), type);
            return Result.successJson(list);
        } catch (Exception e) {
            return Result.successJson(new ArrayList<>());
        }
    }

    @GetMapping("/getKjChatNum/{khuserid}/{empno}")
    public Result getKjChatNumByPath(@PathVariable String khuserid, @PathVariable String empno) {
        return getKjChatNumInternal(khuserid, empno);
    }

    @GetMapping("/getKjChatNum")
    public Result getKjChatNumByParam(@RequestParam String khuserid, @RequestParam String empno) {
        return getKjChatNumInternal(khuserid, empno);
    }

    private Result getKjChatNumInternal(String khuserid, String empno) {
        try {
            String comid = safeComid();
            List<Map<String, Object>> list = smartComWxmsgDao.getKjChat(comid, empno, Integer.valueOf(khuserid), "2");
            JSONObject json = new JSONObject();
            json.put("num", list == null ? 0 : list.size());
            return Result.successJson(json);
        } catch (Exception e) {
            JSONObject json = new JSONObject();
            json.put("num", 0);
            return Result.successJson(json);
        }
    }
}

