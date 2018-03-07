package com.xs.demo.service;


import com.yxp.common.db.entity.apiportal.ApiAccessLog;

/**
 * ApiAccessLogService
 * Created by Xs on 2017/11/30.
 */
public interface ApiAccessLogService {
    ApiAccessLog insert(ApiAccessLog record);

    /**
     * 统计今日当前app访问资源次数
     *
     * @param appKey appKey
     * @param resUri resUri
     * @return 当日资源访问次数
     */
    long statisticalTodayFlow(String appKey, String resUri);
    
    
    void testTransactional(ApiAccessLog apiAccessLog);
}
