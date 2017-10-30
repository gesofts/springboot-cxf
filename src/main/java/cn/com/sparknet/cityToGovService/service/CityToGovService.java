package cn.com.sparknet.cityToGovService.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * Created by WCL on 2017/10/30.
 */
@WebService
@SOAPBinding(style= SOAPBinding.Style.RPC, use= SOAPBinding.Use.LITERAL)
public interface CityToGovService
{
    @WebMethod
    String CityToGovMail(@WebParam(name="arg0") String msg);
}
