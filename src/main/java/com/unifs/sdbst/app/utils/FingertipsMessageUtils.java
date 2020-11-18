package com.unifs.sdbst.app.utils;

import com.alibaba.fastjson.JSONObject;
import com.unifs.sdbst.app.bean.fingertips.FingertipsMessage;
import com.unifs.sdbst.app.common.constant.GlobalURL;
import com.unifs.sdbst.app.service.fingertips.FingertipsMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class FingertipsMessageUtils {

    @Value("${zhijianban.item.url}")
    private String itemUrl;
    @Value("${zhijianban.operation.url}")
    private String operUrl;

    @Autowired
    private FingertipsMessageService fingertipsMessageService;

//    更新status、statusChangeTime
    public void updateStatus(){
        List<FingertipsMessage> list=fingertipsMessageService.needUpdate();
        System.out.println("需要更新列表获取成功");
        for (int i=0;i<list.size();i++){
            FingertipsMessage fingertipsMessage=new FingertipsMessage();
            String id=list.get(i).getId();
            String caseid=list.get(i).getCaseid();
            Map map = new HashMap();
            map.put("caseId", caseid);
            String string = FingertipsHttpUtils.httpRequestToString(operUrl + "/case/detail", "get", map);
            System.out.println(caseid+"状态获取成功");
            JSONObject jsonObject =  JSONObject.parseObject(string);
            if ("200".equals(jsonObject.getString("code"))){
                JSONObject data = jsonObject.getJSONObject("data");

                String status = data.getString("status");
                String  matterName= data.getJSONObject("matterInformationVO").getString("matterName");
                Date statusChangeTime = null;
                try {
                    statusChangeTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(data.getString("statusChangeTime"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String localStatus=list.get(i).getStatus();
                Date localChangeTime=list.get(i).getStatuschangetime();
                String casename=list.get(i).getCaseName();

                try {
                    if (localStatus==null||!(localStatus.equals(status))||localChangeTime==null||casename==null){
                        fingertipsMessage.setStatuschangetime(statusChangeTime);
                        fingertipsMessage.setStatus(status);
                        fingertipsMessage.setId(id);
                        fingertipsMessage.setCaseName(matterName);
                        //  更新caseID的状态
                        fingertipsMessageService.updateByCaseId(fingertipsMessage);
                        System.out.println("更新caseID"+caseid+"的状态");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }





            }
        }

    }


//    获取指定caseid的状态
    public Map queryStatusById(String caseID){
        Map map = new HashMap();
        map.put("caseId", caseID);
        String string = FingertipsHttpUtils.httpRequestToString(operUrl + "/case/detail", "get", map);
        JSONObject jsonObject =  JSONObject.parseObject(string);
        if ("200".equals(jsonObject.getString("code"))){
            JSONObject data =  JSONObject.parseObject(jsonObject.getString("data"));
            JSONObject status=JSONObject.parseObject(data.getString("status"));
            JSONObject statusChangeTime=JSONObject.parseObject(data.getString("statusChangeTime"));
            map.put("status",status);
            map.put("statusChangeTime",statusChangeTime);
        }
        return map;
    }


//    发送短信
    public void sendMessage() throws UnsupportedEncodingException {
        List<FingertipsMessage> list=fingertipsMessageService.needSend();
        for (int i=0;i<list.size();i++){
            StringBuffer content = new StringBuffer();
            String status=list.get(i).getStatus().trim();
            String name=list.get(i).getName();
//            String phone="18688286030";
            String phone=list.get(i).getAgentphone().trim();
            String caseName=list.get(i).getCaseName();
            content.append("尊敬的"+name+"先生/女士:");
            content.append("您的办事事项"+caseName+"已"+status);
            content.append("感谢您使用i顺德指尖办，如需了解更多办事信息，可登录i顺德APP进行查看!");
            System.out.println(phone+content);
            try {
                String result=HttpUtil.sendPost(GlobalURL.SMS_BASE_URL + "&phones=" + phone + "&content=" + URLEncoder.encode(content.toString(), "gb2312")
                        +"&sendUserName=&sendUserUuid=&sendDepUuid=&sendDepName=&relateDocUuid=" + IdGen.uuid() + "&sendPhone=", null);
                if (result!=null){
                    FingertipsMessage fingertipsMessage=new FingertipsMessage();
                    fingertipsMessage.setMessagetime(new Date());
                    fingertipsMessage.setId(list.get(i).getId());
                    fingertipsMessageService.updateByCaseId(fingertipsMessage);}

            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

}
