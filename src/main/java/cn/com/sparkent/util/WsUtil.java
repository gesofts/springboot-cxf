/*****************************************************
 * Copyright(C):南京莱斯信息技术股份有限公司
 * 文件名称      :WsUtil
 * 编制人员      :wch
 * 创建日期      :2017-09-12
 * 版本          :v1.0
 *
 *修改记录
 *  版本信息     ：
 *  更改人员     ：
 *  更改日期     ：
 *  更改内容     ：
 *  更改原因     ：
 ****************************************************/
package cn.com.sparkent.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author WCH on 2018/09/12.
 * @version v1.0
 */
public class WsUtil {

    /**
     *
     * @param soapXML  要推送的数据
     * @param urlStr  http地址
     * @return
     */
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
