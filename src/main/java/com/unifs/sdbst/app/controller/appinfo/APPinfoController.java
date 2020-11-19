package com.unifs.sdbst.app.controller.appinfo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.unifs.sdbst.app.base.Resp;
import com.unifs.sdbst.app.bean.appinfo.AndroidVersion;
import com.unifs.sdbst.app.bean.appinfo.AppVersion;
import com.unifs.sdbst.app.enums.RespCode;
import com.unifs.sdbst.app.service.appinfo.AppinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping(value = "app/info")
public class APPinfoController {

    @Autowired
    private AppinfoService appinfoService;



    @RequestMapping(value = "getCurrentVersion")
    @ResponseBody
    public String getCurrentVersion(AppVersion appVersion, HttpServletRequest request, HttpServletResponse response) {

        List<AppVersion> list =(List<AppVersion>)request.getSession().getAttribute("CurrentVersion");

        if(list == null || list.size() == 0){
            List<AppVersion> version = appinfoService.findList(appVersion);
            request.getSession().setAttribute("CurrentVersion",version);
            Resp entity = new Resp(RespCode.SUCCESS,version);
            String jsonObject = JSON.toJSONString(entity);
//            System.out.println(jsonObject);

            return  jsonObject;
        }else {
            Resp entity = new Resp(RespCode.SUCCESS,list);
            String jsonObject = JSON.toJSONString(entity);
//            System.out.println(jsonObject);

            return  jsonObject;
        }
    }

    @RequestMapping(value = "getAndroidVersion")
    @ResponseBody
    public String getAndroidVersion(HttpServletRequest request) {

        List<AndroidVersion> versions = appinfoService.selectAndroidAll();
//            request.getSession().setAttribute("AndroidVersion",versions.get(0));
//            System.out.println(versions.get(0).toString());
//            Resp entity = new Resp(RespCode.SUCCESS,version);
//            String jsonObject = JSON.toJSONString(entity);
        AndroidVersion androidVersion = versions.get(0);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Code",0);
        jsonObject.put("Msg","成功");
        jsonObject.put("UpdateStatus",Integer.parseInt(androidVersion.getUpdatestatus()));
        jsonObject.put("VersionCode",Integer.parseInt(androidVersion.getVersioncode()));
        jsonObject.put("VersionName",androidVersion.getVersionname());
        jsonObject.put("ModifyContent",androidVersion.getModifycontent());
        jsonObject.put("DownloadUrl",androidVersion.getDownloadurl());
        jsonObject.put("ApkSize",Integer.parseInt(androidVersion.getApksize()));
        jsonObject.put("ApkMd5",androidVersion.getApkmd5());
        request.getSession().setAttribute("AndroidVersion",jsonObject);
//            String jsonObject = JSON.toJSONString();
//            System.out.println(jsonObject);
        return  jsonObject.toJSONString();

        /***
         *  JSONObject jsonObject = new JSONObject();
         *             jsonObject.put("Code",0);
         *             jsonObject.put("Msg","");
         *             jsonObject.put("UpdateStatus",1);
         *             jsonObject.put("VersionCode",3);
         *             jsonObject.put("VersionName",version);
         *             jsonObject.put("ModifyContent","测试升级");
         *             jsonObject.put("DownloadUrl",appUrl);
         *             jsonObject.put("ApkSize",500000);
         *             jsonObject.put("ApkMd5","123s");
         * {
         *   "Code": 0, //0代表请求成功，非0代表失败
         *   "Msg": "", //请求出错的信息
         *   "UpdateStatus": 1, //0代表不更新，1代表有版本更新，不需要强制升级，2代表有版本更新，需要强制升级
         *   "VersionCode": 3,
         *   "VersionName": "1.0.2",
         *   "ModifyContent": "1、优化api接口。\r\n2、添加使用demo演示。\r\n3、新增自定义更新服务API接口。\r\n4、优化更新提示界面。",
         *   "DownloadUrl": "https://raw.githubusercontent.com/xuexiangjys/XUpdate/master/apk/xupdate_demo_1.0.2.apk",
         *   "ApkSize": 2048
         *   "ApkMd5": "..."  //md5值没有的话，就无法保证apk是否完整，每次都会重新下载。
         * }
         */

//        List<AppVersion> version = appinfoService.findAndroid(appVersion);
//        String jsonObject = JSON.toJSONString(version.get(0));
//        return  jsonObject;
    }
//    /**
//     * 手机端App更新接口
//     * @param request
//     * @param response
//     */
//    @RequestMapping(value = "update")
//    public void update(HttpServletRequest request, HttpServletResponse response){
//        String appName = request.getParameter("appName");
//
//        AppVersion app = appVersionDao.getNewVersion(appName);
//
//        renderString(response, app);
//    }
}
