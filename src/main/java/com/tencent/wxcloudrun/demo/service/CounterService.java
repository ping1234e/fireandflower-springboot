package com.tencent.wxcloudrun.demo.service;

import com.tencent.wxcloudrun.demo.model.Counter;

import java.util.Optional;

public interface CounterService {

  Optional<Counter> getCounter(Integer id);

  void upsertCount(Counter counter);

  void clearCount(Integer id);
}
