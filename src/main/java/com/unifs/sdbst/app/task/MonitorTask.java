package com.unifs.sdbst.app.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.unifs.sdbst.app.utils.HttpUtil;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @version V1.0
 * @title MonitorTask
 * @projectName sdbst
 * @description 接口定时监测任务:1、从首页接口中获取监控接口的请求路径（已完成）
 *                            2、请求接口路径，获取响应接口（已完成）
 *                            3、分析相应接口，判断是否正常（已完成）
 *                            4、如果不正常则发送警告短信，并将页面替换（可以考虑对应数据库中接口路径替换为临时页面，但是风险太大，也可以考虑采用标识识别，但是对应用修改较多）
 * @author lx
 * @date 2020/11/20
 */
@Configuration
@EnableScheduling
public class MonitorTask {
    @Value("${monitor.base.find.url}")
    private String monitorMenuPath;
    @Value("${monitor.base.home.url}")
    private String monitorHomePath;
    @Value("${monitor.base.home.param}")
    private String monitorHomeParam;
    @Value("${monitor.base.mobile}")
    private String monitorMobile;
    @Value("${monitor.base.sendMsg}")
    private String monitorMessagePath;
    private static final Logger log = LoggerFactory.getLogger(MonitorTask.class);

//    @Scheduled(cron="0 0 8,13,18 * * ?")
    public void monitorFindTask() throws UnsupportedEncodingException {
        //根据find1To3Menu获取接口路径
        String findMenuJson = HttpUtil.sendGet(monitorMenuPath, "", "");
        //System.out.println("findMenuJson:"+findMenuJson);
        if("".equals(findMenuJson)){
            System.out.println("访问路径："+findMenuJson+"返回结果为null，无法获取对应接口路径信息，请检查url路径配置是否准确");
            return;
        }
        JSONObject findJsonObject = JSONObject.parseObject(findMenuJson);
        log.info("---开始校验find1To3Menu模块---");
        String findResult = checkHotBannerHref(findJsonObject,"data","href");
        System.out.println("在find1To3Menu中，"+findResult);
        //分割除访问失败的具体链接，发送短信警告，并将链接详情写入监控文件中
        warningResult(findResult);
    }

//    @Scheduled(cron="0 15 8,13,18 * * ?")
    public void monitorHomeTask() throws UnsupportedEncodingException {
        //根据homeMenuData获取接口路径
        String homeMenuJson = HttpUtil.sendGet(monitorHomePath, monitorHomeParam, "");
        //System.out.println("homeMenuJson:"+homeMenuJson);
        if("".equals(homeMenuJson)){
            System.out.println("访问路径："+homeMenuJson+"返回结果为null，无法获取对应接口路径信息，请检查url路径配置是否准确");
            return;
        }
        JSONObject homeJsonObject = JSONObject.parseObject(homeMenuJson);
        JSONObject homeDataObject = homeJsonObject.getJSONObject("data");
//        Resp homeResp = JSON.parseObject(homeMenuJson, Resp.class);
        if(homeDataObject==null){
            System.out.println("无法从数据信息实体类中获取信息--home");
            return;
        }
        JSONObject homeArtObject = homeDataObject.getJSONObject("articleMap");
        log.info("---开始校验首页轮播图模块---");
        String artResult = checkHotBannerHref(homeArtObject,"obj","link");
        System.out.println("在首页轮播图模块中，"+artResult);
        System.out.println("---开始处理首页轮播图模块的异常情况---");
        warningResult(artResult);
        log.info("---开始校验热门菜单模块---");
        String hotResult = checkHotBannerHref(homeDataObject,"hotMenu","href");
        System.out.println("在热门菜单模块中，"+hotResult);
        System.out.println("---开始处理热门菜单模块的异常情况---");
        warningResult(hotResult);
        log.info("---开始校验banner模块---");
        String bannerResult = checkHotBannerHref(homeDataObject,"banners","href");
        System.out.println("在banner模块中，"+bannerResult);
        System.out.println("---开始处理banner模块的异常情况---");
        warningResult(bannerResult);
        JSONObject epidemicColumnObj = homeDataObject.getJSONObject("epidemicColumn");
        log.info("---开始校验防疫专栏数据模块---");
        String epidemicColumnResult = checkHotBannerHref(epidemicColumnObj,"children","href");
        System.out.println("在防疫专栏数据模块中，"+epidemicColumnResult);
        System.out.println("---开始处理防疫专栏数据模块的异常情况---");
        warningResult(epidemicColumnResult);
    }


