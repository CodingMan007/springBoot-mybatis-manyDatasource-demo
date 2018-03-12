package com.xs.demo.dao.notice;

import com.yxp.common.db.entity.apiportal.ApiAccessLog;
import com.yxp.common.db.manyDatasource.DataSource;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;

public interface NoticeAccessLogMapper {
    @DataSource("master")
    int insertSelective(ApiAccessLog record);

    @DataSource("slave")
    ApiAccessLog selectByPrimaryKey(Long id);

    @DataSource("master")
    int updateByPrimaryKeySelective(ApiAccessLog record);

    /**
     * 统计指定日期的某应用、某资源访问次数
     *
     * @param appKey appKey
     * @param resUri resUri
     * @param date   date
     */
    @DataSource("slave")
    long statisticalTodayFlow(@Param("appKey") String appKey, @Param("resUri") String resUri, @Param("date") LocalDate date);
}