package com.unifs.sdbst.app.controller.gaffairs;

import com.alibaba.fastjson.JSONObject;
import com.unifs.sdbst.app.base.Resp;
import com.unifs.sdbst.app.bean.gaffairs.CallbackVO;
import com.unifs.sdbst.app.enums.RespCode;
import com.unifs.sdbst.app.utils.HttpUtil;
import com.unifs.sdbst.app.utils.IdGen;
import com.unifs.sdbst.app.utils.MD5;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @version V1.0
 * @title: OnlineAdvisoryController
 * @projectName sdbst
 * @description: 在线咨询
 * @author： 张恭雨
 * @date 2020/4/15 15:15
 */
@Controller
@RequestMapping(value = "/onlineAdvisory")
public class OnlineAdvisoryController {
    @Value("${online.advisory.url}")
    private String url;

    private static String callbackUrl = "http://isd3.shunde.gov.cn/sdbst/onlineAdvisory/callbackMsg";

    /*发送消息*/
    @PostMapping(value = "/sendMsg")
    @ResponseBody
    public Resp sendMsg() {
        String channelKey = "webchat_channel";
        String entId = "12345";
        String key = "123456";
        String serialId = IdGen.uuid();
        String timestamp = System.currentTimeMillis() + "";
        try {
            callbackUrl = URLEncoder.encode(callbackUrl, "utf-8");
            JSONObject userInfo = new JSONObject();
            userInfo.put("accountType", "openid");
            userInfo.put("account", "ofmG7uGYH1bVSgUnCh0Ysq86Fw08");
            userInfo.put("keyCode", "1");//直接接入对应的人工或机器人按键。
            JSONObject data = new JSONObject();
            data.put("sessionId", "ofmG7uGYH1bVSgUnCh0Ysq86Fw08");
            data.put("msgType", "text");
            data.put("msgContent", "321323");
            data.put("bizType", " visitor");
            data.put("userInfo", userInfo.toJSONString());
            SortedMap<String, String> sianMap = new TreeMap<String, String>();
            sianMap.put("callbackUrl", callbackUrl);      //请求消息回调地址,必须是要UTF-8编码之后的。
            sianMap.put("serialId", serialId);         //请求序列号ID，全局唯一
            sianMap.put("data", data.toJSONString());      //请求消息内容
            sianMap.put("entId", entId);     //企业ID
            sianMap.put("channelKey", channelKey);     //渠道Key
            sianMap.put("timestamp", timestamp);  //时间戳
            String sginStr = getSign(sianMap, key);
            System.out.println(sginStr);
            String result = HttpUtil.sendPost(url, "timestamp=" + timestamp + "&sign=" +
                    sginStr + "&data=" + data.toJSONString() + "&callbackUrl=" + callbackUrl + "&serialId=" + serialId
                    + "&entId=" + entId + "&channelKey=" + channelKey);
            System.out.println("result:" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Resp(RespCode.SUCCESS);
    }

    /*消息回调接口*/
    @PostMapping(value = "/callbackMsg")
    @ResponseBody
    public Resp callbackMsg(CallbackVO vo) {
        System.out.println("进入回调接口！");
        System.out.println(vo.toString());
        return new Resp(RespCode.SUCCESS);
    }

    private String getSign(Map<String, String> params, final String key) {
        final StringBuilder sb = new StringBuilder();
        sb.setLength(0);
        int k = 0;
        for (Iterator<String> i = params.keySet().iterator(); i.hasNext(); ++k) {
            String name = i.next();
            sb.append(k == 0 ? "" : '&').append(name).append('=').append(params.get(name));
        }
        sb.append('&').append("key").append('=').append(key);
        System.out.println("crypt before Data->" + sb.toString());
        return MD5.md5(sb.toString()).toUpperCase();
    }

    @GetMapping(value = "/chat")
    public String chat(){
        return "/app/gaffairs/chat";
    }
}
