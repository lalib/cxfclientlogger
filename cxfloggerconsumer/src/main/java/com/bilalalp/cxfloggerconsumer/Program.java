package com.bilalalp.cxfloggerconsumer;

import com.bilalalp.cxfloggerconsumer.cxf.CxfBeanConfig;
import com.w3schools.webservices.TempConvertSoap;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.CXFBusFactory;
import org.apache.cxf.bus.spring.SpringBus;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Program {

    public static void main(String[] args) {

//        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(CxfBeanConfig.class);
//        final TempConvertSoap tempConvertSoap = annotationConfigApplicationContext.getBean("tempConvertSoap", TempConvertSoap.class);
//        final String s = tempConvertSoap.celsiusToFahrenheit("100");
//        System.out.println(s);

        final ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
//        for(int i =0;i<1000;i++) {
            final TempConvertSoap globalWeatherClient = classPathXmlApplicationContext.getBean("globalWeatherClient", TempConvertSoap.class);
            final String s = globalWeatherClient.celsiusToFahrenheit("100");
            System.out.println(s);
//        }
    }
}