package com.tencent.wxcloudrun.xql.dao;

import com.tencent.wxcloudrun.demo.model.Counter;
import com.tencent.wxcloudrun.xql.model.Xql;
import org.apache.ibatis.annotations.Param;

@org.apache.ibatis.annotations.Mapper
public interface BizMapper {

  Xql getCounter(@Param("id") Integer id);

  void upsertCount(Xql counter);

  void clearCount(@Param("id") Integer id);
}
