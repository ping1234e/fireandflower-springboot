package com.tencent.wxcloudrun.common;

import com.tencent.wxcloudrun.config.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CommonController {
    @PostMapping("/msg/rece")
    public ApiResponse getMsg(@RequestBody JSONObject request) {
        log.error("getMsg,{}", request.toString());
        return ApiResponse.ok();
    }
}
