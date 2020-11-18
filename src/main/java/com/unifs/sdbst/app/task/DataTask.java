package com.unifs.sdbst.app.task;

import com.alibaba.fastjson.JSONObject;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import com.unifs.sdbst.app.bean.fingertips.FingertipsEMS;
import com.unifs.sdbst.app.bean.server.HardLog;
import com.unifs.sdbst.app.bean.server.ServerInfo;
import com.unifs.sdbst.app.service.ServerService;
import com.unifs.sdbst.app.service.data.DataService;
import com.unifs.sdbst.app.service.fingertips.FingertipsService;
import com.unifs.sdbst.app.service.menus.AppMenuService;
import com.unifs.sdbst.app.utils.FingertipsHttpUtils;
import com.unifs.sdbst.app.utils.HttpUtil;
import com.unifs.sdbst.app.utils.PropertiesUtil;
import com.unifs.sdbst.app.utils.SftpUtil;
import jdk.nashorn.internal.runtime.logging.Logger;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @version V1.0
 * @title: DataTask
 * @projectName sdbst
 * @description: data数据缓存定时任务
 * @author： 张恭雨
 * @date 2019/10/24 15:03
 */
@Configuration
@EnableScheduling   //定时任务
public class DataTask {
    @Autowired
    private DataService dataService;
    @Value("${task.base.url}")
    private String basePath;

    @Autowired
    private FingertipsService fingertipsService;

    @Value("${zhijianban.operation.url}")
    private String operUrl;

    //缓存清除以及重新加载定时任务
//   @Scheduled(cron = "0 0 2 * * ?")  //定时时间，每天凌晨两点执行
    public void cacheClear() {
        dataService.clear();
    }

    //调用接口加载缓存
//   @Scheduled(cron = "0 0 3 * * ?")  //定时时间，每天凌晨三点执行
    public void cacheLoad() {
        String fileName = "interface-url.properties";
        Set<String> names = PropertiesUtil.getNames(fileName);
        //遍历集合
        for (String name : names) {
            //发起请求
            HttpUtil.sendGet(basePath + PropertiesUtil.getKey(fileName, name), "", "");
        }
    }
//    @Scheduled(cron = "0 0 10 * * ?")  //定时时间，每天凌晨4点执行
    public void sendEMSTask(){
        List<FingertipsEMS> list = fingertipsService.getFingertipsEMSByNotEms();//获取所有没调用EMS接口的订单
        for (int i = 0; i < list.size(); i++) {
            FingertipsEMS fingertipsEMS = list.get(i);
            Map map = new HashMap();
            map.put("caseId", fingertipsEMS.getCaseId());
            String string = FingertipsHttpUtils.httpRequestToString(operUrl + "/case/detail", "get", map);

            JSONObject jsonObject = JSONObject.parseObject(string);

            if ("200".equals(jsonObject.getString("code"))){
                JSONObject data = jsonObject.getJSONObject("data");
                String status = data.getString("status");
                System.out.println("接口调用成功"+status);
                fingertipsEMS.setCol1(status);
                fingertipsEMS.setCol2((new Date()).toString());
                fingertipsService.updateStatusById(fingertipsEMS);
                if ("预受理".equals(status)){
    //揽收地址信息（上门取件地址）
                    JSONObject collect = JSONObject.parseObject(fingertipsEMS.getCollects());
    //寄件人地址信息（面单使用）
                    JSONObject sender = JSONObject.parseObject(fingertipsEMS.getSender());
    //收件人地址
                    JSONObject receiver = JSONObject.parseObject(fingertipsEMS.getReceiver());
                    JSONObject gotInfo = new JSONObject();
                    gotInfo.put("collect",collect);
                    gotInfo.put("sender",sender);
                    gotInfo.put("receiver",receiver);
                    gotInfo.put("orderType",2);
                    gotInfo.put("remark",fingertipsEMS.getRemark());

                    gotInfo.put("txLogisticID",fingertipsEMS.getId());
                    gotInfo.put("custCode","90000011275444");
                    fingertipsService.createOrder(gotInfo);
                }
            }
        }
    }



}
