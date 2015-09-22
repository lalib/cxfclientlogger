[![Build Status](https://travis-ci.org/lalib/cxfclientlogger.svg?branch=master)](https://travis-ci.org/lalib/cxfclientlogger)

Documentation
=============

Overview
--------

Logging web services is a general issue for development world. This project aims to solve this issue in a different way.
Many people write their own logging mechanism to log web services via interceptors.
This project gets done whole dirty jobs instead of you.
Our interceptors will get the job done. Web service logs will be saved to the database whether in your database or in another database. 
It is your choise.


I've used some codes from  [Bahadır Akın](http://www.bahadirakin.com/) 's projects. Thanks to him.

Requirements
--------

| Library   | Version |
|-----------|---------|
| Java      | 1.7+    |
| Maven     | 3+      |
| Spring    | 4.2+    |
| Cxf       | 3.1+    |
| Hibernate | 5+      |


Build
--------

To build the project, run mvn clean install, this will put jar file in `cxfloggerprovider/target/cxfloggerprovider-1.0.jar

Usage
--------

There are two usage scenarios.

1. Using a different datasource
2. Using the same datasource

#####1. Using a different datasource

The configuration class is `com.bilalalp.cxflogger.config.CxfLoggerApplicationConfig`

```xml
<bean id="cxfLoggerConfig" class="com.bilalalp.cxflogger.config.CxfLoggerApplicationConfig"/>
```

This will use default H2 database. You can give your own database connection informations. Here is the usage:

```xml
<context:annotation-config/>
<bean id="cxfLoggerConfig" class="com.bilalalp.cxflogger.config.CxfLoggerApplicationConfig">
    <property name="cxfLoggerConfigurationMap">
        <map>
            <entry key="cxflogger.db.jdbc.url" value="jdbc:postgresql://localhost\:5432/mydb?characterEncoding\=UTF-8"/>
            <entry key="cxflogger.db.jdbc.userName" value="postgres"/>
            <entry key="cxflogger.db.jdbc.password" value="root"/>
            <entry key="cxflogger.db.jdbc.driverClassName" value="org.postgresql.Driver"/>
        </map>
    </property>
</bean>
```

You can also give hibernate parameters in the same map. Below configurations are also default parameters. If you don't give any parameters, these parameters will be used. Here is the usage:

```xml
<context:annotation-config/>
<bean id="cxfLoggerConfig" class="com.bilalalp.cxflogger.config.CxfLoggerApplicationConfig">
    <property name="cxfLoggerConfigurationMap">
        <map>
            <entry key="cxflogger.db.jdbc.url" value="jdbc:h2:~/h2CxfLoggerDb;AUTO_SERVER\=TRUE"/>
            <entry key="cxflogger.db.jdbc.userName" value="postgres"/>
            <entry key="cxflogger.db.jdbc.password" value="root"/>
            <entry key="cxflogger.db.jdbc.driverClassName" value="200"/>
            <entry key="cxflogger.db.pool.maxPoolSize" value="org.postgresql.Driver"/>
            <entry key="cxflogger.db.pool.initialPoolSize" value="50"/>
            <entry key="cxflogger.db.pool.minPoolSize" value="50"/>
            <entry key="cxflogger.db.pool.incrementSize" value="2"/>
            <entry key="cxflogger.db.pool.connectionTestPeriod" value="3600"/>
            <entry key="cxflogger.db.pool.maxIdleTime" value="21600"/>
            <entry key="cxflogger.db.pool.unreturnedTimeout" value="3600"/>
            <entry key="cxflogger.db.pool.autoCommit" value="false"/>
            <entry key="cxflogger.db.pool.numHelperThreads" value="6"/>
            <entry key="cxflogger.db.pool.maxStatements" value="0"/>
            <entry key="cxflogger.db.pool.maxStatementsPerConnection" value="0"/>
            <entry key="cxflogger.db.pool.debugUnreturnedConnectionStackTraces" value="false"/>
            <entry key="cxflogger.hibernate.hbm2ddl.auto" value="update"/>
            <entry key="cxflogger.hibernate.showSql" value="false"/>
            <entry key="cxflogger.hibernate.formatSql" value="false"/>
            <entry key="cxflogger.hibernate.generate_statistics" value="false"/>
            <entry key="cxflogger.hibernate.max_fetch_depth" value="3"/>
            <entry key="cxflogger.hibernate.default_batch_fetch_size" value="16"/>
            <entry key="cxflogger.hibernate.jdbc.batch_size" value="20"/>
            <entry key="cxflogger.db.jdbc.driverClassName" value="org.postgresql.Driver"/>
        </map>
    </property>
</bean>
```

#####2. Using the same datasource

If you'd like to use the same datasource, here is the usage:

```xml
<context:annotation-config/>
<bean id="cxfLoggerConfig" class="com.bilalalp.cxflogger.config.CxfLoggerApplicationConfig">
    <property name="useDatasource" value="true"/>
    <property name="dataSource" ref="cxfLoggerConsumerDatasource"/>
</bean>
```

This will use your datasource directly, If you don't have a table named WsLog, it will be created automaticly.

After Logging configurations, you just need to add interceptors to your bus.

Here is the sample:

```xml
<bean id="abstractLoggingInterceptor" abstract="true">
    <property name="prettyLogging" value="true"/>
</bean>

<bean id="loggingInInterceptor" class="org.apache.cxf.interceptor.LoggingInInterceptor"
      parent="abstractLoggingInterceptor"/>
<bean id="loggingOutInterceptor" class="org.apache.cxf.interceptor.LoggingOutInterceptor"
      parent="abstractLoggingInterceptor"/>

<cxf:bus>
    <cxf:inInterceptors>
        <ref bean="loggingInInterceptor"/>
        <ref bean="wsLoggingInInterceptor"/>
    </cxf:inInterceptors>
    <cxf:outInterceptors>
        <ref bean="loggingOutInterceptor"/>
        <ref bean="wsLoggingOutInterceptor"/>
    </cxf:outInterceptors>
    <cxf:inFaultInterceptors>
        <ref bean="loggingInInterceptor"/>
        <ref bean="wsLoggingInInterceptor"/>
    </cxf:inFaultInterceptors>
    <cxf:outFaultInterceptors>
        <ref bean="loggingOutInterceptor"/>
        <ref bean="wsLoggingOutInterceptor"/>
    </cxf:outFaultInterceptors>
</cxf:bus>
```

This is it. This will store logs in wslog table.

The full example is cxfloggerconsumer project. You can check it out and run it. It uses w3schools test web services.

It has its own xml configurations.
