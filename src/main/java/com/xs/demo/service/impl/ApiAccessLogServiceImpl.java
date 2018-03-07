package com.xs.demo.service.impl;

import com.xs.demo.dao.ApiAccessLogMapper;
import com.xs.demo.service.ApiAccessLogService;
import com.yxp.common.db.entity.apiportal.ApiAccessLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.time.LocalDate;

/**
 * ApiAccessLogServiceImpl
 * Created by Xs on 2017/11/30.
 */
@Service
@Transactional("apiWriteTransactionManager")
public class ApiAccessLogServiceImpl implements ApiAccessLogService {
    @Autowired
    private ApiAccessLogMapper logMapper;
    @Autowired
    private PlatformTransactionManager apiReadTransactionManager;
    @Autowired
    private DataSource apiReadDataSource;
    @Autowired
    private DataSource apiWriteDataSource;

    @Override
    public ApiAccessLog insert(ApiAccessLog record) {
        logMapper.insertSelective(record);
        return record;
    }

    @Override
    public long statisticalTodayFlow(String appKey, String resUri) {
        return logMapper.statisticalTodayFlow(appKey, resUri, LocalDate.now());
    }

    @Override
    @Transactional(value = "apiWriteTransactionManager", propagation = Propagation.REQUIRES_NEW)
    public void testTransactional(ApiAccessLog apiAccessLog) {
        logMapper.insertSelective(apiAccessLog);

        Connection connection = DataSourceUtils.getConnection(apiReadDataSource);
        Connection connection1 = DataSourceUtils.getConnection(apiWriteDataSource);
        throw new RuntimeException("123");
    }

}
