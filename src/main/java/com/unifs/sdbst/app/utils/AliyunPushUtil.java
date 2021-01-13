package com.unifs.sdbst.app.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.push.model.v20160801.PushRequest;
import com.aliyuncs.push.model.v20160801.PushResponse;
import com.aliyuncs.utils.ParameterHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AliyunPushUtil {
    @Value("${isd.ailyn.push.appKey}")
    private Long appKey;
    @Value("${isd.ailyn.push.regionId}")
    private String regionId;
    @Value("${isd.ailyn.push.accessKeyId}")
    private String accessKeyId;
    @Value("${isd.ailyn.push.accessKeySecret}")
    private String accessKeySecret;

    /**
     * 阿里云推送高级接口
     * @param target 推送目标: DEVICE:推送给设备; ACCOUNT:推送给指定帐号,TAG:推送给自定义标签; ALIAS: 按别名推送; ALL: 全推
     * @param targetValue 根据Target来设定，如Target=DEVICE, 则对应的值为 设备id1,设备id2. 多个值使用逗号分隔.(帐号与设备有一次最多100个的限制)
     * @param url 点击弹窗后跳转路径
     * @param title 推送通知标题
     * @param body  推送通知内容
     * @param popupTitle    辅助通道推送通知标题，启用辅助通道时popupTitle不能为空
     * @param popupBody     辅助通道推送通知内容，启用辅助通道时popupBody不能为空
     * @param hour  离线消息保存小时数
     * @throws ClientException
     */
    public void pushAliyun(String target, String targetValue, String url, String title, String body, String popupTitle, String popupBody, int hour) throws ClientException {
        System.out.println("---pushAliyun---regionId:"+regionId+",accessKeyId:"+accessKeyId+",accessKeySecret:"+accessKeySecret);
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        PushRequest pushRequest = new PushRequest();
        // 推送目标
        pushRequest.setAppKey(appKey);
        pushRequest.setTarget(target); //推送目标: DEVICE:推送给设备; ACCOUNT:推送给指定帐号,TAG:推送给自定义标签; ALIAS: 按别名推送; ALL: 全推
        pushRequest.setTargetValue(targetValue); //根据Target来设定，如Target=DEVICE, 则对应的值为 设备id1,设备id2. 多个值使用逗号分隔.(帐号与设备有一次最多100个的限制)
        pushRequest.setDeviceType("ALL"); // 设备类型deviceType, iOS设备: "iOS"; Android设备: "ANDROID"; 全部: "ALL", 这是默认值.
        // 推送配置
        pushRequest.setPushType("NOTICE"); // MESSAGE:表示消息(默认), NOTICE:表示通知
        pushRequest.setTitle(title); // 消息的标题
        pushRequest.setBody(body); // 消息的内容
        // 推送配置: iOS
        pushRequest.setIOSBadge(5); // iOS应用图标右上角角标
        pushRequest.setIOSMusic("default"); // iOS通知声音
        pushRequest.setIOSApnsEnv("PRODUCT");//iOS的通知是通过APNs中心来发送的，需要填写对应的环境信息。'DEV': 表示开发环境 'PRODUCT': 表示生产环境
        pushRequest.setIOSRemind(true); //  消息推送时设备不在线（既与移动推送的服务端的长连接通道不通），则这条推送会做为通知，通过苹果的APNs通道送达一次。注意：**离线消息转通知仅适用于`生产环境`**
        pushRequest.setIOSRemindBody("PushRequest summary"); // iOS消息转通知时使用的iOS通知内容，仅当iOSApnsEnv=`PRODUCT` && iOSRemind为true时有效
        pushRequest.setIOSExtParameters("{\"k1\":\"ios\",\"k2\":\"v2\"}"); //通知的扩展属性(注意 : 该参数要以json map的格式传入,否则会解析出错)
        // 推送配置: Android
        pushRequest.setAndroidNotificationChannel("channel");// 指定notificaitonchannel id，Android 8.0以上的设备需要此设置才能显示通知
        pushRequest.setAndroidOpenType("ACTIVITY"); // 点击通知后动作 'APPLICATION': 打开应用 'ACTIVITY': 打开应用AndroidActivity 'URL': 打开URL 'NONE': 无跳转
        pushRequest.setAndroidNotifyType("SOUND"); // 通知的提醒方式 ‘VIBRATE': 振动  'SOUND': 声音 'DEFAULT': 声音和振动 'NONE': 不做处理，用户自定义
//        pushRequest.setAndroidOpenUrl("http://www.alibaba.com"); //点击通知后动作为'URL'时，可在此设置具体url地址
        pushRequest.setAndroidMusic("default"); // Android通知声音
        pushRequest.setAndroidActivity("com.cn.sdt.activity.NoticeActivity"); // Android收到推送后打开对应的ACTIVITY,仅当`AndroidOpenType="ACTIVITY"`有效
        if(!"".equals(popupTitle)&&!"".equals(popupBody)){
            pushRequest.setAndroidPopupActivity("com.cn.sdt.activity.AliPopupPushActivity"); //设置该参数后启动辅助弹窗功能, 此处指定通知点击后跳转的Activity（辅助弹窗的前提条件：1. 集成第三方辅助通道；2. StoreOffline参数设为true）
            pushRequest.setAndroidPopupTitle(popupTitle); //设置辅助弹窗通知的标题
            pushRequest.setAndroidPopupBody(popupBody); //设置辅助弹窗通知的内容
        }
        pushRequest.setAndroidNotificationBarType(50); //Android自定义通知栏样式，取值：1-100
        pushRequest.setAndroidNotificationBarPriority(2); //Android通知在通知栏展示时排列位置的优先级 -2 -1 0 1 2
        pushRequest.setAndroidExtParameters("{\"url\":\"" + url + "\"}"); //设定通知的扩展属性。(注意 : 该参数要以 json map 的格式传入,否则会解析出错)
        // 推送控制
//        final Date pushDate = new Date(System.currentTimeMillis() + 3600 * 1000); //用于定时发送。不设置缺省是立即发送。时间格式按照ISO8601标准表示，并需要使用UTC时间，格式为`YYYY-MM-DDThh:mm:ssZ`。
//        final String pushTime = ParameterHelper.getISO8601Time(pushDate);
//        pushRequest.setPushTime(pushTime); // 延后推送。可选，如果不设置表示立即推送
        pushRequest.setStoreOffline(true); // 离线消息是否保存,若保存, 在推送时候，用户即使不在线，下一次上线则会收到
        final String expireTime = ParameterHelper.getISO8601Time(new Date(System.currentTimeMillis() + hour * 3600 * 1000)); // 12小时后消息失效, 不会再发送
        pushRequest.setExpireTime(expireTime);
        // 短信融合通知s
//        pushRequest.setSmsTemplateName("SMS_1234567"); // 设置短信模板名
//        pushRequest.setSmsSignName("测试"); //设置短信签名
//        pushRequest.setSmsParams("name=Bob&code=123"); // 短信模板变量
//        pushRequest.setSmsSendPolicy(0); // 补发短信的策略，0 表示当设备未收到推送时补发
//        pushRequest.setSmsDelaySecs(120); // 两分钟未收到触发短信
        PushResponse pushResponse = client.getAcsResponse(pushRequest);
        System.out.printf("RequestId: %s, MessageId: %s\n",
                pushResponse.getRequestId(), pushResponse.getMessageId());
    }
}
