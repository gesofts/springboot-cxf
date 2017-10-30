package cn.com.sparknet.cityToGovService.service.imp;

import cn.com.sparkent.util.WsUtil;
import cn.com.sparknet.cityToGovService.service.CityToGovService;
import org.springframework.beans.factory.annotation.Value;

import javax.jws.WebService;

/**
 * Created by WCL on 2017/10/30.
 */
@WebService(endpointInterface="cn.com.sparknet.cityToGovService.service.CityToGovService", serviceName="CityToGovService" )
public class CityToGovMailImpl implements CityToGovService
{

    @Value("${ws.url}")
    private String url;

    public String CityToGovMail(String msg) {
        System.out.println("sayHi called");
        String rs = WsUtil.HttpClientUtil(getXML(msg), url);
        System.out.println(rs);
        return rs;
    }

    public static  String getXML(String param){
        String soapXML= "<?xml version=\"1.0\" encoding=\"utf-8\"?><soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:ser='http://service.cityToGovService.sparknet.com.cn/'><soapenv:Header/><soapenv:Body><ser:CityToGovMail><arg0>"+param+"</arg0></ser:CityToGovMail></soapenv:Body></soapenv:Envelope>";
        return soapXML;
    }


}
