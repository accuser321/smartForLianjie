package com.nh.smart.controller.compat;

import com.nh.smart.entity.base.Result;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/saas/upload")
public class V2CompatUploadController {

    @PostMapping("/uploadBase64Img")
    public Result uploadBase64Img(@RequestBody Map<String, Object> data) {
        String type = String.valueOf(data.getOrDefault("type", ""));
        Map<String, Object> result = new LinkedHashMap<>();
        if ("WZ".equalsIgnoreCase(type)) {
            // 前端编辑器视频分支会读取 path[0].url
            List<Map<String, String>> path = new ArrayList<>();
            Map<String, String> item = new LinkedHashMap<>();
            item.put("url", "compat/video-placeholder.mp4");
            path.add(item);
            result.put("path", path);
        } else {
            // 普通图片上传分支读取 data.path
            result.put("path", "compat/img-placeholder.jpg");
        }
        return Result.successJson(result);
    }

    @PostMapping("/uploadImg")
    public Result uploadImg(@RequestBody(required = false) Map<String, Object> data) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("path", "compat/img-placeholder.jpg");
        return Result.successJson(result);
    }
}

