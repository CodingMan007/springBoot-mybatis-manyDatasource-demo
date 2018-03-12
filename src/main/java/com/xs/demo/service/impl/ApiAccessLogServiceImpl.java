package com.xs.demo.service.impl;

import com.xs.demo.dao.portal.ApiAccessLogMapper;
import com.xs.demo.service.ApiAccessLogService;
import com.xs.demo.service.ApiAccessLogService1;
import com.yxp.common.db.entity.apiportal.ApiAccessLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * ApiAccessLogServiceImpl
 * Created by Xs on 2017/11/30.
 */
@Service
public class ApiAccessLogServiceImpl implements ApiAccessLogService {
    @Autowired
    private ApiAccessLogMapper logMapper;
    @Autowired
    private ApiAccessLogService1 apiAccessLogServiceImpl1;

    @Override
    public ApiAccessLog insert(ApiAccessLog record) {
        logMapper.insertSelective(record);
        System.err.println(1);
        return record;
    }

    @Override
    public long statisticalTodayFlow(String appKey, String resUri) {
        return logMapper.statisticalTodayFlow(appKey, resUri, LocalDate.now());
    }

    @Override
    @Transactional("masterTransactionManager")
    public void testTransactional(ApiAccessLog apiAccessLog) {
        logMapper.insertSelective(apiAccessLog);
        try {
            apiAccessLogServiceImpl1.testTransactional1(apiAccessLog);
        } catch (Exception e) {
            System.err.println(e);
        }
//        throw new RuntimeException("123");
    }


}
