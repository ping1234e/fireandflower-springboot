package com.tencent.wxcloudrun.demo.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Counter {
    private long id;
    private Integer count;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
