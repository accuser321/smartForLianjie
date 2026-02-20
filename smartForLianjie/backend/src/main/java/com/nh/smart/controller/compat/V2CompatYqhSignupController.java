package com.nh.smart.controller.compat;

import com.nh.smart.entity.base.Result;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/abt/abtComKjYqhSignup")
public class V2CompatYqhSignupController {

    private static final List<Map<String, Object>> PUSH_TEMPLATES = new ArrayList<>();
    private static final Map<String, Set<String>> USER_READ_PUSH = new ConcurrentHashMap<>();

    static {
        PUSH_TEMPLATES.add(basePush("1", "系统公告", "欢迎使用V2版本", "2026-02-20 10:00:00", "4", "compat/push-demo.html"));
        PUSH_TEMPLATES.add(basePush("2", "服务通知", "本期功能持续更新中", "2026-02-19 14:30:00", "4", "compat/push-service.html"));
        PUSH_TEMPLATES.add(basePush("3", "运营提醒", "今日建议跟进3位高意向客户", "2026-02-18 11:20:00", "3", "https://example.com/compat/push/operation"));
    }

    private static Map<String, Object> basePush(String id, String title, String content, String pushtime, String stagcode, String pcontent) {
        Map<String, Object> row = new LinkedHashMap<>();
        row.put("id", id);
        row.put("title", title);
        row.put("content", content);
        row.put("pushtime", pushtime);
        row.put("stagcode", stagcode);
        row.put("pcontent", pcontent);
        return row;
    }

    @PostMapping("/selectKFMsgList")
    public Result selectKFMsgList(@RequestBody Map<String, Object> data) {
        String userId = String.valueOf(data.getOrDefault("userId", "10001"));
        Set<String> readSet = USER_READ_PUSH.computeIfAbsent(userId, k -> new HashSet<>());
        List<Map<String, Object>> rows = new ArrayList<>();
        for (Map<String, Object> template : PUSH_TEMPLATES) {
            String id = String.valueOf(template.get("id"));
            rows.add(rowKV(
                "id", Integer.valueOf(id),
                "userid", userId,
                "title", template.get("title"),
                "content", template.get("content"),
                "pushtime", template.get("pushtime"),
                "status", readSet.contains(id) ? "1" : "0"
            ));
        }
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("rows", rows);
        result.put("total", rows.size());
        result.put("totalpage", 1);
        return Result.successJson(result);
    }

    @PostMapping("/selectPush")
    public Result selectPush(@RequestBody Map<String, Object> data) {
        String pushid = String.valueOf(data.getOrDefault("pushid", "1"));
        String userId = String.valueOf(data.getOrDefault("userId", "10001"));
        USER_READ_PUSH.computeIfAbsent(userId, k -> new HashSet<>()).add(pushid);
        Map<String, Object> hit = null;
        for (Map<String, Object> template : PUSH_TEMPLATES) {
            if (pushid.equals(String.valueOf(template.get("id")))) {
                hit = template;
                break;
            }
        }
        if (hit == null) {
            hit = PUSH_TEMPLATES.get(0);
        }
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("ptitle", hit.get("title"));
        result.put("stagcode", hit.get("stagcode"));
        result.put("pcontent", hit.get("pcontent"));
        return Result.successJson(result);
    }

    @GetMapping("/selectWDKFMsg")
    public Result selectWDKFMsg(@RequestParam Map<String, String> params) {
        String userId = params.getOrDefault("userId", "10001");
        Set<String> readSet = USER_READ_PUSH.computeIfAbsent(userId, k -> new HashSet<>());
        Map<String, Object> data = new LinkedHashMap<>();
        int unread = Math.max(0, PUSH_TEMPLATES.size() - readSet.size());
        data.put("num", unread);
        return Result.successJson(data);
    }

    private Map<String, Object> rowKV(Object... values) {
        Map<String, Object> map = new LinkedHashMap<>();
        for (int i = 0; i + 1 < values.length; i += 2) {
            map.put(String.valueOf(values[i]), values[i + 1]);
        }
        return map;
    }
}
