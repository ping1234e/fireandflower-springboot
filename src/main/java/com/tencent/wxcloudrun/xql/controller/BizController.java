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
    @GetMapping(value = "/api/count1")
    ApiResponse get() {
        logger.info("/api/count get request");
        Optional<Xql> counter = bizService.getCounter(1);
        String count = null;
        if (counter.isPresent()) {
            count = counter.get().getAccount();
        }

        return ApiResponse.ok(count);
    }


    /**
     * 更新计数，自增或者清零
     *
     * @param request {@link CounterRequest}
     * @return API response json
     */
    @PostMapping(value = "/api/count1")
    ApiResponse create(@RequestBody CounterRequest request) {
        logger.info("/api/count post request, action: {}", request.getAction());

        Optional<Xql> curCounter = bizService.getCounter(1);
        if (request.getAction().equals("inc")) {
            String count = "1";
            if (curCounter.isPresent()) {
                count += curCounter.get().getAccount();
            }
            Xql counter = new Xql();
            counter.setId(1);
            counter.setAccount(count);
            bizService.upsertCount(counter);
            return ApiResponse.ok(count);
        } else if (request.getAction().equals("clear")) {
            if (!curCounter.isPresent()) {
                return ApiResponse.ok(0);
            }
            bizService.clearCount(1);
            return ApiResponse.ok(0);
        } else {
            return ApiResponse.error("参数action错误");
        }
    }

}