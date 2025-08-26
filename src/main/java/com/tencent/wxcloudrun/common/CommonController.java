package com.tencent.wxcloudrun.common;

import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.config.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class CommonController {
    @PostMapping("/msg/rece")
    public ApiResponse getMsg(@RequestBody JSONObject jsonObject, HttpRequest request) {
        log.error("getMsg,{}", jsonObject.toString());
        // {"Content":"1","CreateTime":1755854772,"ToUserName":"gh_d17c82863523","FromUserName":"oe9UKt6zlWd9hHk8S79ggqXJsqpY","MsgType":"text","MsgId":25137798904747621}
        String content = jsonObject.getString("Content");
        if (StringUtils.isBlank(content)) {
            return ApiResponse.error("消息为空");
        }

        Header[] headers = request.getHeaders("X-WX-CLOUDBASE-ACCESS-TOKEN");
        if (headers == null || headers.length < 1) {
            return ApiResponse.error("未获取到请求头");
        }
        String value = headers[0].getValue();
        if (StringUtils.isBlank(value)) {
            return ApiResponse.error("未获取到请求头");
        }
        Map<String, Object> payload = new HashMap<>(16);
        payload.put("FromUserName", jsonObject.getString("ToUserName"));
        payload.put("ToUserName", jsonObject.getString("FromUserName"));
        payload.put("CreateTime", LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")));
        payload.put("MsgType", "text");
        if (content.contains("学企来")) {
            String[] split = content.split(":");
            if (split.length <= 1) {
                payload.put("Content", "启动格式->'学企来:account:password:accessToken:ddmm:ww',输入一次就可以了，如果没成功可以再试试\n" +
                        "  ——>账号密码为八三管理平台密码\n" +
                        "  ——>accessToken为pushplus的授权码，可以接受推送结果，非必填\n" +
                        "  ——>ddmm，运行时间，浮动上下一两分钟，如0934表示在早上09:33-09:35之间运行\n" +
                        "  ——>ww表示周一到周日,如1-7表示周一到周日");
            } else {
                String account = split[1];
                String password = split[2];
                String accessToken = split[3];
                String ddmm = split[4];
                String ww = split[5];
                if (StringUtils.isAnyBlank(account, password)) {
                    // 回复使用格式
                    // 学企来:account:password:accessToken:ddmm:ww
                    // 说明
                    // 账号密码为八三管理平台密码，
                    // accessToken为pushplus的授权码，可以接受推送结果，非必填
                    // ddmm，运行时间，浮动上下一两分钟，如0934表示在早上09:33-09:35之间运行
                    // ww表示周一到周日,如1-7表示周一到周日
                    payload.put("Content", "启动格式->'学企来:account:password:accessToken:ddmm:ww',输入一次就可以了，如果没成功可以再试试\n" +
                            "  ——>账号密码为八三管理平台密码\n" +
                            "  ——>accessToken为pushplus的授权码，可以接受推送结果，非必填\n" +
                            "  ——>ddmm，运行时间，浮动上下一两分钟，如0934表示在早上09:33-09:35之间运行\n" +
                            "  ——> ww表示周一到周日,如1-7表示周一到周日");
                } else {
                    // 加入处理表
                    payload.put("Content", "加入成功");
                }
            }

        }
        try {
            log.error("回复消息,{}", JSONObject.toJSONString(payload));
            // 被动回复
            HttpResponse response = HttpUtils.doPost("http://api.weixin.qq.com", "/cgi-bin/message/custom/send?cloudbase_access_token=" + value, null, null, payload);
            try (InputStream content1 = response.getEntity().getContent()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(content1));
                String line;
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                log.error("回复消息结果{}", stringBuilder);
            }

        } catch (Exception e) {
            log.error("回复消息失败{}", e.getMessage());
            return ApiResponse.error("回复消息失败");
        }
//        payload.put("Content", "测试接口返回值会不会触发回复！！" + jsonObject.getString("FromUserName"));
        return ApiResponse.ok();
    }
}
