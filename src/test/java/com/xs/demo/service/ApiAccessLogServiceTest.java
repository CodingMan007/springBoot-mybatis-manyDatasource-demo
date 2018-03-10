package com.xs.demo.service;

import com.xs.demo.CleanSpringbootProjectApplicationTests;
import com.yxp.common.db.entity.apiportal.ApiAccessLog;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * ApiAccessLogServiceTest
 * Created by Xs on 2017/12/1.
 */
public class ApiAccessLogServiceTest extends CleanSpringbootProjectApplicationTests {

    @Autowired
    private ApiAccessLogService logService;

    @Test
    public void testStatisticalTodayFlow() {
        long todayFlow = logService.statisticalTodayFlow("youxinpai", "/test/hello");
        System.out.println("今日访问资源次数：" + todayFlow);
    }


    @Test
    public void testTransactional() {
        ApiAccessLog apiAccessLog = new ApiAccessLog();
//        try {
        apiAccessLog.setAppKey("test");
        apiAccessLog.setAccessTime(new Date());
        apiAccessLog.setResourceUri("/testTransactional");
//            logService.testTransactional(apiAccessLog);
//        } finally {
        logService.testTransactional(apiAccessLog);
//        }
    }

}
