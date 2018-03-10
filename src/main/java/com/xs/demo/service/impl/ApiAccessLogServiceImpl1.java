package com.xs.demo.service.impl;

import com.xs.demo.dao.ApiAccessLogMapper;
import com.xs.demo.service.ApiAccessLogService1;
import com.yxp.common.db.entity.apiportal.ApiAccessLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * ApiAccessLogServiceImpl
 * Created by Xs on 2017/11/30.
 */
@Service
public class ApiAccessLogServiceImpl1 implements ApiAccessLogService1 {
    @Autowired
    private ApiAccessLogMapper logMapper;

    @Override
    public ApiAccessLog insert(ApiAccessLog record) {
        return null;
    }

    @Transactional(value = "slaveTransactionManager", propagation = Propagation.REQUIRES_NEW)
    public void testTransactional1(ApiAccessLog apiAccessLog) {
        logMapper.insertSelective(apiAccessLog);
        throw new RuntimeException("123");
    }
}
