package com.unifs.sdbst.app.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

/**
 * <p>
 * HTTP请求发送工具类（包含转译）。
 * </p>
 * 
 * @author liuda
 * 
 */
public class EMSHttpUtil {

	private static final Log _log = LogFactory.getLog(EMSHttpUtil.class);

	public static final int DEFAULT_CONNECT_TIMEOUT = 15 * 1000;
	public static final int DEFAULT_READ_TIMEOUT = 15 * 1000;

	public static final String EQUAL = "=";
	public static final String AND = "&";
	public static final String PREFIX = "*_*";
	public static final String SUFFIX = "_*_";
	public static final String EQUAL_PS = PREFIX + EQUAL + SUFFIX;
	public static final String AND_PS = PREFIX + AND + SUFFIX;

	public enum ContentType {
		APPLICATION_XML("application/xml"), 
		APPLICATION_ATOM_XML("application/atom+xml"), 
		APPLICATION_XHTML_XML("application/xhtml+xml"), 
		APPLICATION_SVG_XML("application/svg+xml"), 
		APPLICATION_JSON("application/json"), 
		APPLICATION_FORM_URLENCODED("application/x-www-form-urlencoded"), 
		APPLICATION_OCTET_STREAM("application/octet-stream"), 
		MULTIPART_FORM_DATA("multipart/form-data"), 
		TEXT_PLAIN("text/plain"), 
		TEXT_XML("text/xml"), 
		TEXT_HTML("text/html");

		private String value;

		private ContentType(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return value;
		}
	}

	public enum Charset {
		UTF8("UTF-8"), GBK("GBK"), GB2312("GB2312"), ISO88591("ISO8859-1");

		private String value;

		private Charset(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return value;
		}
	}

	public enum HttpMethod {
		GET, POST, HEAD, OPTIONS, PUT, DELETE, TRACE;
	}

	private static final String CHARSET = "; charset=";

	/**
	 * 生成标准contentType字符串
	 * 
	 * @param type
	 * @param charset
	 * @return
	 */
	public static String getContentType(ContentType type, Charset charset) {
		if (charset == null) {
			return type.toString();
		}
		return type.toString() + CHARSET + charset.toString();
	}

