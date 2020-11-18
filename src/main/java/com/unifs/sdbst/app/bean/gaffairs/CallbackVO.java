package com.unifs.sdbst.app.bean.gaffairs;

import lombok.Data;
import lombok.ToString;

/**
 * @version V1.0
 * @title: CallbackVO
 * @projectName sdbst
 * @description: 回调参数实体
 * @author： 张恭雨
 * @date 2020/4/15 15:34
 */
@Data
@ToString
public class CallbackVO {
    private String serialId;
    private String channelKey;
    private String entId;
    private String timestamp;
    private String event;
    private String data;
    private String sign;
}
