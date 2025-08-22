package com.tencent.wxcloudrun.xql.service.impl;

import com.tencent.wxcloudrun.xql.dao.BizMapper;
import com.tencent.wxcloudrun.xql.model.Xql;
import com.tencent.wxcloudrun.xql.service.BizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BizServiceImpl implements BizService {

    final BizMapper bizMapper;

    public BizServiceImpl(@Autowired BizMapper bizMapper) {
        this.bizMapper = bizMapper;
    }

    @Override
    public Optional<Xql> getCounter(Integer id) {
        return Optional.ofNullable(bizMapper.getCounter(id));
    }

    @Override
    public void upsertCount(Xql counter) {
        bizMapper.upsertCount(counter);
    }

    @Override
    public void clearCount(Integer id) {
        bizMapper.clearCount(id);
    }
}
