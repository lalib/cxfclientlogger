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
<bean id="cxfLoggerConfig" class="com.bilalalp.cxflogger.config.CxfLoggerApplicationConfig">
    <property name="cxfLoggerConfigurationMap">
        <map>
            <entry key="cxflogger.db.jdbc.url" value="jdbc:postgresql://localhost\:5432/mydb?characterEncoding\=UTF-8"/>
            <entry key="cxflogger.db.jdbc.userName" value="postgres"/>
            <entry key="cxflogger.db.jdbc.password" value="root"/>
            <entry key="cxflogger.db.jdbc.driverClassName" value="org.postgresql.Driver"/>
        </map>
    </property>
</bean>```

You can also give hibernate parameters in the same map. Here is the usage:

```xml
<bean id="cxfLoggerConfig" class="com.bilalalp.cxflogger.config.CxfLoggerApplicationConfig">
    <property name="cxfLoggerConfigurationMap">
        <map>
            <entry key="cxflogger.db.jdbc.url" value="jdbc:postgresql://localhost\:5432/mydb?characterEncoding\=UTF-8"/>
            <entry key="cxflogger.db.jdbc.userName" value="postgres"/>
            <entry key="cxflogger.db.jdbc.password" value="root"/>
            <entry key="cxflogger.db.jdbc.driverClassName" value="org.postgresql.Driver"/>
        </map>
    </property>
</bean>```
