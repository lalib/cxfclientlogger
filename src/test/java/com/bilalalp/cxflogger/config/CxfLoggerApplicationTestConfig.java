package com.bilalalp.cxflogger.config;

import lombok.Setter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Setter
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.bilalalp.cxflogger"})
@Import(value = {CxfLoggerApplicationConfig.class})
@PropertySource(value = {"classpath:application-test.properties"})
public class CxfLoggerApplicationTestConfig {


}