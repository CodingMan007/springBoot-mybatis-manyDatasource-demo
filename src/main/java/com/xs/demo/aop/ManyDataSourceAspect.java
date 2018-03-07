package com.xs.demo.aop;

import com.yxp.common.db.manyDatasource.BaseManyDataSourceAspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 数据库读写分离切面配置
 * Created by Xs on 2017/12/7.
 */
@Aspect
@Component
public class ManyDataSourceAspect extends BaseManyDataSourceAspect {

    private static Logger logger = LoggerFactory.getLogger(ManyDataSourceAspect.class);

    @Pointcut("execution ( * com.xs.demo.dao.*.*(..))")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void before(JoinPoint point) {
        super.before(point);
    }

    @After("pointcut()")
    public void after(JoinPoint point) {
        super.after(point);
    }
}
