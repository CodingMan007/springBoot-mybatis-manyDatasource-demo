package com.xs.demo.service.transaction;

import com.yxp.common.db.manyDatasource.ManyDataSource;
import com.yxp.common.db.manyDatasource.ManyDataSourceSwitch;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.DelegatingTransactionAttribute;

import javax.sql.DataSource;

/**
 * 多数据源事务管理
 * 说明：此类是为了解决跨库使用多数据源切换时，开启事务错误的问题。***注意：此类并不是为了实现多数据源的跨库事务而实现的。
 * *** 多数据源切换时，获取到的dataSource为默认的dataSource 而非事务实际所需的dataSource
 * 推荐：随着微服务的推进，应当不再使用ManyDataSource做多数据源切换，及事务管理，而使用服务调用的方法。
 * <p>
 * <p>
 * 使用方法：
 * 定义transactionManager ：为该定义的transactionManager声明名称，与多数据源切换的Key值相同
 * ****   例：
 * ****       @Bean
 * ****       public PlatformTransactionManager master(DataSource dataSource) {
 * ****            return new ManyDataSourceTransactionManager(dataSource);
 * ****       }
 * ****       上例定义的transactionManager在Mapper中使用的注解相同: @DataSource("master")
 * <p>
 * 当调用的service使用的数据源改变时，需要新建一个事务，参考spring本身的事务传播机制。
 * <p>
 * Created by XuSheng on 2018/3/10.
 */
public class ManyDataSourceTransactionManager extends DataSourceTransactionManager {
    private static final String TRANSACTION_SUFFIX = "TransactionManager";

    public ManyDataSourceTransactionManager(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected void doBegin(Object transaction, TransactionDefinition definition) {
        try {
            if (ManyDataSource.class.equals(super.getDataSource().getClass())) {
                String transactionName;
                if (definition instanceof DelegatingTransactionAttribute) {
                    transactionName = ((DelegatingTransactionAttribute) definition).getQualifier();
                } else {
                    transactionName = ((DefaultTransactionAttribute) definition).getQualifier();
                }
                
                transactionName = transactionName.substring(0, transactionName.indexOf(TRANSACTION_SUFFIX));
                ManyDataSourceSwitch.setDataSourceType(transactionName);
                super.doBegin(transaction, definition);
                ManyDataSourceSwitch.clearDataSourceType();
            } else {
                super.doBegin(transaction, definition);
            }
        } catch (Exception e) {
            super.doBegin(transaction, definition);
        }
    }
}
