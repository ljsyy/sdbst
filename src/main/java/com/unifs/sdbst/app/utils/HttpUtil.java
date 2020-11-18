package com.unifs.sdbst.app.utils;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unifs.sdbst.app.exception.MyException;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @创建人 张恭雨
 * @创建时间 2018/8/20
 * @描述httpClient远程接口访问工具类
 */
public class HttpUtil {
    private static Logger log = LoggerFactory.getLogger(HttpUtil.class);
    private static ObjectMapper objectMapper = new ObjectMapper();

    /*
     *功能描述 向远程接口发起请求，返回字符类型结果
     * @author 张恭雨
     * @param url 接口地址
     * @param requestType 请求类型
     * @param params 传递参数
     * @return String 返回结果
     */
    public static String httpRequestToString(String url, String requestType,
                                             Map<String, Object> params) {
        log.info("当前位置：HttpUtil httpRequestToString" +
                "进入远程接口调用");
        //接口返回结果
        String requestResult = null;

        try {
            String parameters = "";
            boolean hasParams = false;    //判断是否传入了参数，默认为false（无参数）
            //将参数集合拼接成字符串
            for (String key : params.keySet()) {
                String value = URLEncoder.encode(params.get(key).toString(), "UTF-8");
                parameters += key + "=" + value + "&";
                hasParams = true;
            }
            //除去字符串末尾了&符号
            if (hasParams) {
                parameters = parameters.substring(0, parameters.length() - 1);
            }
            //判断请求类型并创建对应的请求对象
            boolean isGet = "get".equalsIgnoreCase(requestType);
            boolean isPost = "post".equalsIgnoreCase(requestType);
            boolean isDelete = "delete".equalsIgnoreCase(requestType);
            boolean isPut = "put".equalsIgnoreCase(requestType);

            //创建连接对象
            DefaultHttpClient client = new DefaultHttpClient();
            HttpRequestBase method = null;
            if (isGet) {
                url += "?" + parameters;
                method = new HttpGet(url);
            } else if (isPost) {
                method = new HttpPut(url);
                HttpPut putMethod = (HttpPut) method;
                StringEntity entity = new StringEntity(parameters);
                putMethod.setEntity(entity);

            } else if (isDelete) {
                url += "?" + parameters;
                method = new HttpDelete(url);

            } else if (isPut) {
                method = new HttpPut(url);
                HttpPut putMethod = (HttpPut) method;
                StringEntity entity = new StringEntity(parameters);
                putMethod.setEntity(entity);
            }
            //设置请求超时时间
            method.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 6000);
            //设置请求头
            method.addHeader("Content-Type", "application/x-www-form-urlencoded");
            //访问接口，返回状态码
            HttpResponse response = client.execute(method);
            //判断状态码为200
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                requestResult = EntityUtils.toString(response.getEntity());   //返回json格式
                log.info("接口调用成功");
            }
            client.close();
        } catch (UnsupportedEncodingException e) {
            log.error("接口调用失败，错误：" + e);
            e.printStackTrace();
        } catch (IOException e) {
            log.error("接口调用失败，错误：" + e);
            e.printStackTrace();
        }
        return requestResult;
    }

    public static String getClientIp(HttpServletRequest request) {

        //String ip = request.getHeader("x-forwarded-for");
        String ip = request.getHeader("X-real-ip");
        log.debug("x-forwarded-for = {}", ip);
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            log.debug("Proxy-Client-IP = {}", ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            log.debug("WL-Proxy-Client-IP = {}", ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            log.debug("RemoteAddr-IP = {}", ip);
        }
        if (StringUtils.isNotBlank(ip)) {
            ip = ip.split(",")[0];
        }
        return ip;

    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param, String charset) {
        String result = "";
        String urlNameString = "";
        BufferedReader in = null;
        try {
            if (StringUtils.isEmpty(param)) {
                urlNameString = url;
            } else {
                urlNameString = url + "?" + param;
            }
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            System.err.println("urlNameString=>>>>>>" + realUrl);
            URLConnection connection;

            connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                if (null != key && "Content-Type".equals(key)) {
                    int a = map.get(key).toString().indexOf("=") + 1;
                    if (a != 0) {
                        charset = map.get(key).toString().substring(map.get(key).toString().indexOf("=") + 1, map.get(key).toString().length() - 1);
                    }
                }
                System.out.println(key + "--->" + map.get(key));
            }
            if (null != charset && !"".equals(charset)) {
                System.out.println("编码格式:" + charset);
                // 定义 BufferedReader输入流来读取URL的响应
                in = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));
            } else {
                in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            }
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static String sendGetData(String url, String param, String charset) {
        String result = "";
        String urlNameString = "";
        BufferedReader in = null;
        try {
            if (StringUtils.isEmpty(param)) {
                urlNameString = url;
            } else {
                urlNameString = url + "?" + param;
            }
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            System.err.println("urlNameString=>>>>>>" + realUrl);
            URLConnection connection;
            //判断该链接是否包含域名www.shunde.gov.cn，并设置代理
            /*if (url.contains("www.shunde.gov.cn")) {
                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("221.4.169.241", 80));
                connection = realUrl.openConnection(proxy);
            } else {

            }*/
            connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type", "text/plain");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

            // 建立实际的连接
            connection.connect();

            if (null != charset && !"".equals(charset)) {
                System.out.println("编码格式:" + charset);
                // 定义 BufferedReader输入流来读取URL的响应
                in = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));
            } else {
                in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            }
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
//                System.out.println(" ==" + result);
            }
        } catch (Exception e) {
            log.error("请求失败！：" + e.getMessage());
            throw new MyException("发送失败！", e);
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }


    public static String sendGetRequest(String username, String pwd, String url) {
        int vapStatusCode = -1;
        HttpClient httpClient = new HttpClient();
        GetMethod method = new GetMethod(url);
        Object[] ipAndPort = getCmsIpAndPort(url);

        label73:
        {
            String var8;
            try {
                method.setDoAuthentication(true);
                Credentials defaultcreds = new UsernamePasswordCredentials(username, pwd);
                httpClient.getState().setCredentials(new AuthScope((String) ipAndPort[0], (Integer) ipAndPort[1], AuthScope.ANY_REALM), defaultcreds);
                httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(10000);
                httpClient.getParams().setContentCharset("UTF-8");
                httpClient.getParams().setCookiePolicy("ignoreCookies");
                vapStatusCode = httpClient.executeMethod(method);
                if (vapStatusCode != 200) {
                    break label73;
                }

                var8 = method.getResponseBodyAsString();
            } catch (HttpException var14) {
                break label73;
            } catch (IOException var15) {
                break label73;
            } catch (Exception var16) {
                break label73;
            } finally {
                method.releaseConnection();
            }

            return var8;
        }

        if (vapStatusCode == 401) {
            throw new MyException("", 2);
        } else {
            throw new MyException("", 1);
        }
    }

    public static Document sendStringVapBody(String username, String pwd, String vapCommand, String requestBody) {
        PostMethod method = new PostMethod(vapCommand);
        HttpClient httpClient = new HttpClient();
        method.setRequestHeader("Content-Type", "text/xml");
        method.setRequestBody(requestBody);
        Object[] ipAndPort = getCmsIpAndPort(vapCommand);
        int vapStatusCode = -1;

        label73:
        {
            Document var9;
            try {
                Credentials defaultcreds = null;
                defaultcreds = new UsernamePasswordCredentials(username, pwd);
                httpClient.getState().setCredentials(new AuthScope((String) ipAndPort[0], (Integer) ipAndPort[1], AuthScope.ANY_REALM), defaultcreds);
                httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(10000);
                httpClient.getParams().setContentCharset("UTF-8");
                httpClient.getParams().setCookiePolicy("ignoreCookies");
                vapStatusCode = httpClient.executeMethod(method);
                if (vapStatusCode != 200) {
                    break label73;
                }

                var9 = DocumentHelper.parseText(method.getResponseBodyAsString());
            } catch (HttpException var15) {
                break label73;
            } catch (IOException var16) {
                break label73;
            } catch (Exception var17) {
                break label73;
            } finally {
                method.releaseConnection();
            }

            return var9;
        }

        if (vapStatusCode == 401) {
            throw new MyException("", 2);
        } else {
            throw new MyException("", 1);
        }
    }

    public static Document executeMethod(String cmsUrl, String command, NameValuePair[] nvps, String username, String password) throws DocumentException, MyException, IOException {
        if (!cmsUrl.endsWith("/")) {
            cmsUrl = cmsUrl + "/";
        }

        PostMethod pm = new PostMethod(cmsUrl + command);
        Object[] ipAndPort = getCmsIpAndPort(cmsUrl);
        HttpClient httpClient = new HttpClient();

        Document var11;
        try {
            pm.addParameters(nvps);
            pm.setDoAuthentication(true);
            Credentials defaultcreds = new UsernamePasswordCredentials(username, password);
            httpClient.getState().setCredentials(new AuthScope((String) ipAndPort[0], (Integer) ipAndPort[1], AuthScope.ANY_REALM), defaultcreds);
            httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(10000);
            httpClient.getParams().setContentCharset("UTF-8");
            httpClient.getParams().setCookiePolicy("ignoreCookies");
            int status = httpClient.executeMethod(pm);
            if (status != 200) {
                throw new MyException("", status);
            }

            Document doc = DocumentHelper.parseText(pm.getResponseBodyAsString());
            var11 = doc;
        } catch (Exception var15) {
            throw var15;
        } finally {
            pm.releaseConnection();
        }

        return var11;
    }

    private static Object[] getCmsIpAndPort(String cmsUrl) {
        String cmsIp = "localhost";
        Integer cmsPort = 80;
        int ind1 = cmsUrl.indexOf("//");
        if (ind1 > -1) {
            cmsUrl = cmsUrl.substring(ind1 + 2);
        } else {
            cmsUrl = cmsUrl.substring(0);
        }

        int ind2 = cmsUrl.indexOf(":");
        if (ind2 > -1) {
            cmsIp = cmsUrl.substring(0, ind2);
            int ind3 = cmsUrl.indexOf("/");
            if (ind3 > -1) {
                cmsPort = Integer.parseInt(cmsUrl.substring(ind2 + 1, ind3));
            } else {
                cmsPort = Integer.parseInt(cmsUrl.substring(ind2 + 1));
            }
        } else {
            ind2 = cmsUrl.indexOf("/");
            if (ind2 > -1) {
                cmsIp = cmsUrl.substring(0, ind2);
            } else {
                cmsIp = cmsUrl.substring(0);
            }
        }

        return new Object[]{cmsIp, cmsPort};
    }

    public static Object convertJsonStr2Bean(String jsonStr, Class<?> targetObjectClass) throws JsonMappingException, IOException {
        return objectMapper.readValue(jsonStr, targetObjectClass);
    }

    public static Long string2Long(String source) {
        try {
            return Long.valueOf(source);
        } catch (Exception var2) {
            return null;
        }
    }

    public static Date stringTranDate(String format, String str) {
        if (str.trim().equals("")) {
            return null;
        } else {
            SimpleDateFormat df = new SimpleDateFormat(format);

            try {
                return df.parse(str.trim());
            } catch (ParseException var4) {
                return null;
            }
        }
    }


    public static String postJsonData(String strUrlPath,String jsonStr){

        byte[] data = jsonStr.getBytes();//获得请求体
        try {

            URL url = new URL(strUrlPath);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            //设置连接超时时间
            httpURLConnection.setConnectTimeout(3000);
            //打开输入流，以便从服务器获取数据
            httpURLConnection.setDoInput(true);
            //打开输出流，以便向服务器提交数据
            httpURLConnection.setDoOutput(true);
            //设置以Post方式提交数据
            httpURLConnection.setRequestMethod("POST");
            //使用Post方式不能使用缓存
            httpURLConnection.setUseCaches(false);
            //设置请求体的类型是json类型
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            //设置请求体的长度
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
            //获得输出流，向服务器写入数据
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(data);
            //获得服务器的响应码
            int response = httpURLConnection.getResponseCode();
            // Log.d("服务器的响应码",""+response);
            if (response == HttpURLConnection.HTTP_OK) {
                InputStream inptStream = httpURLConnection.getInputStream();
                //处理服务器的响应结果
                return dealResponseResult(inptStream);
            }else{
                return "error_获取token失败";
            }

        }catch (Exception e){
            e.printStackTrace();
            return "error_获取token报错";
        }
    }

    /*
     * Function  :   处理服务器的响应结果（将输入流转化成字符串）
     * Param     :   inputStream服务器的响应输入流
     */
    private static String dealResponseResult(InputStream inputStream) {
        String resultData = null;      //存储处理结果
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        try {
            while ((len = inputStream.read(data)) != -1) {
                byteArrayOutputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultData = new String(byteArrayOutputStream.toByteArray());
        return resultData;
    }
}
