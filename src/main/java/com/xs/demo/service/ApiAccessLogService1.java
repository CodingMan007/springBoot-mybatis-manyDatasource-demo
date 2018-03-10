package com.xs.demo.service;


import com.yxp.common.db.entity.apiportal.ApiAccessLog;

/**
 * ApiAccessLogService
 * Created by Xs on 2017/11/30.
 */
public interface ApiAccessLogService1 {
    ApiAccessLog insert(ApiAccessLog record);

    void testTransactional1(ApiAccessLog apiAccessLog);
}
