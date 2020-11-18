package com.unifs.sdbst.app.controller.push;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.unifs.sdbst.app.base.Resp;
import com.unifs.sdbst.app.bean.push.AliPushUser;
import com.unifs.sdbst.app.enums.RespCode;
import com.unifs.sdbst.app.service.push.AliPushService;
import com.unifs.sdbst.app.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping(value = "/app/push")
public class AliPushController {

    @Autowired
    private AliPushService aliPushService;
    private Resp resp;

    @RequestMapping(value = "/addPushUser")
    public Resp addPushUser(String info, HttpServletRequest request){
        System.out.println(info);
        if (info == null || "".equals(info)) {
            resp = new Resp(RespCode.DEFAULT);
            resp.setMsg("参数不能为空！");
            return resp;
        }
        JSONObject jsonObject = JSON.parseObject(info);

        String userId = jsonObject.getString("userId");
        String deviceId = jsonObject.getString("deviceId");
        String alive = jsonObject.getString("alive");
        String mobileType = jsonObject.getString("mobileType");
        String mobileTypeDetail = jsonObject.getString("mobileTypeDetail");

        if (userId == null || "".equals(userId)) {
            resp = new Resp(RespCode.DEFAULT);
            resp.setMsg("用户ID不能为空！");
            return resp;
        }
        if (deviceId == null || "".equals(deviceId)) {
            resp = new Resp(RespCode.DEFAULT);
            resp.setMsg("deviceId不能为空！");
            return resp;
        }

        AliPushUser aliPushUser=new AliPushUser();
        aliPushUser.setId(UUID.randomUUID().toString().replaceAll("-", ""));

        aliPushUser.setUpdateDate(new Date());
        aliPushUser.setUserId(userId);
        aliPushUser.setDeviceId(deviceId);
        aliPushUser.setAlive(alive);
        aliPushUser.setMobileType(mobileType);
        aliPushUser.setMobileTypeDetail(mobileTypeDetail);
        aliPushUser.setLoginIp(HttpUtil.getClientIp(request));

        int usercount=aliPushService.queryPushUser(aliPushUser);
        if (usercount==0){
            aliPushUser.setCreateDate(new Date());
            int count=aliPushService.addPushUser(aliPushUser);
            if (count>0){
                resp = new Resp(RespCode.SUCCESS);
                resp.setMsg("新增用户成功！");
                return resp;
            }else {
                resp = new Resp(RespCode.DEFAULT);
                resp.setMsg("新增用户失败！");
                return resp;
            }
        }else {
            int count=aliPushService.updatePushUser(aliPushUser);
            if (count>0){
                resp = new Resp(RespCode.SUCCESS);
                resp.setMsg("更新用户deviceId成功！");
                return resp;
            }else {
                resp = new Resp(RespCode.DEFAULT);
                resp.setMsg("更新用户deviceId失败！");
                return resp;
            }
        }


    }

    @RequestMapping(value = "/updatePushUser")
    public Resp updatePushUser(String info, HttpServletRequest request){
        if (info == null || "".equals(info)) {
            resp = new Resp(RespCode.DEFAULT);
            resp.setMsg("参数不能为空！");
            return resp;
        }
        JSONObject jsonObject = JSON.parseObject(info);
        String userId = jsonObject.getString("userId");
        String deviceId = jsonObject.getString("deviceId");
        String alive = jsonObject.getString("alive");
        String mobileType = jsonObject.getString("mobileType");
        String mobileTypeDetail = jsonObject.getString("mobileTypeDetail");

        if (userId == null || "".equals(userId)) {
            resp = new Resp(RespCode.DEFAULT);
            resp.setMsg("用户ID不能为空！");
            return resp;
        }
        if (deviceId == null || "".equals(deviceId)) {
            resp = new Resp(RespCode.DEFAULT);
            resp.setMsg("deviceId不能为空！");
            return resp;
        }

        AliPushUser aliPushUser=new AliPushUser();

        aliPushUser.setUserId(userId);
        aliPushUser.setDeviceId(deviceId);
        aliPushUser.setAlive(alive);
        aliPushUser.setMobileType(mobileType);
        aliPushUser.setMobileTypeDetail(mobileTypeDetail);
        aliPushUser.setLoginIp(HttpUtil.getClientIp(request));
        aliPushUser.setUpdateDate(new Date());

        int count=aliPushService.updatePushUser(aliPushUser);
        if (count>0){
            resp = new Resp(RespCode.SUCCESS);
            return resp;
        }else {
            resp = new Resp(RespCode.DEFAULT);
            return resp;
        }
    }
}
