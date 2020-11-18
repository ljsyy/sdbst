package com.unifs.sdbst.app.utils;

import com.alibaba.fastjson.JSON;
import org.apache.axis.message.SOAPHeaderElement;

import javax.xml.soap.SOAPException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WsClientTool {

	/**
	 * 解析返回状态码
	 * 
	 * @param jsonresult
	 * @return
	 */
	public static String getStatus(String jsonresult) {
		Map<String, String> outDatas = getOutMap(jsonresult);
		String status = String.valueOf(outDatas.get("STATUS")); // 获取返回状态
		return status;
	}

	/**
	 * 解析返回的数据说明
	 * 
	 * @param jsonresult
	 * @return
	 */
	public static Map<String, String> getHeadMap(String jsonresult) {
		Map<String, String> outDatas = getOutMap(jsonresult);
		String heads = String.valueOf(outDatas.get("HEAD")); // 获取返回的数据说明
		Map<String, String> head = JSON.parseObject(heads, new HashMap<String, String>().getClass());
		return head;
	}

	/**
	 * 获取数据集
	 * 
	 * @param jsonresult
	 * @return
	 */
	public static List<Map<String, Object>> getData(String jsonresult) {
		Map<String, String> outDatas = getOutMap(jsonresult);
		String datas = String.valueOf(outDatas.get("DATA")); // 获取返回的数据集
		List<Map<String, Object>> data = JSON.parseObject(datas, new ArrayList<HashMap<String, Object>>().getClass());
		return data;
	}

	/**
	 * 解析分页参数
	 * 
	 * @param jsonresult
	 * @return
	 */
	public static Map<String, Object> getPageMap(String jsonresult) {
		Map<String, String> outDatas = getOutMap(jsonresult);
		String pageData = String.valueOf(outDatas.get("PAGE_DATA")); // 获取分页参数
		Map<String, Object> pageMap = JSON.parseObject(pageData, new HashMap<String, Object>().getClass());
		return pageMap;
	}

	/**
	 * 解析返回的json数据
	 * 
	 * @param jsonresult
	 * @return
	 */
	public static Map<String, String> getOutMap(String jsonresult) {
		Map<String, String> outDatas = JSON.parseObject(jsonresult, new HashMap<String, String>().getClass());
		return outDatas;
	}

	/**
	 * 调用webservice所需要的消息头
	 * 
	 * @param userName
	 *            用户名
	 * @param userPwd
	 *            密码
	 * @return
	 */
	public static SOAPHeaderElement getSoapHead(String userName, String userPwd) {
		SOAPHeaderElement soapHeaderElement = new SOAPHeaderElement("", "CustomHeader");
		try {
			// 输入用户名、密码
			
			soapHeaderElement.addChildElement("userName").setValue(userName);
			soapHeaderElement.addChildElement("userPassWord").setValue(getMD5Str(userPwd));
		} catch (SOAPException e) {
			e.printStackTrace();
		}
		return soapHeaderElement;

	}

	/**
	 * 加密密码
	 * 
	 * @param str
	 *            需要加密的字串
	 * @return 加密后的字串
	 */
	public static String getMD5Str(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString();
	}

}
