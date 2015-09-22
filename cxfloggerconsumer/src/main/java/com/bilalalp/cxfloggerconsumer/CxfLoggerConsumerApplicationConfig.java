package com.bilalalp.cxfloggerconsumer;

import com.mchange.v2.c3p0.ComboPooledDataSource;
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

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.Map;

@Setter
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.bilalalp.cxfloggerconsumer"})
@PropertySource(value = {"classpath:app.properties"})
public class CxfLoggerConsumerApplicationConfig {

    private boolean useDatasource = false;

    private boolean useEntityManager = false;

    @Autowired
    private Environment environment;

    @Bean(name = "cxfLoggerConsumerEntityManager")
    public EntityManager entityManager() throws PropertyVetoException {
        return localEntityManagerFactory().getObject().createEntityManager();
    }

    @Bean(name = "cxfLoggerConsumerEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean localEntityManagerFactory() throws PropertyVetoException {

        final LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSource1());
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        localContainerEntityManagerFactoryBean.setJpaDialect(new HibernateJpaDialect());
        localContainerEntityManagerFactoryBean.setPackagesToScan("com.bilalalp.cxfloggerconsumer.entity");
        localContainerEntityManagerFactoryBean.setJpaPropertyMap(getJpaPropertyMap());
        return localContainerEntityManagerFactoryBean;
    }

    @Bean(name = "cxfLoggerConsumerJpaTransactionManager")
    public JpaTransactionManager jpaTransactionManager1() throws PropertyVetoException {

        final JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(localEntityManagerFactory().getObject());
        return jpaTransactionManager;
    }

    @Bean(name = "cxfLoggerConsumerDatasource")
    public DataSource dataSource1() throws PropertyVetoException {

        final ComboPooledDataSource basicDataSource = new ComboPooledDataSource();
        basicDataSource.setJdbcUrl(environment.getProperty("cxfLoggerConsumer.db.jdbc.url"));
        basicDataSource.setPassword(environment.getProperty("cxfLoggerConsumer.db.jdbc.password"));
        basicDataSource.setUser(environment.getProperty("cxfLoggerConsumer.db.jdbc.userName"));
        basicDataSource.setDriverClass(environment.getProperty("cxfLoggerConsumer.db.jdbc.driverClassName"));
        basicDataSource.setMaxPoolSize(environment.getProperty("cxfLoggerConsumer.db.pool.maxPoolSize", Integer.class));
        basicDataSource.setInitialPoolSize(environment.getProperty("cxfLoggerConsumer.db.pool.initialPoolSize", Integer.class));
        basicDataSource.setAcquireIncrement(environment.getProperty("cxfLoggerConsumer.db.pool.incrementSize", Integer.class));
        basicDataSource.setIdleConnectionTestPeriod(environment.getProperty("cxfLoggerConsumer.db.pool.connectionTestPeriod", Integer.class));
        basicDataSource.setMaxIdleTime(environment.getProperty("cxfLoggerConsumer.db.pool.maxIdleTime", Integer.class));
        basicDataSource.setUnreturnedConnectionTimeout(environment.getProperty("cxfLoggerConsumer.db.pool.unreturnedTimeout", Integer.class));
        basicDataSource.setAutoCommitOnClose(environment.getProperty("cxfLoggerConsumer.db.pool.autoCommit", Boolean.class));
        basicDataSource.setNumHelperThreads(environment.getProperty("cxfLoggerConsumer.db.pool.numHelperThreads", Integer.class));
        basicDataSource.setMaxStatements(environment.getProperty("cxfLoggerConsumer.db.pool.maxStatements", Integer.class));
        basicDataSource.setMaxStatementsPerConnection(environment.getProperty("cxfLoggerConsumer.db.pool.maxStatementsPerConnection", Integer.class));
        basicDataSource.setDebugUnreturnedConnectionStackTraces(environment.getProperty("cxfLoggerConsumer.db.pool.debugUnreturnedConnectionStackTraces", Boolean.class));
        basicDataSource.setMinPoolSize(environment.getProperty("cxfLoggerConsumer.db.pool.minPoolSize", Integer.class));
        return basicDataSource;
    }

    private Map<String, Object> getJpaPropertyMap() {

        final Map<String, Object> jpaPropertyMap = new HashMap<>();
        jpaPropertyMap.put("hibernate.implicit_naming_strategy", new ImplicitNamingStrategyJpaCompliantImpl());
        jpaPropertyMap.put("hibernate.physical_naming_strategy", new PhysicalNamingStrategyStandardImpl());
        jpaPropertyMap.put("hibernate.hbm2ddl.auto", environment.getProperty("cxfLoggerConsumer.hibernate.hbm2ddl.auto", String.class));
        jpaPropertyMap.put("hibernate.showSql", environment.getProperty("cxfLoggerConsumer.hibernate.showSql", Boolean.class));
        jpaPropertyMap.put("hibernate.formatSql", environment.getProperty("cxfLoggerConsumer.hibernate.formatSql", Boolean.class));
        jpaPropertyMap.put("hibernate.generate_statistics", environment.getProperty("cxfLoggerConsumer.hibernate.generate_statistics", Boolean.class));
        jpaPropertyMap.put("hibernate.max_fetch_depth", environment.getProperty("cxfLoggerConsumer.hibernate.max_fetch_depth", Integer.class));
        jpaPropertyMap.put("hibernate.default_batch_fetch_size", environment.getProperty("cxfLoggerConsumer.hibernate.default_batch_fetch_size", Integer.class));
        jpaPropertyMap.put("hibernate.jdbc.batch_size", environment.getProperty("cxfLoggerConsumer.hibernate.jdbc.batch_size", Integer.class));
        jpaPropertyMap.put("hibernate.default_schema", environment.getProperty("cxfLoggerConsumer.hibernate.default_schema", String.class));
        return jpaPropertyMap;
    }
}