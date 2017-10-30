package com.leftso.client;

import com.leftso.webservice.CommonService;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class CxfClient {
	public static void main(String[] args) {
		//cl1();
		//cl2();
		try {
			//HttpClientUtil(getMsg(), "http://218.2.177.210:8086/onlineGovEMS/services/CityToGovService?wsdl");
			HttpClientUtil(getMsg(), "http://127.0.0.1:8086/onlineGovEMS/services/CityToGovService?wsdl");
			//HttpClientUtil(getCL3("---------SDS-----------"), "http://127.0.0.1:8080/soap/hello");
			//cl2();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static String getMsg() {
		//cl1();
		//cl2();
		try {
			String s = "<?xml version='1.0' encoding='UTF-8'?><RequestMessage><PostInfo><EMS_ORD_NO>5</EMS_ORD_NO><MAIL_NUM></MAIL_NUM><GOV_TB_NAME>A-201</GOV_TB_NAME><POST_TYPE>1</POST_TYPE><NET_TYPE>1</NET_TYPE><BUSS_TYPE>1</BUSS_TYPE><SEND_NAME>t1</SEND_NAME><SEND_PROV>江苏省</SEND_PROV><SEND_CITY>南京市</SEND_CITY><SEND_COUNTRY>鼓楼区</SEND_COUNTRY><SEND_STRECT>江苏南京</SEND_STRECT><SEND_PHONE>15122112233</SEND_PHONE><SEND_CALL></SEND_CALL><RCV_NAME>张小康</RCV_NAME><RCV_PROV>江苏省</RCV_PROV><RCV_CITY>南京</RCV_CITY><RCV_COUNTRY>玄武区</RCV_COUNTRY><RCV_STRECT>江苏</RCV_STRECT><RCV_PHONE>15112344321</RCV_PHONE><RCV_CALL></RCV_CALL><RCV_POSTCODE>221000</RCV_POSTCODE><ITEM></ITEM><CHK_CODE>57A05</CHK_CODE><ISSEND>1</ISSEND><ZWFWZX_CODE>3200AJ</ZWFWZX_CODE><ORGNAME>江苏省安全生产监督管理局</ORGNAME><ORGNAME_ID>014001434</ORGNAME_ID><SENDTIME></SENDTIME></PostInfo><ApplyInfos><ApplyInfo><INTERNAL_NO>3200000000000140014340010038300001201709180001</INTERNAL_NO></ApplyInfo></ApplyInfos></RequestMessage>";
			String emsEncryptInfo = encrypt(s, "GOV17EMS");
			String msg = getXML(emsEncryptInfo);
			return msg;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * 将数据转换成需要的格式
	 * @param param
	 * @return
	 */
	public static  String getXML(String param){
		String soapXML= "<?xml version=\"1.0\" encoding=\"utf-8\"?><soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:ser='http://service.cityToGovService.sparknet.com.cn/'><soapenv:Header/><soapenv:Body><ser:CityToGovMail><arg0>"+param+"</arg0></ser:CityToGovMail></soapenv:Body></soapenv:Envelope>";
		return soapXML;
	}

	public static String encrypt(String message, String key)
			throws Exception {
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
		byte[] tempByte = cipher.doFinal(message.getBytes("UTF-8"));
		return toHexString(tempByte);

	}

	public  static String toHexString(byte b[]) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String plainText = Integer.toHexString(0xff & b[i]);
			if (plainText.length() < 2)
				plainText = "0" + plainText;
			hexString.append(plainText);
		}
		return hexString.toString();
	}


	/**
	 * 方式1.代理类工厂的方式,需要拿到对方的接口
	 */
	public static void cl1() {
		try {
			// 接口地址
			String address = "http://localhost:8080/services/CommonService?wsdl";
			// 代理工厂
			JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
			// 设置代理地址
			jaxWsProxyFactoryBean.setAddress(address);
			// 设置接口类型
			jaxWsProxyFactoryBean.setServiceClass(CommonService.class);
			// 创建一个代理接口实现
			CommonService cs = (CommonService) jaxWsProxyFactoryBean.create();
			// 数据准备
			String userName = "Leftso";
			// 调用代理接口的方法调用并返回结果
			String result = cs.sayHello(userName);
			System.out.println("返回结果:" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 动态调用方式
	 */
	public static void cl2() {
		// 创建动态客户端
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		//Client client = dcf.createClient("http://localhost:8080/services/CommonService?wsdl");
		//Client client = dcf.createClient("http://218.2.177.210:8086/onlineGovEMS/services/CityToGovService?wsdl");
		Client client = dcf.createClient("http://127.0.0.1:8080/soap/hello?wsdl");
		// 需要密码的情况需要加上用户名和密码
		// client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME,
		// PASS_WORD));
		Object[] objects = new Object[0];
		try {
			// invoke("方法名",参数1,参数2,参数3....);
			objects = client.invoke("sayHi", "------------------");
			System.out.println("返回数据:" + objects[0]);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}
	}

	public static void cl3() {
		// 创建动态客户端
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		//Client client = dcf.createClient("http://localhost:8080/services/CommonService?wsdl");
		Client client = dcf.createClient("http://127.0.0.1:8080/soap/hello?wsdl");
		// 需要密码的情况需要加上用户名和密码
		// client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME,
		// PASS_WORD));
		Object[] objects = new Object[0];
		try {
			// invoke("方法名",参数1,参数2,参数3....);
			objects = client.invoke("sayHi", "------------------");
			System.out.println("返回数据:" + objects[0]);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}
	}

	public static  String getCL3(String param){
		String soapXML= "<?xml version=\"1.0\" encoding=\"utf-8\"?><soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:ser='http://webservice.leftso.com/'><soapenv:Header/><soapenv:Body><ser:sayHi><text>"+param+"</text></ser:sayHi></soapenv:Body></soapenv:Envelope>";
		return soapXML;
	}

	public static String HttpClientUtil(String soapXML,String urlStr) {

		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		OutputStream os = null;
		try {
			//第一步：创建服务地址，不是WSDL地址
			URL url = new URL(urlStr);
			//第二步：打开一个通向服务地址的连接
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			//第三步：设置参数
			//3.1发送方式设置：POST必须大写
			connection.setRequestMethod("POST");
			//3.2设置数据格式：content-type
			connection.setRequestProperty("content-type", "text/xml;charset=utf-8");
			//3.3设置输入输出，因为默认新创建的connection没有读写权限，
			connection.setDoInput(true);
			connection.setDoOutput(true);

			//第四步：组织SOAP数据，发送请求
			os = connection.getOutputStream();
			os.write(soapXML.getBytes());
			//第五步：接收服务端响应，打印
			int responseCode = connection.getResponseCode();
			if (200 == responseCode) {//表示服务端响应成功
				is = connection.getInputStream();
				isr = new InputStreamReader(is);
				br = new BufferedReader(isr);

				StringBuffer sb = new StringBuffer();
				String temp = null;
				while (null != (temp = br.readLine())) {
					sb.append(temp);
				}
				String result = sb.toString().replaceAll("&lt;", "<").replaceAll("&gt;", ">");
				System.out.println(result);
				return result;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != is){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != isr){
				try {
					isr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != br){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != os) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return "";
	}

}
