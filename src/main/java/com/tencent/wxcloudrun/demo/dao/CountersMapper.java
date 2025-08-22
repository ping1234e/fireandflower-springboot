package com.tencent.wxcloudrun.demo.dao;

import com.tencent.wxcloudrun.demo.model.Counter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CountersMapper {

  Counter getCounter(@Param("id") Integer id);

  void upsertCount(Counter counter);

  void clearCount(@Param("id") Integer id);
}