	/**
	 * 将POST请求Map参数转换为请求体
	 * 
	 * @param params
	 * @return
	 */
	public static String convertToBody(Map<String, Object> params) {
		String body = "";
		if (params == null) {
			return body;
		}
		Set<Entry<String, Object>> entrySet = params.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			body += entry.getKey() + "=" + entry.getValue().toString() + "&";
		}
		body = body.substring(0, body.length() - 1);
		return body;
	}
	/**
	 * 将POST请求Map参数转换为请求体
	 * 
	 * @param params
	 * @return
	 */
	public static String convertToBody2(Map<String, String> params) {
		String body = "";
		if (params == null) {
			return body;
		}
		Set<Entry<String, String>> entrySet = params.entrySet();
		for (Entry<String, String> entry : entrySet) {
			body += entry.getKey() + "=" + entry.getValue().toString() + "&";
		}
		body = body.substring(0, body.length() - 1);
		return body;
	}
	/**
	 * 将参数按照指定编码格式进行URL转换
	 * 
	 * @param body
	 * @param charset
	 * @return
	 */
	public static String URLEncode(String body, String charset) {
		if (null == body || "".equals(body) || "".equals(body.trim())) {
			return "";
		}
		try {
			String equalEncode = URLEncoder.encode(EQUAL_PS, charset);
			String andEncode = URLEncoder.encode(AND_PS, charset);
			body = body.replace(EQUAL, EQUAL_PS);
			body = body.replace(AND, AND_PS);
			body = URLEncoder.encode(body, charset);
			body = body.replace(equalEncode, EQUAL);
			body = body.replace(andEncode, AND);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
//		System.out.println("==>URLEncode: " + body);
		return body;
	}

	
	private static class DefaultTrustManager implements X509TrustManager {
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}
	}
	
	/**
	 * 发送HTTP请求
	 * 
	 * @param path
	 *            请求地址
	 * @param body
	 *            请求体
	 * @param method
	 *            请求方式GET/POST
	 * @param charset
	 *            请求编码格式
	 * @param header
	 *            请求头
	 * @param readTimeout
	 *            请求超时时间
	 * @param connectTimeout
	 *            连接超时时间
	 * @param doOutput
	 *            是否可写
	 * @param doInput
	 *            是否可读
	 * @param contentType
	 *            请求contentType
	 * @param isEncode
	 *            body是否需要转译(POST请求时有意义)
	 * @return
	 * @throws IOException
	 */
	public static ByteArrayOutputStream sendHttpRequest(String path,
			String body, String method, String charset,
			Map<String, String> header, int readTimeout, int connectTimeout,
			boolean doOutput, boolean doInput, String contentType,
			boolean isEncode) throws Exception {
		HttpURLConnection httpURLConnection = null;
		InputStream in = null;
		ByteArrayOutputStream baos = null;
		// URL转译
		int index = path.indexOf("?");
		if (index != -1) {
			String urlStr = path.substring(0, index + 1);
			String param = path.substring(index + 1);
			param = URLEncode(param, charset);
			path = urlStr + param;
		}
	//		System.out.println("====>url: " + path);
		URL url = new URL(path);
/*		if ("https".equals(url.getProtocol())) {
		url =new URL(null,path,new com.sun.net.ssl.internal.www.protocol.https.Handler());
		SSLContext ctx = null;
			try {
				ctx = SSLContext.getInstance("TLS");
				ctx.init(new KeyManager[0],
						new TrustManager[] { new DefaultTrustManager() },
						new SecureRandom());
			} catch (Exception e) {
				throw new IOException(e);
			}
			HttpsURLConnection connHttps = (HttpsURLConnection) url
					.openConnection();
			connHttps.setSSLSocketFactory(ctx.getSocketFactory());
			connHttps.setHostnameVerifier(new HostnameVerifier() {

				@Override
				public boolean verify(String arg0, String arg1) {
					return true;// 默认都认证通过
				}
			});
			httpURLConnection = connHttps;
		} else {*/
			httpURLConnection = (HttpURLConnection) url.openConnection();
//		}
		httpURLConnection.setRequestMethod(method);
		httpURLConnection.setReadTimeout(readTimeout);
		httpURLConnection.setConnectTimeout(connectTimeout);
		httpURLConnection.setDoOutput(doOutput);// 打开写入属性
		httpURLConnection.setDoInput(doInput);// 打开读取属性
		if (null != contentType && !"".equals(contentType.trim())) {
			httpURLConnection.setRequestProperty("Content-Type",
					contentType);
		}
		// 设置header
		httpURLConnection = setRequestHeader(httpURLConnection, header);
		
		if (!"GET".equalsIgnoreCase(method)) {
			if (isEncode) {
				body = URLEncode(body, charset);
			}
//			System.out.println("====>body: " + body);
			httpURLConnection.getOutputStream().write(
					body.getBytes(charset));
		}
		int status = httpURLConnection.getResponseCode();
		_log.info("EMSurlparam=" + path.substring(path.indexOf("?") + 1)
				+ ", param=" + body + ", responseCode=" + status);
		if (200 == status) {
			in = (InputStream) httpURLConnection.getContent();
			baos = convert(in);
		} else {
//				System.out.println("====>response status: " + status);
			new Exception("responseCode is " + status);
		}
		if (httpURLConnection != null)
			httpURLConnection.disconnect();
		return baos;
	}

	
	/**
	 * 发送HTTP请求
	 * 
	 * @param path
	 *            请求地址
	 * @param body
	 *            请求体
	 * @param method
	 *            请求方式GET/POST
	 * @param charset
	 *            请求编码格式
	 * @param header
	 *            请求头
	 * @param readTimeout
	 *            请求超时时间
	 * @param connectTimeout
	 *            连接超时时间
	 * @param doOutput
	 *            是否可写
	 * @param doInput
	 *            是否可读
	 * @param contentType
	 *            请求contentType
	 * @return
	 * @throws IOException 
	 */
	public static String sendHttpRequest2(String path,
			String body, String method, String charset,
			Map<String, String> header, int readTimeout, int connectTimeout,
			boolean doOutput, boolean doInput, String contentType)
			throws Exception {
		HttpURLConnection httpURLConnection = null;
		InputStream in = null;
		// URL转译
		int index = path.indexOf("?");
		if (index != -1) {
			String urlStr = path.substring(0, index + 1);
			String param = path.substring(index + 1);
			param = URLEncode(param, charset);
			path = urlStr + param;
		}
//			System.out.println("====>url: " + path);
		URL url = new URL(path);
		httpURLConnection = (HttpURLConnection) url.openConnection();
		httpURLConnection.setRequestMethod(method);
		httpURLConnection.setReadTimeout(readTimeout);
		httpURLConnection.setConnectTimeout(connectTimeout);
		httpURLConnection.setDoOutput(doOutput);// 打开写入属性
		httpURLConnection.setDoInput(doInput);// 打开读取属性
		if (null != contentType && !"".equals(contentType.trim())) {
			httpURLConnection.setRequestProperty("Content-Type",
					contentType);
		}
		// 设置header
		httpURLConnection = setRequestHeader(httpURLConnection, header);
//		long s = System.currentTimeMillis();
		if (!"GET".equalsIgnoreCase(method)) {
//			System.out.println("====>body: " + body);
			httpURLConnection.getOutputStream().write(
					body.getBytes(charset));
		}
		int status = httpURLConnection.getResponseCode();
		_log.info("urlparam=" + path.substring(path.indexOf("?") + 1)
				+ ", param=" + body + ", responseCode=" + status);
		in = (InputStream) httpURLConnection.getContent();
//		long e = System.currentTimeMillis();
//		System.out.println((e - s) / 1000 + "," + (e - s));
		String response = getResponseStream(in);
		if (httpURLConnection != null)
			httpURLConnection.disconnect();
		return response;
	}
	
	
	private static String getResponseStream(InputStream in) throws Exception {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			StringBuffer buf = new StringBuffer();
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				buf.append(tempString);
				buf.append("\n");
			}
			return buf.toString();
		} catch (Exception e) {
			throw e;
		}finally {
			if(reader != null){
				try{reader.close();}catch(Exception e){}
			}
		}
}
	
	/**
	 * 将respon转换为中间变量，解决由于http连接关闭导致inputStream关闭，从而不能读取的问题
	 * 
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static ByteArrayOutputStream convert(InputStream in)
			throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len;
		while ((len = in.read(buffer)) > -1) {
			baos.write(buffer, 0, len);
		}
		baos.flush();
		return baos;
	}

	/**
	 * 将中间变量还原为为请求response的inputStream
	 * 
	 * @param baos
	 * @return
	 */
	public static InputStream convert(ByteArrayOutputStream baos)
			throws Exception {
		return new ByteArrayInputStream(baos.toByteArray());
	}

	/**
	 * 响应类型是String
	 * 
	 * @param path
	 * @param body
	 * @param method
	 * @param charset
	 * @param header
	 * @param readTimeout
	 * @param connectTimeout
	 * @param doOutput
	 * @param doInput
	 * @param contentType
	 * @param isEncode
	 *            body是否需要转译(POST请求时有意义)
	 * @return
	 * @throws Exception
	 */
	public static String sendTextHttpRequest(String path, String body,
			String method, String charset, Map<String, String> header,
			int readTimeout, int connectTimeout, boolean doOutput,
			boolean doInput, String contentType, boolean isEncode)
			throws Exception {
		String response = null;
		InputStream in = convert(sendHttpRequest(path, body, method, charset,
				header, readTimeout, connectTimeout, doOutput, doInput,
				contentType, isEncode));
		if (in != null) {
			try {
				response = getResponseStream(in, charset);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return response;
	}

	/**
	 * 响应类型是String
	 * 
	 * @param path
	 * @param body
	 * @param method
	 * @param charset
	 * @param header
	 * @param readTimeout
	 * @param connectTimeout
	 * @param doOutput
	 * @param doInput
	 * @param contentType
	 * @return
	 * @throws Exception
	 */
	public static String sendTextHttpRequest2(String path, String body,
			String method, String charset, Map<String, String> header,
			int readTimeout, int connectTimeout, boolean doOutput,
			boolean doInput, String contentType) throws Exception {
		String response =  sendHttpRequest2(path, body, method, charset,
				header, readTimeout, connectTimeout, doOutput, doInput,
				contentType);
	 
		return response;
	}
	/**
	 * 发送GET请求
	 * 
	 * @param path
	 *            请求地址
	 * @param charset
	 *            编码格式
	 * @param header
	 *            请求头
	 * @return
	 * @throws Exception
	 */
	public static String sendHttpGet(String path, String charset,
			Map<String, String> header, String contentType) throws Exception {
		return sendTextHttpRequest(path, "", HttpMethod.GET.toString(),
				charset, header, DEFAULT_READ_TIMEOUT, DEFAULT_CONNECT_TIMEOUT,
				true, true, contentType, false);
	}

	/**
	 * 发送普通POST请求，默认转译body
	 * 
	 * @param path
	 *            请求地址
	 * @param body
	 *            请求体
	 * @param charset
	 *            编码格式
	 * @param header
	 *            请求头
	 * @return
	 */
	public static String sendHttpPost(String path, String body, String charset,
			Map<String, String> header, String contentType) throws Exception {
		return sendTextHttpRequest(path, body, HttpMethod.POST.toString(),
				charset, header, DEFAULT_READ_TIMEOUT, DEFAULT_CONNECT_TIMEOUT,
				true, true, contentType, true);
	}


	/**
	 * 发送POST请求 -获取运单接口
	 * 
	 * @param path
	 *            请求地址
	 * @param body
	 *            请求体
	 * @param charset
	 *            编码格式
	 * @param header
	 *            请求头
	 * @return
	 */
	public static String sendHttpPost2(String path, String body, String charset,
			Map<String, String> header, String contentType) throws Exception {
		return sendTextHttpRequest2(path, body, HttpMethod.POST.toString(),
				charset, header, DEFAULT_READ_TIMEOUT, DEFAULT_CONNECT_TIMEOUT,
				true, true, contentType);
	}

	/**
	 * 发送SOAP请求，默认不转译body
	 * 
	 * @param path
	 *            请求地址
	 * @param body
	 *            请求报文
	 * @param charset
	 *            编码格式
	 * @param header
	 *            请求头
	 * @param contentType
	 *            请求的类型
	 * @return
	 * @throws Exception
	 */
	public static String sendSoapHttpPost(String path, String body, String charset,
			Map<String, String> header, String contentType) throws Exception {
		return sendTextHttpRequest(path, body, HttpMethod.POST.toString(),
				charset, header, DEFAULT_READ_TIMEOUT, DEFAULT_CONNECT_TIMEOUT,
				true, true, contentType, false);
	}

	/**
	 * 发送POST请求
	 * 
	 * @param path
	 *            请求地址
	 * @param body
	 *            请求报文
	 * @param charset
	 *            编码格式
	 * @param header
	 *            请求头
	 * @param contentType
	 *            请求的类型
	 * @param isEncode
	 *            body是否需要转译(POST请求时有意义)
	 * @return
	 * @throws Exception
	 */
	public static String sendHttpPost(String path, String body, String charset,
			Map<String, String> header, String contentType, boolean isEncode)
			throws Exception {
		return sendTextHttpRequest(path, body, HttpMethod.POST.toString(),
				charset, header, DEFAULT_READ_TIMEOUT, DEFAULT_CONNECT_TIMEOUT,
				true, true, contentType, isEncode);
	}
	
	private static HttpURLConnection setRequestHeader(
			HttpURLConnection httpURLConnection, Map<String, String> header) {
		if (header != null) {
			Set<Entry<String, String>> headerEntrySet = header.entrySet();
			for (Entry<String, String> headerEntry : headerEntrySet) {
				httpURLConnection.setRequestProperty(headerEntry.getKey(),
						headerEntry.getValue());
			}
		}
		return httpURLConnection;
	}

	private static String getResponseStream(InputStream in, String charset)
			throws Exception {
		if (in == null) {
			return null;
		}
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(in, charset));
			StringBuffer buf = new StringBuffer();
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				buf.append(tempString);
				buf.append("\n");
			}
			return buf.toString();
		} catch (Exception e) {
			throw e;
		} finally {
			if(in != null){
				in.close();
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public static void sendPostAt0(String robotUrl,String title,String msg,String imageName) throws ClientProtocolException, IOException {
		// TODO Auto-generated method stub
		HttpClient httpclient = HttpClients.createDefault();

		HttpPost httppost = new HttpPost(robotUrl);
		httppost.addHeader("Content-Type", "application/json; charset=utf-8");

		String textMsg = "{\"msgtype\": \"markdown\",\"markdown\": {\"title\":\""+title+"\",\"text\":  \"![screenshot](http://fsuni.com/images/testDing.jpg)\n  "+msg+    "\"},\"at\": {\"atMobiles\": [], \"isAtAll\": false}}";
//		"+msg+"\n\n" +">
//		String textMsg = "{\"msgtype\": \"markdown\",\"markdown\": {\"title\":\"冲刺\",\"text\": \"![screenshot](http://fsuni.com/images/testDing.jpg)\n" +
//				"\\n#全年冲刺倒计时：311天 \\n\\n\"},\"at\": {\"atMobiles\": [], \"isAtAll\": false}}";
		System.out.println(textMsg);
		StringEntity se = new StringEntity(textMsg, "utf-8");
		httppost.setEntity(se);

		HttpResponse response = httpclient.execute(httppost);
		if (response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
			String result= EntityUtils.toString(response.getEntity(), "utf-8");
			System.out.println(result);
		}
	}
}

