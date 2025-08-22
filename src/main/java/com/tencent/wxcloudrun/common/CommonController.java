package com.tencent.wxcloudrun.common;

import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.config.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class CommonController {
    @PostMapping("/msg/rece")
    public ApiResponse getMsg(@RequestBody JSONObject request) {
        log.error("getMsg,{}", request.toString());
        // {"Content":"1","CreateTime":1755854772,"ToUserName":"gh_d17c82863523","FromUserName":"oe9UKt6zlWd9hHk8S79ggqXJsqpY","MsgType":"text","MsgId":25137798904747621}
        String url = "http://api.weixin.qq.com/cgi-bin/message/custom/send";
//const payload = {
//                touser: headers['x-wx-openid'],
//                msgtype: 'text',
//                text: {
//            content: `云托管接收消息推送成功，内容如下：\n${JSON.stringify(req.body, null, 2)}`
//        }
//    }
        Map<String,Object> payload = new HashMap<>(16);
        payload.put("touser",request.getString("FromUserName"));
        payload.put("msgtype","text");
        payload.put("text","{'content':"+request.getString("Content")+"}");
        try {
            HttpUtils.doPost("http://api.weixin.qq.com", "/cgi-bin/message/custom/send", null, null, payload);
        } catch (Exception e) {
            log.error("回复消息失败{}",e.getMessage());
            return ApiResponse.error("回复消息失败");
        }
        return ApiResponse.ok();
    }
}
