package com.leftso.webservice;

import javax.jws.WebService;

/**
 * Created by WCL on 2017/10/30.
 */
@WebService(endpointInterface="com.leftso.webservice.HelloWorld", serviceName="HelloWorld" )
public class HelloWorldImpl  implements  HelloWorld{
    public String sayHi(String text) {
        System.out.println("sayHi called");
        return "Hello " + text;
    }
}
