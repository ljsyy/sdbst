package com.unifs.sdbst.app.controller.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.unifs.sdbst.app.annotation.ControlLog;
import com.unifs.sdbst.app.annotation.FormCommit;
import com.unifs.sdbst.app.base.Resp;
import com.unifs.sdbst.app.bean.user.Advice;
import com.unifs.sdbst.app.bean.user.User;
import com.unifs.sdbst.app.dao.primary.user.UserMapper;
import com.unifs.sdbst.app.enums.RespCode;
import com.unifs.sdbst.app.service.user.AdviceService;
import com.unifs.sdbst.app.service.user.UserService;
import com.unifs.sdbst.app.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

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
    private UserMapper userMapper;

    @Value("shunde8180buffet")
    private String key;
    @Autowired
    private UserService userService;

    @Value("${aes.key}")
    private String aesKey;

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
    @ControlLog(operateType = "/adviceNew", context = "投诉建议")       //日志记录注解
    @RequestMapping(value = "/adviceNew")
    public String advice(HttpServletRequest request, Model model,@RequestParam(name = "link",required = false) String link,
                         @RequestParam(name = "urlparams",required = false) String urlparams) throws Exception {
        //获取当前登录用户的手机号码
        String userId=CookieUtil.getCookie(request,"userId");
        if(!StringUtils.isEmpty(userId)){
            String userStr=redisUtil.get(userId);
            Map userMap = JSON.parseObject(userStr);
            User user=(User) TypeTransformUtil.mapToObject(userMap, User.class);
            model.addAttribute("phone",user.getPhone());
        }
        model.addAttribute("link",link);
        model.addAttribute("urlparams",urlparams);
        return "app/user/advice";
    }

    //投诉建议入口
    @RequestMapping(value = "/advice")
    public String adviceNew(HttpServletRequest request, Model model,@RequestParam(name = "link",required = false) String link) throws Exception {
        //获取当前登录用户的手机号码
        String userId=CookieUtil.getCookie(request,"userId");
        if(!StringUtils.isEmpty(userId)){
            String userStr=redisUtil.get(userId);
            Map userMap = JSON.parseObject(userStr);
            User user=(User) TypeTransformUtil.mapToObject(userMap, User.class);
            model.addAttribute("phone",user.getPhone());
        }
        model.addAttribute("link",link);
        return "app/user/advicenew";
    }

    //app投诉建议入口
    @RequestMapping(value = "/appAdvice")
    public String appAdvice(HttpServletRequest request, Model model,@RequestParam(name = "link",required = false) String link,
                            @RequestParam(name = "urlparams",required = false) String urlparams) throws Exception {
        //获取当前登录用户的手机号码
        String userId=CookieUtil.getCookie(request,"userId");
        if(!StringUtils.isEmpty(userId)){
            String userStr=redisUtil.get(userId);
            Map userMap = JSON.parseObject(userStr);
            User user=(User) TypeTransformUtil.mapToObject(userMap, User.class);
            model.addAttribute("phone",user.getPhone());
        }
        model.addAttribute("link",link);
        model.addAttribute("urlparams",urlparams);
        return "app/user/appAdvice";
    }


    @RequestMapping(value = "/MyAdvice")
    public ModelAndView MyAdvice(Advice Advice,HttpServletRequest request, Model model,@RequestParam(name = "link",required = false) String link,
                                 @RequestParam(name = "urlparams",required = false) String urlparams) throws Exception {

        ModelAndView mav = new ModelAndView();
        List<Advice> adviceList=new ArrayList<Advice>();
        try{
            if(link != null && link.length()>0) {
                //用户信息
                String string = EncryptUtil.aesDecrypt(link, aesKey);
                JSONObject jsonObject = JSONObject.parseObject(string);
                String identity = (String)jsonObject.get("link_person_cid");
                String phone = (String)jsonObject.get("mobile");
                User user = userService.selectByFactor(phone, identity, null);
                if(user != null){
                    Advice.setUserId(user.getId());
                    adviceList=adviceService.selectAllByAdvice(Advice);
                }
            }
            if(urlparams!=null&&urlparams.length()>0&&"".equals(link)){
                urlparams = AES.decrypt(urlparams, key);
                JSONObject object = JSONObject.parseObject(urlparams);
                String mobile=object.getString("mobile");
                if(mobile!=null&&mobile.length()>0){
                    User user = userMapper.selectByPhone(mobile);
                    if(user != null){
                        Advice.setUserId(user.getId());
                        adviceList=adviceService.selectAllByAdvice(Advice);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        mav.addObject("adviceList",adviceList);
        mav.setViewName("app/user/myAdvice");
        return mav;
    }

    //投诉建议提交
    @FormCommit
    @ControlLog(operateType = "/adviceSubmit", context = "投诉建议提交")       //日志记录注解
    @RequestMapping(value = "/adviceSubmit",method = RequestMethod.POST)
    @ResponseBody
    public Resp adviceSubmit(Advice Advice,@RequestParam(name = "link",required = false) String link,
                             @RequestParam(name = "urlparams",required = false) String urlparams) {
        //保存数据库
        try{
            if(link != null && link.length()>0) {
                //用户信息
                String string = EncryptUtil.aesDecrypt(link, aesKey);
                JSONObject jsonObject = JSONObject.parseObject(string);
                String identity = (String)jsonObject.get("link_person_cid");
                String phone = (String)jsonObject.get("mobile");
                User user = userService.selectByFactor(phone, identity, null);
                if(user != null){
                    Advice.setUserId(user.getId());
                }
                if(!"".equals(phone))Advice.setPhone(phone);
            }
            if(urlparams!=null&&urlparams.length()>0&&"".equals(link)){
                urlparams = AES.decrypt(urlparams, key);
                JSONObject object = JSONObject.parseObject(urlparams);
                String mobile=object.getString("mobile");
                if(mobile!=null&&mobile.length()>0){
                    User user = userMapper.selectByPhone(mobile);
                    if(user != null){
                        Advice.setUserId(user.getId());
                    }
                    Advice.setPhone(mobile);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
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
