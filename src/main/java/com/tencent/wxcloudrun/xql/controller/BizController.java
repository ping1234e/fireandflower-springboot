package com.tencent.wxcloudrun.xql.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.demo.dto.CounterRequest;
import com.tencent.wxcloudrun.xql.model.Xql;
import com.tencent.wxcloudrun.xql.service.BizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * counter控制器
 */
@RestController
@RequestMapping("/xql")
public class BizController {

    final BizService bizService;
    final Logger logger;

    public BizController(@Autowired BizService bizService) {
        this.bizService = bizService;
        this.logger = LoggerFactory.getLogger(BizController.class);
    }


    /**
     * 获取当前计数
     *
     * @return API response json
     */
    @GetMapping(value = "/api/count")
    ApiResponse query() {
        logger.info("/api/count get request");
        Optional<Xql> counter = bizService.getCounter(1);
        String count = null;
        if (counter.isPresent()) {
            count = counter.get().getAccount();
        }

        return ApiResponse.ok(count);
    }

}