package com.unifs.sdbst.app.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class AppHttpUtils {
	public static String sendHttpPost(String url, String body) throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("Content-Type", "application/json");
		httpPost.setEntity(new StringEntity(body));

		CloseableHttpResponse response = httpClient.execute(httpPost);
		System.out.println("StatusCode "+response.getStatusLine().getStatusCode());
		HttpEntity entity = response.getEntity();
		String result = EntityUtils.toString(entity, "UTF-8");
		System.out.println("result "+result);

		response.close();
		httpClient.close();
		return result;
	}
}
