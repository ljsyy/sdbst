package com.unifs.sdbst.app.controller.user;

import com.alibaba.fastjson.JSON;
import com.unifs.sdbst.app.annotation.ControlLog;
import com.unifs.sdbst.app.annotation.FormCommit;
import com.unifs.sdbst.app.base.Resp;
import com.unifs.sdbst.app.bean.user.Advice;
import com.unifs.sdbst.app.bean.user.User;
import com.unifs.sdbst.app.enums.RespCode;
import com.unifs.sdbst.app.service.user.AdviceService;
import com.unifs.sdbst.app.utils.CookieUtil;
import com.unifs.sdbst.app.utils.RedisUtil;
import com.unifs.sdbst.app.utils.TypeTransformUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @version V1.0
 * @title: PersonalController
 * @projectName sdbst
 * @description: 个人
 * @author： 张恭雨
 * @date 2019/9/17 9:42
 */
@Controller
@RequestMapping(value = "/app/personal")
public class PersonalController {
    @Autowired
    private AdviceService adviceService;

    private Resp resp;

    @Autowired
    private RedisUtil redisUtil;
    //关于软件
    @ControlLog(operateType = "/about", context = "关于软件")       //日志记录注解
    @RequestMapping(value = "/about")
    public String about() {
        return "app/user/about/about";
    }
    @RequestMapping(value = "/aboutMe")
    public String aboutMe(String num, Model model){
        model.addAttribute("num",num);
        return "app/user/about/aboutme";
    }

    //投诉建议
    @ControlLog(operateType = "/advice", context = "投诉建议")       //日志记录注解
    @RequestMapping(value = "/advice")
    public String advice(HttpServletRequest request, Model model) throws Exception {
        //获取当前登录用户的手机号码
        String userId=CookieUtil.getCookie(request,"userId");
        if(!StringUtils.isEmpty(userId)){
            String userStr=redisUtil.get(userId);
            Map userMap = JSON.parseObject(userStr);
            User user=(User) TypeTransformUtil.mapToObject(userMap, User.class);
            model.addAttribute("phone",user.getPhone());
        }
        return "app/user/advice";
    }

    //投诉建议提交
    @FormCommit
    @ControlLog(operateType = "/adviceSubmit", context = "投诉建议提交")       //日志记录注解
    @RequestMapping(value = "/adviceSubmit",method = RequestMethod.POST)
    @ResponseBody
    public Resp adviceSubmit(Advice Advice) {
        //保存数据库
        adviceService.save(Advice);
        resp=new Resp(RespCode.SUCCESS);
        return resp;
    }

    //个人中心分享
    @ControlLog(operateType = "/myShare", context = "分享")       //日志记录注解
    @RequestMapping(value = "/myShare")
    public String myShare() {
        return "/app/user/myShare";
    }
}
