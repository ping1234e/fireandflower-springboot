package com.tencent.wxcloudrun.xql.model;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Xql {
    private long id;
    private String open_id;
    private String account;
    private String pwd;
    private String push_ak;
    private String push_type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
