package com.bilalalp.cxflogger.config;

import com.bilalalp.cxflogger.exception.NoDataSourceException;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.bilalalp.cxflogger"})
@PropertySource(value = {"classpath:application.properties","classpath:db/${db:h2}.properties"})
public class CxfLoggerApplicationConfig {

    private boolean useDatasource = false;

    private DataSource dataSource;

    private Map<String,Object> cxfLoggerConfigurationMap;

    @Autowired
    private CxfLoggerConfigurationWrapper cxfLoggerConfigurationWrapper;

    @Autowired
    private Environment environment;

    @PostConstruct
    public void post(){
        cxfLoggerConfigurationWrapper.setCxfLoggerConfigurationMap(cxfLoggerConfigurationMap);
    }

    @Bean(name = "cxfLoggerEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean localEntityManagerFactory() throws PropertyVetoException {

        final LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSource());
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        localContainerEntityManagerFactoryBean.setJpaDialect(new HibernateJpaDialect());
        localContainerEntityManagerFactoryBean.setPackagesToScan("com.bilalalp.cxflogger.entity");
        localContainerEntityManagerFactoryBean.setJpaPropertyMap(getJpaPropertyMap());
        return localContainerEntityManagerFactoryBean;
    }

    @Bean(name = "cxfLoggerJpaTransactionManager")
    public JpaTransactionManager jpaTransactionManager() throws PropertyVetoException {

        final JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(localEntityManagerFactory().getObject());
        return jpaTransactionManager;
    }

    @Bean(name = "cxfLoggerDatasource")
    public DataSource dataSource() throws PropertyVetoException {

        if (useDatasource) {
            if (dataSource == null) {
                throw new NoDataSourceException("DataSource must be given!");
            } else {
                return dataSource;
            }
        } else {
            return getDataSource();
        }
    }

    private DataSource getDataSource() throws PropertyVetoException {

        final ComboPooledDataSource basicDataSource = new ComboPooledDataSource();

        basicDataSource.setJdbcUrl(cxfLoggerConfigurationWrapper.getJdbcUrl());
        basicDataSource.setPassword(cxfLoggerConfigurationWrapper.getJdbcPassword());
        basicDataSource.setUser(cxfLoggerConfigurationWrapper.getJdbcUserName());
        basicDataSource.setDriverClass(cxfLoggerConfigurationWrapper.getDriverClassName());

        basicDataSource.setMaxPoolSize(cxfLoggerConfigurationWrapper.getMaxPoolSize());
        basicDataSource.setInitialPoolSize(cxfLoggerConfigurationWrapper.getInitialPoolSize());
        basicDataSource.setAcquireIncrement(cxfLoggerConfigurationWrapper.getIncrementSize());
        basicDataSource.setIdleConnectionTestPeriod(cxfLoggerConfigurationWrapper.getConnectionTestPeriod());
        basicDataSource.setMaxIdleTime(cxfLoggerConfigurationWrapper.getMaxIdleTime());
        basicDataSource.setUnreturnedConnectionTimeout(cxfLoggerConfigurationWrapper.getUnReturnedTimeOut());
        basicDataSource.setAutoCommitOnClose(cxfLoggerConfigurationWrapper.getAutoCommit());
        basicDataSource.setNumHelperThreads(cxfLoggerConfigurationWrapper.getNumHelperThreads());
        basicDataSource.setMaxStatements(cxfLoggerConfigurationWrapper.getMaxStatements());
        basicDataSource.setMaxStatementsPerConnection(cxfLoggerConfigurationWrapper.getMaxStatementsPerConnections());
        basicDataSource.setDebugUnreturnedConnectionStackTraces(cxfLoggerConfigurationWrapper.getDebugUnreturnedConnectionStackTraces());
        basicDataSource.setMinPoolSize(cxfLoggerConfigurationWrapper.getMinPoolSize());

        return basicDataSource;
    }

    private Map<String, Object> getJpaPropertyMap() {

        final Map<String, Object> jpaPropertyMap = new HashMap<>();
        jpaPropertyMap.put("hibernate.implicit_naming_strategy", new ImplicitNamingStrategyJpaCompliantImpl());
        jpaPropertyMap.put("hibernate.physical_naming_strategy", new PhysicalNamingStrategyStandardImpl());
        jpaPropertyMap.put("hibernate.hbm2ddl.auto", cxfLoggerConfigurationWrapper.getHbm2DllAuto());
        jpaPropertyMap.put("hibernate.showSql", cxfLoggerConfigurationWrapper.getShowSql());
        jpaPropertyMap.put("hibernate.formatSql", cxfLoggerConfigurationWrapper.getFormatSql());
        jpaPropertyMap.put("hibernate.generate_statistics", cxfLoggerConfigurationWrapper.getGenerateStatistics());
        jpaPropertyMap.put("hibernate.max_fetch_depth", cxfLoggerConfigurationWrapper.getMaxFetchSize());
        jpaPropertyMap.put("hibernate.default_batch_fetch_size", cxfLoggerConfigurationWrapper.getDefaultBatchFetchSize());
        jpaPropertyMap.put("hibernate.jdbc.batch_size", cxfLoggerConfigurationWrapper.getBatchSize());
        jpaPropertyMap.put("hibernate.default_schema", cxfLoggerConfigurationWrapper.getDefaultSchema());
        return jpaPropertyMap;
    }
}