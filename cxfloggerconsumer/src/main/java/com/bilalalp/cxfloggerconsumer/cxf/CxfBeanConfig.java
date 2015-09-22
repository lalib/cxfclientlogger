package com.bilalalp.cxfloggerconsumer.cxf;

import com.bilalalp.cxflogger.config.CxfLoggerApplicationConfig;
import com.bilalalp.cxflogger.interceptor.WsLoggingInInterceptor;
import com.bilalalp.cxflogger.interceptor.WsLoggingOutInterceptor;
import com.w3schools.webservices.TempConvertSoap;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@Configuration
@Import(value = {CxfLoggerApplicationConfig.class})
public class CxfBeanConfig {

    @Autowired
    private WsLoggingInInterceptor wsLoggingInInterceptor;

    @Autowired
    private WsLoggingOutInterceptor wsLoggingOutInterceptor;

    @Bean(name = "cxf")
        public Bus bus() {

        final SpringBus springBus = new SpringBus();
        springBus.getInInterceptors().add(wsLoggingInInterceptor);
        springBus.getOutInterceptors().add(wsLoggingOutInterceptor);
        return springBus;
    }

    @Bean(name = "tempConvertSoap")
    public TempConvertSoap endpoint() {

        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        jaxWsProxyFactoryBean.setServiceClass(TempConvertSoap.class);
        jaxWsProxyFactoryBean.setAddress("http://www.w3schools.com/webservices/tempconvert.asmx");
        jaxWsProxyFactoryBean.setBus(bus());
        return (TempConvertSoap) jaxWsProxyFactoryBean.create();
    }
}