    //校验链接是否正常
    private String checkHotBannerHref(JSONObject homeDataObject ,String arrayType ,String hrefType){
        String result;
        int successCount = 0;//访问成功的链接数
        int failCount = 0;//访问失败的链接数
        int noCount = 0;//无需校验的链接数
        //StringBuilder successHref = new StringBuilder();
        StringBuilder failHref = new StringBuilder();
        //StringBuilder noHref = new StringBuilder();
        StringBuilder failName = new StringBuilder();
        System.out.println("开始校验---checkHotBannerHref："+arrayType+"--"+hrefType+",homeDataObject:"+homeDataObject.toString());
        JSONArray menuArray = homeDataObject.getJSONArray(arrayType);
        if(menuArray==null){
            System.out.println("无法从数据信息实体类中获取信息");
            return "无法从数据信息实体类中获取信息";
        }
        System.out.println("menuArray-size:"+menuArray.size()+",menuArray:"+menuArray.toString());
        StringBuilder resultBuilder = new StringBuilder();
        for(int i = 0; i<menuArray.size(); i++){
            JSONObject job = menuArray.getJSONObject(i);
            if(job!=null){
                String href;
                if("href".equals(hrefType)){
                    href = job.getString("href");
                }else if("link".equals(hrefType)){
                    href = job.getString("link");
                }else{
                    System.out.println("无href参数");
                    continue;
                }
                if("children".equals(arrayType)){
                    JSONArray childrenArray = job.getJSONArray("children");
                    if(!childrenArray.isEmpty()){
                        String childrenResult = checkHotBannerHref(job,"children","href");
                        System.out.println("子模块"+i+":"+childrenResult);
                        resultBuilder.append("子模块").append(i).append(":").append(childrenResult);
                    }
                }
                System.out.println("href:"+href);
                if(href==null){
                    System.out.println("无href--href为null");
                    continue;
                }
                String id = job.getString("id");
                String name = job.getString("name");
                String remarks = job.getString("remarks");
                //请求接口路径，确认接口是否能正常访问
                String checkResult = checkHref(href);
                if(!"".equals(checkResult)&&!"".equals(href)){
                    System.out.println("监控接口结果---checkResult:"+checkResult);
                    if(checkResult.contains("请求失败")){
                        failCount++;
                        failHref.append(",").append(href).append("---").append(name);
                        failName.append(",").append(name);
                    }
                    if(checkResult.contains("200")){
                        successCount++;
                        //successHref.append(",").append(href).append("---").append(name);
                    }
                }else{
                    noCount++;
                    //noHref.append(",").append(href).append("---").append(name);
                }
            }
        }
        result = resultBuilder.toString();
        result = "访问成功的链接数有"+successCount+"个，无需访问的链接数有"+noCount+"个,访问失败的链接数有"+failCount
                +"个；访问失败的具体链接："+failHref+ "；访问失败的具体链接名称："+failName+"；"+result;
        return result;
    }

