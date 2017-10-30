package com.leftso.config;

import cn.com.sparknet.cityToGovService.service.CityToGovService;
import cn.com.sparknet.cityToGovService.service.imp.CityToGovMailImpl;
import com.leftso.webservice.CommonService;
import com.leftso.webservice.HelloWorld;
import com.leftso.webservice.HelloWorldImpl;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class CxfConfig {
	@Autowired
	private Bus bus;

	@Autowired
	CommonService commonService;


	/** JAX-WS **/
	@Bean
	public Endpoint endpoint() {
		EndpointImpl endpoint = new EndpointImpl(bus, commonService);
		endpoint.publish("/CommonService");
		return endpoint;
	}


	@Bean(name = Bus.DEFAULT_BUS_ID)
	public SpringBus springBus() {
		return new SpringBus();
	}
	@Bean
	public HelloWorld anotherService() {
		return new HelloWorldImpl();
	}

	@Bean
	public EndpointImpl anotherEndpoint(HelloWorld anotherService) {
		EndpointImpl endpoint = new EndpointImpl(springBus(), anotherService);
		endpoint.publish("/hello");
		return endpoint;
	}

	@Bean
	public CityToGovService cityToGovService()
	{
		return new CityToGovMailImpl();
	}

	@Bean
	public EndpointImpl cityToGovEndpoint(CityToGovService cityToGovService) {
		EndpointImpl endpoint = new EndpointImpl(springBus(), cityToGovService);
		endpoint.publish("/CityToGovService");
		return endpoint;
	}


	@Bean
	public ServletRegistrationBean dispatcherServlet() {
		return new ServletRegistrationBean(new CXFServlet(), "/onlineGovEMS/services/*");
	}
}
