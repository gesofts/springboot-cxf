package com.leftso.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * Created by WCL on 2017/10/30.
 */
@WebService
@SOAPBinding(style= SOAPBinding.Style.RPC, use= SOAPBinding.Use.LITERAL)/* 注意 ：如果要动态调用一定要注释掉这句代码*/
public interface HelloWorld {
    @WebMethod
    String sayHi(@WebParam(name="text") String text);
}
