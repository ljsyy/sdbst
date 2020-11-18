package com.unifs.sdbst;

import com.alibaba.fastjson.JSONObject;
import com.unifs.sdbst.app.utils.HttpUtil;
import com.unifs.sdbst.app.utils.IdGen;
import com.unifs.sdbst.app.utils.MD5;
import org.junit.Test;

import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class Test2 {


    //提问，流程
    @Test
    public void question() {
        String callbackUrl = "http://19.202.137.130:9060/yc-mediagw/interface";
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
            String result = HttpUtil.sendPost("http://19.202.137.130:9060/yc-mediagw/interface", "timestamp=" + timestamp + "&sign=" +
                    sginStr + "&data=" + data.toJSONString() + "&callbackUrl=" + callbackUrl + "&serialId=" + serialId
                    + "&entId=" + entId + "&channelKey=" + channelKey);
            System.out.println("result:" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成MD5串
     *
     * @param params
     * @param key
     * @return
     */

    private static String getSign(Map<String, String> params, final String key) {
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


}
