package com.leftso.config;

import cn.com.sparknet.cityToGovService.service.CityToGovService;
import cn.com.sparknet.cityToGovService.service.imp.CityToGovMailImpl;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by WCL on 2017/10/30.
 */
@Configuration
public class SomeApiV1Config {

    @Bean
    public SpringBus springBusV1() {
        SpringBus bus = new SpringBus();
        bus.setId("v1");
        return bus;
    }

    @Bean
    public ServletRegistrationBean v1Servlet() {
        CXFServlet cxfServlet = new CXFServlet();
        cxfServlet.setBus(springBusV1());

        ServletRegistrationBean servletBean = new ServletRegistrationBean(cxfServlet, "/api/v1/*");
        servletBean.setName("v1");
        return servletBean;
    }

    @Bean
    public EndpointImpl getOrderV1(CityToGovService cityToGovService) {
        EndpointImpl endpoint = new EndpointImpl(springBusV1(), cityToGovService);
        endpoint.publish("/orders/get");
        return endpoint;
    }

    @Bean
    public EndpointImpl createOrderV1(CityToGovService cityToGovService) {
        EndpointImpl endpoint = new EndpointImpl(springBusV1(), cityToGovService);
        endpoint.publish("/orders/create");
        return endpoint;
    }

    @Bean
    public CityToGovService cityToGovService()
    {
        return new CityToGovMailImpl();
    }

}
