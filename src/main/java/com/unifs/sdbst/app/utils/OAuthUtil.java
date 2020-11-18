package com.unifs.sdbst.app.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author liuzibo
 * @version 2018-06-12
 */
public class OAuthUtil {
	/**
	 * 根据access_token获取用户信息 
	 * @throws UnsupportedEncodingException 
	 */
	public static String getUserInfo(String accessTokenUrl) throws UnsupportedEncodingException{
		InputStream isrUserInfo = getContentByGET(accessTokenUrl);
		StringBuilder buf = new StringBuilder();
		if(isrUserInfo == null){ //对access_token过期的处理
			buf.append("{\"error_code\":\"500\",\"error_msg\":\"服务器内部错误，可能access_token已经过期或client_id不可用或重定向地址通信异常.\"}");
			return buf.toString();
		}
		 InputStreamReader isr  = new InputStreamReader(isrUserInfo,"UTF-8");
		 BufferedReader in = new BufferedReader(isr);
	        try {
	            for (String str; (str = in.readLine()) != null;) {
	                buf.append(str);
	            }
	        } catch (IOException ioe) {
	        } finally {
	            try {
	                in.close();
	            } catch (IOException ioe) {
	            }
	        }
	        return buf.toString();
	}
	
	
	/**
	 * 处理传递的URL参数 
	 */
	public static String getParamValue(String query, String param) {
        String paramValue = "";
        if (query != null && query.length() != 0) {
            String[] paramsArray = query.split("\\&");
            for (String parameter : paramsArray) {
                if (parameter.startsWith(param)) {
                    paramValue = parameter.substring(parameter.indexOf("=") + 1);
                    break;
                }
            }
        }
        return paramValue;
    }
	/**
	 * 根据授权码获取access_token 
	 * @throws UnsupportedEncodingException 
	 */
	public static String getAccessToken(String serviceUrl) throws UnsupportedEncodingException{
		InputStream isr = getContentByPOST(serviceUrl);
		StringBuilder buf = new StringBuilder();
		if(isr == null){ //对授权码过期的处理
			buf.append("{\"error_code\":\"500\",\"error_msg\":\"服务器内部错误，可能授权码已经过期或client_id不可用或重定向地址通信异常.\"}");
			return buf.toString();
		}
        BufferedReader in = new BufferedReader(new InputStreamReader(isr,"UTF-8"));
        try {
            for (String str; (str = in.readLine()) != null;) {
                buf.append(str);
            }
        } catch (IOException ioe) {
        } finally {
            try {
                in.close();
            } catch (IOException ioe) {}
        }
        return buf.toString();
	}
	/** 
	 * 通过Get方式发起请求
	 */
	public static InputStream getContentByGET(String serviceUrl){
   	 	InputStream is = null;
        try {
            HttpURLConnection connection = getURLConnection(serviceUrl,"GET");
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                is = connection.getInputStream();
            } else {
            	is = getContentByPOST(serviceUrl);
            }
        } catch (IOException ioe) {
        	System.out.println(ioe.getMessage());
        }
        return is;
   }
	
	/** 
	 * 通过Post方式
	 */
    public static InputStream getContentByPOST(String serviceUrl) {
        InputStream is = null;
        try {
        	HttpURLConnection connection = getURLConnection(serviceUrl,"POST");
           
                is = connection.getInputStream();
            
        } catch (IOException ioe) {
        	
        	System.out.println(ioe.getMessage());
        }
        return is;
    }
    /***
     * 获取HttpURLConnection连接
     */
    public static HttpURLConnection getURLConnection(String serviceUrl, String method){
    	URL url = null;
    	HttpURLConnection connection = null;
		try {
			url = new URL(serviceUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			//设置超时时间(单位：毫秒)：10秒
			connection.setConnectTimeout(300000);
			connection.setReadTimeout(300000);
		    connection.setRequestMethod(method);
		    connection.setRequestProperty("Accept-Charset", "utf-8");
		    connection.setRequestProperty("contentType", "utf-8");
		} catch (IOException e) {}
       return connection;
    }
}
