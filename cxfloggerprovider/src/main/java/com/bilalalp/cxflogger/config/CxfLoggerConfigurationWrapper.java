package com.bilalalp.cxflogger.config;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Map;

@Setter
@Getter
@Service
public class CxfLoggerConfigurationWrapper {

    @Autowired
    private Environment environment;

    private Map<String, Object> cxfLoggerConfigurationMap;

    public String getDriverClassName() {
        return getProperty("cxflogger.db.jdbc.driverClassName", String.class);
    }

    public String getDefaultSchema() {
        return getProperty("cxflogger.hibernate.default_schema", String.class);
    }

    public Integer getBatchSize() {
        return getProperty("cxflogger.hibernate.jdbc.batch_size", Integer.class);
    }

    public Integer getDefaultBatchFetchSize() {
        return getProperty("cxflogger.hibernate.default_batch_fetch_size", Integer.class);
    }

    public Integer getMaxFetchSize() {
        return getProperty("cxflogger.hibernate.max_fetch_depth", Integer.class);
    }

    public Boolean getGenerateStatistics() {
        return getProperty("cxflogger.hibernate.generate_statistics", Boolean.class);
    }

    public Boolean getFormatSql() {
        return getProperty("cxflogger.hibernate.formatSql", Boolean.class);
    }

    public Boolean getShowSql() {
        return getProperty("cxflogger.hibernate.showSql", Boolean.class);
    }

    public String getHbm2DllAuto() {
        return getProperty("cxflogger.hibernate.hbm2ddl.auto", String.class);
    }

    public Integer getMinPoolSize() {
        return getProperty("cxflogger.db.pool.minPoolSize", Integer.class);
    }

    public Boolean getDebugUnreturnedConnectionStackTraces() {
        return getProperty("cxflogger.db.pool.debugUnreturnedConnectionStackTraces", Boolean.class);
    }

    public Integer getMaxStatementsPerConnections() {
        return getProperty("cxflogger.db.pool.maxStatementsPerConnection", Integer.class);
    }

    public Integer getMaxStatements() {
        return getProperty("cxflogger.db.pool.maxStatements", Integer.class);
    }

    public Integer getNumHelperThreads() {
        return getProperty("cxflogger.db.pool.numHelperThreads", Integer.class);
    }

    public Boolean getAutoCommit() {
        return getProperty("cxflogger.db.pool.autoCommit", Boolean.class);
    }

    public Integer getUnReturnedTimeOut() {
        return getProperty("cxflogger.db.pool.unreturnedTimeout", Integer.class);
    }

    public Integer getMaxIdleTime() {
        return getProperty("cxflogger.db.pool.maxIdleTime", Integer.class);
    }

    public Integer getConnectionTestPeriod() {
        return getProperty("cxflogger.db.pool.connectionTestPeriod", Integer.class);
    }

    public Integer getIncrementSize() {
        return getProperty("cxflogger.db.pool.incrementSize", Integer.class);
    }

    public Integer getInitialPoolSize() {
        return getProperty("cxflogger.db.pool.initialPoolSize", Integer.class);
    }

    public Integer getMaxPoolSize() {
        return getProperty("cxflogger.db.pool.maxPoolSize", Integer.class);
    }

    public String getJdbcUserName() {
        return getProperty("cxflogger.db.jdbc.userName", String.class);
    }

    public String getJdbcPassword() {
        return getProperty("cxflogger.db.jdbc.password", String.class);
    }

    public String getJdbcUrl() {
        return getProperty("cxflogger.db.jdbc.url", String.class);
    }

    public <T> T getProperty(final String key, Class<T> aClass) {

        if (MapUtils.isNotEmpty(cxfLoggerConfigurationMap) && cxfLoggerConfigurationMap.containsKey(key)) {
            return (T) cxfLoggerConfigurationMap.get(key);
        } else {
            return (T) environment.getProperty(key, aClass);
        }
    }
}
