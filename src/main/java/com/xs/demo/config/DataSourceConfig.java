package com.xs.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.xs.demo.service.transaction.ManyDataSourceTransactionManager;
import com.yxp.common.db.manyDatasource.ManyDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * DataSource config
 * Created by Xs on 2017/11/28.
 */
@Configuration
@MapperScan(value = "com.xs.demo.dao.portal", sqlSessionFactoryRef = "apiPortalSqlSessionFactory", sqlSessionTemplateRef = "apiSqlSessionTemplate")
public class DataSourceConfig {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.poolConfig.initialSize}")
    private int initialSize;
    @Value("${spring.datasource.poolConfig.minIdle}")
    private int minIdle;
    @Value("${spring.datasource.poolConfig.maxActive}")
    private int maxActive;
    @Value("${spring.datasource.poolConfig.maxWait}")
    private int maxWait;

    @Value("${spring.datasource.write.url}")
    private String writeUrl;
    @Value("${spring.datasource.write.username}")
    private String writeUsername;
    @Value("${spring.datasource.write.password}")
    private String writePassword;

    @Value("${spring.datasource.read.url}")
    private String readUrl;
    @Value("${spring.datasource.read.username}")
    private String readUsername;
    @Value("${spring.datasource.read.password}")
    private String readPassword;

    @Bean
    public DataSource apiWriteDataSource() {
        DataSource writeDataSource = createDataSource(writeUrl, writeUsername, writePassword);
        logger.info("init apiWriteDataSource complate ...... " + writeDataSource);
        return writeDataSource;
    }

    @Bean
    public DataSource apiReadDataSource() {
        DataSource readDataSource = createDataSource(readUrl, readUsername, readPassword);
        logger.info("init apiReadDataSource complate ...... " + readDataSource);
        return readDataSource;
    }

    @Bean
    public DataSource apiPortalDataSource(DataSource apiWriteDataSource, DataSource apiReadDataSource) {
        Map<Object, Object> map = new HashMap<>();
        map.put("master", apiWriteDataSource);
        map.put("slave", apiReadDataSource);

        ManyDataSource manyDataSource = new ManyDataSource();
        manyDataSource.setTargetDataSources(map);
        manyDataSource.setDefaultTargetDataSource(apiWriteDataSource);
        logger.info("init manyDataSource complate ...... " + manyDataSource);
        return manyDataSource;
    }

    private DataSource createDataSource(String url, String username, String password) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);
        dataSource.setInitialSize(initialSize);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxActive(maxActive);
        dataSource.setMaxWait(maxWait);

        return dataSource;
    }

    

    @Bean
    public SqlSessionFactory apiPortalSqlSessionFactory(DataSource apiPortalDataSource, MybatisProperties mybatisProperties) {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(apiPortalDataSource);
        bean.setConfigurationProperties(mybatisProperties.getConfigurationProperties());
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        try {
            bean.setMapperLocations(resolver.getResources("classpath:mapper/portal/*.xml"));
            return bean.getObject();
        } catch (Exception e) {
            logger.error("初始化mybatissqlSessesionFactoryBean失败: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Bean
    public SqlSessionTemplate apiSqlSessionTemplate(SqlSessionFactory apiPortalSqlSessionFactory) {
        return new SqlSessionTemplate(apiPortalSqlSessionFactory);
    }


    @Bean
    public PlatformTransactionManager masterTransactionManager(DataSource apiPortalDataSource) {
        return new ManyDataSourceTransactionManager(apiPortalDataSource);
    }
}