    //校验请求路径是否能正常访问
    private String checkHref(String href){
        String result = "";
        if(href==null){
            return "href为null";
        }
        //问题在于需要区分请求是get还是post，或者其他
        //外部链接为get请求,排除掉本地链接与部门链接
        if(!href.contains("sdbst") && !href.contains("isd1") && !href.contains("isd3") && !href.equals("department")){
            //如果不是http开头的，需要去除掉前面的随机字母串
            if(href.indexOf("http")>0){
                href = href.substring(href.indexOf("http"));
                System.out.println("sub--href:"+href);
            }
            //需要先判断监控服务器本身是否能联通外网
            String testResult = requestByGet("http://www.baidu.com");
            if(!testResult.contains("200")){
                log.error("本监控服务器无法联通外网");
                return "本监控服务器无法联通外网";
            }else{
                System.out.println("本监控服务器可以联通外网--"+testResult);
            }
            System.out.println("final--href:"+href);
            //不能使用sendGet方法，本项目HttpUtil封装的sendGet方法不会返回状态码，且遇到请求不到的链接，会直接抛出异常，无法做下一步操作
            result = requestByGet(href);
        }
        return result;
    }

    private String requestByGet(String url) {
        // get请求返回结果
        String result = "";
        CloseableHttpClient client = HttpClients.createDefault();
        // 发送get请求
        HttpGet request = new HttpGet(url);
        // 设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(10000).setConnectTimeout(10000).build();
        request.setConfig(requestConfig);
        try {
            CloseableHttpResponse response = client.execute(request);
            //请求发送成功，并得到响应
            result = response.getStatusLine().getStatusCode()+" "+response.getStatusLine();
            System.out.println("result::"+result);
        } catch (IOException e) {
            System.out.println("请求---"+url+"---失败："+e.toString()+"---"+e.getMessage());
            result = "请求失败："+e.toString();
        } finally {
            System.out.println("释放连接");
            request.releaseConnection();
        }
        return result ;
    }

    //短信通报接口异常任务
    private void sendWarningMessage(String warningMessage) throws UnsupportedEncodingException {
        String phone=monitorMobile;
        String[] phoneList = phone.split(",");
        for(String subPhone : phoneList){
            System.out.println("发送短信中"+ URLEncoder.encode(warningMessage,"utf-8") +subPhone);
            String sendResult = HttpUtil.sendGet(monitorMessagePath, "warningMessage="+URLEncoder.encode(warningMessage,"utf-8")+"&subPhone="+subPhone, "");
            if(sendResult==null || sendResult.contains("error:")){
                log.error("发送短信接口："+monitorMessagePath+"?warningMessage="+URLEncoder.encode(warningMessage,"utf-8")+"&subPhone="+subPhone+" 请求异常，请及时处理");
            }
        }
    }

    //编辑需要发送的警告短信内容
    private String makeWarningMessage(String findResult) {
        //如果不包含字符串“访问失败的链接数有”，则返回
        if(!findResult.contains("访问失败的链接数有")){
            return "";
        }
        String failCountStr = findResult.substring(findResult.indexOf("访问失败的链接数有")+9,findResult.indexOf("访问失败的链接数有")+10);
        //判断failResult是否为数字
        if(failCountStr.matches("^[0-9]*$")){
            int failCount = Integer.parseInt(failCountStr);
            if(failCount>0){
                String subFindResult = findResult.substring(findResult.indexOf("访问失败的具体链接名称：")+13);
                System.out.println("failCount:"+failCount);
                System.out.println("subFindResult:"+subFindResult);
                return subFindResult;
            }
        }else{
            log.error("--monitorFindTask--定时任务中，failCountStr不是数字，返回结果分割失败，请及时处理");
            return "";
        }
        return "";
    }

    //分析接口校验结果，调用编辑警告短信方法生成警告短信后，再调用发送警告短信方法发送警告短信
    private void warningResult(String findResult) throws UnsupportedEncodingException {
        String makeResult = "";
        if(findResult.contains("子模块")){
            String[] subFindResultList = findResult.split("子模块");
            for(String subFindResultStr : subFindResultList){
                makeResult = makeWarningMessage(subFindResultStr);
            }
        }else{
            makeResult = makeWarningMessage(findResult);
        }
        if(!"".equals(makeResult)){
            //发送警告短信
            sendWarningMessage(makeResult);
            //链接监控情况写入监控文件中
            log.info(findResult);
        }
    }
}
