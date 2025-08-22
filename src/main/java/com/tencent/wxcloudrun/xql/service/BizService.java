package com.tencent.wxcloudrun.xql.service;

import com.tencent.wxcloudrun.demo.model.Counter;
import com.tencent.wxcloudrun.xql.model.Xql;

import java.util.Optional;

public interface BizService {

  Optional<Xql> getCounter(Integer id);

  void upsertCount(Xql counter);

  void clearCount(Integer id);
}
