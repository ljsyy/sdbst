package com.unifs.sdbst.app.controller.ememergency;

import com.alibaba.fastjson.JSONObject;
import com.unifs.sdbst.app.utils.FingertipsHttpUtils;
import com.unifs.sdbst.app.utils.HttpUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/app/emergency")
public class EmergencyController {
//    调试用
//    private String emergencyUrl="http://19.202.137.139:8080/admin/emergency/isd/list";
//    private String emergencyDetailUrl="http://19.202.137.139:8080/admin/emergency/isd/mobile";

    @RequestMapping(value = "/guide")
    public String webGuide(Model model,String type){
        model.addAttribute("type",type);
        return "/app/emergency/emergency";
    }


    @RequestMapping(value = "/list")
    @ResponseBody
    public JSONObject getList(String type){
//    调试用
//        JSONObject object=new JSONObject();
//        String url =emergencyUrl+"?type="+type;
//        String string = FingertipsHttpUtils.sendPostJson(url, object);

        String url="http://19.202.134.136/a/messagepublish/mobile/isd/list?type="+type;
        String string = HttpUtil.sendPost(url, null);

        if (string.contains(" 您的操作权限不足！")){
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("code",2000);
            jsonObject1.put("Msg","应急平台维护中，请稍后再试！");
            return jsonObject1;
        }
        JSONObject jsonObject = JSONObject.parseObject(string);
        return jsonObject;
    }




    @RequestMapping(value = "/detail")
    public String getDetail(String warnid, Model model,HttpServletRequest request){
        model.addAttribute("warnid",warnid);
        return "/app/emergency/emergencydetail";
    }

    @RequestMapping(value = "/word")
    @ResponseBody
    public JSONObject getDetailEnd(String warnid, Model model,HttpServletRequest request){
//    调试用
//        JSONObject object=new JSONObject();
//        String detailUrl =emergencyDetailUrl+"?warnid="+warnid;
//        String string = FingertipsHttpUtils.sendPostJson(detailUrl, object);

        String url="http://19.202.134.136/a/messagepublish/mobile/detail?warnid="+warnid;
        String string = HttpUtil.sendPost(url, null);
        if (string.contains(" 您的操作权限不足！")){
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("code",2000);
            jsonObject1.put("Msg","应急平台维护中，请稍后再试！");
            return jsonObject1;
        }
        JSONObject jsonObject = JSONObject.parseObject(string);
        return jsonObject;
    }


}
