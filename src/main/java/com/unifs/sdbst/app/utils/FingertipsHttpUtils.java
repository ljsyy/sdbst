package com.unifs.sdbst.app.utils;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unifs.sdbst.app.exception.MyException;
import okhttp3.*;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @创建人 张恭雨
 * @创建时间 2018/8/20
 * @描述httpClient远程接口访问工具类
 */
public class FingertipsHttpUtils {
    private static Logger log = LoggerFactory.getLogger(FingertipsHttpUtils.class);
    private static ObjectMapper objectMapper = new ObjectMapper();

    private static String AccessId = "c55ddfbeaf1b";
    private static String appSecret = "secret62a4d";
    /*
     *功能描述 向远程接口发起请求，返回字符类型结果，参数为Map
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
            if (params != null){
                for (String key : params.keySet()) {
                    String value = URLEncoder.encode(params.get(key).toString(), "UTF-8");
                    parameters += key + "=" + value + "&";
                    hasParams = true;
                }
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
                method = new HttpPost(url);
                HttpPost postMethod = (HttpPost) method;
                StringEntity entity = new StringEntity(parameters);
                postMethod.setEntity(entity);

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
            method.addHeader("Authorization",getAuthorization());
//            getAuthorizationHeaderInfo(AccessId,appSecret);
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
            return e.getMessage();
        } catch (IOException e) {
            log.error("接口调用失败，错误：" + e);
            e.printStackTrace();
            return e.getMessage();

        }
        return requestResult;
    }
    //上传文件
    public static String uploadFilePost(String url, String filePath){
        File excelFile = new File(filePath);
        try{
            RequestBody requestBody = new MultipartBody.Builder()
                    .addFormDataPart("file",excelFile.getName(),  RequestBody.create(MediaType.parse
                            ("multipart/form-data"),excelFile))
                    .build();
            Request.Builder requestOk = new Request.Builder()
                    .url(url)
                    .post(requestBody);
            //设置请求超时时间

            //设置请求头
            requestOk.addHeader("Content-Type", "application/x-www-form-urlencoded");
            requestOk.addHeader("Authorization",getAuthorization());
            Request request = requestOk.build();
            Response response;
            try {
                response = new OkHttpClient().newCall(request).execute();
                String jsonString = response.body().string();
                log.info("一窗上传文件接口实际返回："+jsonString);
                if(response.isSuccessful()){
                    //上传成功，删除临时文件
                    File file=new File(filePath);
                    if(file.exists()&&file.isFile())
                        file.delete();
                    //返回结果
                    return jsonString;
                }
            } catch (Exception e) {
                log.info("调用一窗上传文件接口异常：");
                e.printStackTrace();
            }
        }catch (Exception e){
            log.info("调用一窗上传文件接口异常：");
            e.printStackTrace();
        }
        return "上传失败";
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param object 请求参数，JSONObject object
     * @return 所代表远程资源的响应结果
     */
    public static String sendPostJson(String url, JSONObject object) {
        log.info("当前位置：FingertipsHttpUtils sendPostJson" +
                "进入远程接口调用");
        //接口返回结果
        String requestResult = null;

        try {
    //创建连接对象
            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost method = new HttpPost( url);
            StringEntity entity = new StringEntity(object.toJSONString(),"UTF-8");
            method.setEntity(entity);

            //设置请求超时时间
            method.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 6000);
            //设置请求头
            method.addHeader("Content-Type", "application/json; charset=utf-8");
            method.addHeader("Authorization",getAuthorization());
//            getAuthorizationHeaderInfo(AccessId,appSecret);
            //访问接口，返回状态码
            HttpResponse response = client.execute(method);
            //判断状态码为200
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                requestResult = EntityUtils.toString(response.getEntity());   //返回json格式
                log.info("接口调用成功");
            }
            System.out.println("响应内容：" + requestResult);
            client.close();
        } catch (UnsupportedEncodingException e) {
            log.error("接口调用失败，错误：" + e);
            return e.getMessage();
        } catch (IOException e) {
            log.error("接口调用失败，错误：" + e);
            return e.getMessage();
        }
        return requestResult;
    }

    /**
     * 向指定 URL 发送Put方法的请求
     *
     * @param url   发送请求的 URL
     * @param object 请求参数，JSONObject object
     * @return 所代表远程资源的响应结果
     */
    public static String sendPutJson(String url, JSONObject object) {
        log.info("当前位置：FingertipsHttpUtils sendPutJson" +
                "进入远程接口调用");
        //接口返回结果
        String requestResult = null;

        try {
            //创建连接对象
            DefaultHttpClient client = new DefaultHttpClient();
            HttpPut method = new HttpPut( url);
            StringEntity entity = new StringEntity(object.toJSONString(),"UTF-8");
            method.setEntity(entity);

            //设置请求超时时间
            method.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 6000);
            //设置请求头
            method.addHeader("Content-Type", "application/json; charset=utf-8");
            method.addHeader("Authorization",getAuthorization());
//            getAuthorizationHeaderInfo(AccessId,appSecret);
            //访问接口，返回状态码
            HttpResponse response = client.execute(method);
            //判断状态码为200
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                requestResult = EntityUtils.toString(response.getEntity());   //返回json格式
                log.info("接口调用成功");
            }
            System.out.println("响应内容：" + requestResult);
            client.close();
        } catch (UnsupportedEncodingException e) {
            log.error("接口调用失败，错误：" + e);
            return e.getMessage();
        } catch (IOException e) {
            log.error("接口调用失败，错误：" + e);
            return e.getMessage();
        }
        return requestResult;
    }

    /**
     * 向指定 URL 发送Put方法的请求 非json格式
     *
     * @param url   发送请求的 URL
     * @param object 请求参数，Map
     * @return 所代表远程资源的响应结果
     */
    public static String sendPut(String url, JSONObject object) {
        log.info("当前位置：FingertipsHttpUtils sendPut" +
                "进入远程接口调用"+url);
        //接口返回结果
        String requestResult = null;

        try {
            //创建连接对象
            DefaultHttpClient client = new DefaultHttpClient();
            HttpPut method = new HttpPut( url);
            StringEntity entity = new StringEntity(object.toJSONString(),"UTF-8");
            method.setEntity(entity);

            //设置请求超时时间
            method.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 6000);
            //设置请求头
            method.addHeader("Content-Type", "application/x-www-form-urlencoded");
            method.addHeader("Authorization",getAuthorization());
//            getAuthorizationHeaderInfo(AccessId,appSecret);
            //访问接口，返回状态码
            HttpResponse response = client.execute(method);
            //判断状态码为200
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                requestResult = EntityUtils.toString(response.getEntity());   //返回json格式
                log.info("接口调用成功");
            }
            System.out.println("响应内容：" + requestResult);
            client.close();
        } catch (UnsupportedEncodingException e) {
            log.error("接口调用失败，错误：" + e);
            return e.getMessage();
        } catch (IOException e) {
            log.error("接口调用失败，错误：" + e);
            return e.getMessage();
        }
        return requestResult;
    }
    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        String urlNameString = "";
        String charset = "";
        BufferedReader in = null;
        try {
            if (param == null) {
                urlNameString = url;
            } else {
                urlNameString = url + "?" + param;
            }
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            System.err.println("urlNameString=>>>>>>" + realUrl);
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("Authorization",getAuthorization());
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
                // 定义 BufferedReader输入流来读取URL的响应
                in = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));
            } else {
                in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            }
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            log.info("接口sendGet调用成功");
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
            conn.setRequestProperty("Authorization",getAuthorization());
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
                System.out.println(" ==" + result);
            }
            log.info("接口sendPost调用成功");
        } catch (Exception e) {
//            log.error("发送短信验证码错误：" + e.getMessage());
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


    /**
     *
     *  [获取UTC标准时]
     *  @return
     */
    public static String getUTCDate() {
        String dateStr = "";
        Date date = null;
        String months = "", days = "", hours = "", sec = "", minutes = "";

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        StringBuffer UTCTimeBuffer = new StringBuffer();
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));//important
        int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
        int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
        cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        if (month < 10) {
            months = "0" + String.valueOf(month);
        }
        else {
            months = String.valueOf(month);
        }
        if (minute < 10) {
            minutes = "0" + String.valueOf(minute);
        }
        else {
            minutes = String.valueOf(minute);
        }
        if (day < 10) {
            days = "0" + String.valueOf(day);
        }
        else {
            days = String.valueOf(day);
        }
        if (hour < 10) {
            hours = "0" + String.valueOf(hour);
        }
        else {
            hours = String.valueOf(hour);
        }
        if (second < 10) {
            sec = "0" + String.valueOf(second);
        }
        else {
            sec = String.valueOf(second);
        }
        UTCTimeBuffer.append(year).append("-").append(months).append("-").append(days);
        UTCTimeBuffer.append("T").append(hours).append(":").append(minutes).append(":").append(sec).append("Z");
        try {
            date = format.parse(UTCTimeBuffer.toString());
            dateStr = format.format(date);
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dateStr;
    }

    public static  String getAuthorization(){
        log.info("=====================getAuthorization=====================");
//        System.out.println(AccessId);
//        System.out.println(appSecret);
//        System.out.println("========================");
        Date date = new Date();//创建一个date对象保存当前时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");//Date指定格式：yyyy-MM-dd HH:mm:ss:SSS
        String dateStr = simpleDateFormat.format(date);//format()方法将Date转换成指定格式的String
//        System.out.println(dateStr);
//        String TimeStamp = "2019-09-09T14:31:18Z";//
        String TimeStamp = dateStr;//getUTCDate();
        String StringToSign = "AccessId=" + AccessId + "&" + "TimeStamp=" + TimeStamp;
//        System.out.println("StringToSign:"+StringToSign);
        String Signature = buildMessageBySha256(StringToSign,appSecret);
//        System.out.println("Signature:"+Signature);
        String Authorization = "AccessId="+AccessId+",DigestAlgorithm=HMAC-SHA256,TimeStamp="+TimeStamp+",Signature="+Signature;
//        System.out.println("Authorization:"+Authorization);
        return  Authorization;
    }

    public static String getAuthorizationHeaderInfo(String appId,String secret){
        Calendar nowDate = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
//        nowDate.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));//important
        nowDate.setTime(new Date());
        nowDate.add(Calendar.MINUTE,3);
        //获取UTC格式的日期
        String TimeStamp = DateUtil.formatDate(nowDate.getTime(),"yyyy-MM-dd'T'HH:mm:ss'Z'");
        System.out.println(TimeStamp);
        //封装StringToSign数据
        StringBuilder StringToSign = new StringBuilder();
        StringToSign.append("AccessId=");
        StringToSign.append(appId);
        StringToSign.append("&");
        StringToSign.append("TimeStamp=");
        StringToSign.append(TimeStamp);
        String Signature = buildMessageBySha256(StringToSign.toString(),secret);

        //封装请求头最终所需的数据
        StringBuilder Authorization = new StringBuilder();
        Authorization.append("AccessId=");
        Authorization.append(appId);
        Authorization.append(",DigestAlgorithm=HMAC-SHA256,TimeStamp=");
        Authorization.append(TimeStamp);
        Authorization.append(",Signature=");
        Authorization.append(Signature);
        System.out.println("签名：\n"+Authorization.toString());
        return Authorization.toString();
    }

    private static String buildMessageBySha256(String message, String secret) {
        if (null == message || null == secret || "".equals(secret)) {
            return "";
        }
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
            byte[] bytes = mac.doFinal(message.getBytes("UTF-8"));
            return invertBytesToHex(bytes);
        } catch (Exception e) {
            log.info("=====================签名制作错误=====================");
            return "";
        }
    }

    /**
     * 字节数组转十六进制字符串
     *
     * @param bytes 字节数组
     * @return 十六进制字符串
     */
    private static String invertBytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%x", b));
        }
        return sb.toString();
    }

}
