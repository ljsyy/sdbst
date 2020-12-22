package com.unifs.sdbst.app.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.unifs.sdbst.app.annotation.ControlLog;
import com.unifs.sdbst.app.annotation.FormCommit;
import com.unifs.sdbst.app.annotation.RequestLimit;
import com.unifs.sdbst.app.base.LogEntity;
import com.unifs.sdbst.app.base.RequestInfo;
import com.unifs.sdbst.app.base.Resp;
import com.unifs.sdbst.app.bean.user.User;
import com.unifs.sdbst.app.common.constant.GlobalURL;
import com.unifs.sdbst.app.enums.RespCode;
import com.unifs.sdbst.app.exception.MyException;
import com.unifs.sdbst.app.service.InterfaceUrlService;
import com.unifs.sdbst.app.service.LogService;
import com.unifs.sdbst.app.service.PublicService;
import com.unifs.sdbst.app.service.RequestInfoService;
import com.unifs.sdbst.app.service.user.UserService;
import com.unifs.sdbst.app.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * 公共接口
 *
 * @author 张恭雨
 */
@Controller
@RequestMapping("/public")
public class PublicController {

    private Resp resp;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private PublicService publicService;
    @Value("${aes.key}")
    private String aesKey;
    @Value("${AES.SUBSIDY}")
    private String subsidyKey;
    @Autowired
    private LogService logService;

    @Autowired
    private UserService userService;


    @RequestMapping("/codePage")
    public ModelAndView checkCode(String phone,String info){
        ModelAndView mav = new ModelAndView();
        mav.addObject("phone",phone);
        mav.addObject("user_info",info);
        mav.setViewName("app/user/codePage");
        return mav;
    }

    @RequestMapping("/checkCode")
    @ResponseBody
    public Resp checkCode(@RequestBody  Map<String,String> info, HttpServletRequest request){
        Resp resp = null;
        if(info == null){
            resp = new Resp(RespCode.DEFAULT);
            resp.setMsg("参数不能为空");
            return resp;
        }
        String phone = info.get("phone");
        String code = info.get("code");
        if(StringUtils.isBlank(code)){
            resp = new Resp(RespCode.DEFAULT);
            resp.setMsg("验证码不能为空");
            return resp;
        }
        if(StringUtils.isBlank(phone)){
            resp = new Resp(RespCode.DEFAULT);
            resp.setMsg("手机号不能为空");
            return resp;
        }
        String sysCode = redisUtil.get(phone);
        // 验证验证码是否过期
        if (StringUtils.isEmpty(sysCode)) {
            resp = new Resp(RespCode.DEFAULT);
            resp.setMsg("验证码已失效！请重新获取");
            return resp;
        }
        if(!sysCode.equals(code)){
            resp = new Resp(RespCode.DEFAULT);
            resp.setMsg("验证码错误！");
            return resp;
        }
        resp = new Resp(RespCode.SUCCESS);
        return  resp;
    }


    /**
     * 处理扫描结果
     * @param result 扫描结果
     * @return
     */
    @RequestMapping("/dealScanResult")
    @ResponseBody
    public String dealScanResult(String result,String loginFlag,HttpServletRequest request) {
        Resp resp = null;
        if(StringUtils.isBlank(result)){
            resp = new Resp(RespCode.DEFAULT);
            resp.setMsg("结果为空值");
            return JSON.toJSONString(resp);
        }
       // System.out.println(result);
        String info = "";
        try{
            info = AES.decrypt(result, "Shunde2020buffe^");
        }catch(Exception e){
            e.printStackTrace();
            resp = new Resp(RespCode.ERROR);
            return JSON.toJSONString(resp);
        }
        System.out.println(info);
        JSONObject jsonObject = JSONObject.parseObject(info);

        String businessType = jsonObject.getString("businessType");
        if(StringUtils.isNotBlank(businessType)){
            if("corpQRCode".equals(businessType)){ //企业用户登陆
                //用户姓名
                String link_person_name = jsonObject.getString("personName");
                //手机号
                String phone = jsonObject.getString("personPhone");
                //证件号
                String link_person_cid = jsonObject.getString("personIdentity");
                //企业名称
                String corpName = jsonObject.getString("corpName");
                //企业社会统一信用代码
                String corpCode = jsonObject.getString("corpCode");
                if(StringUtils.isBlank(link_person_cid)
                        || StringUtils.isBlank(phone)
                        || StringUtils.isBlank(link_person_name)
                        || StringUtils.isBlank(corpName)
                        || StringUtils.isBlank(corpCode)){
                    resp = new Resp(RespCode.DEFAULT);
                    resp.setMsg("参数不能为空");
                    return JSON.toJSONString(resp);
                }
                //查询用户
                Map<String,String> map = new HashMap<>();
                User user = userService.selectByFactor(phone, corpCode, corpName);
                if(user != null){
                    //更新为企业用户，并返回客户端，短信验证，跳转短信验证输入页面
                    user.setLinkPersonCid(link_person_cid);
                    user.setLinkPersonName(link_person_name);
                    user.setAccountType("corp");
                    user.setClevel("L1");
                    if (user.getLoginCount() == null) {
                        user.setLoginCount(1);
                    } else {
                        user.setLoginCount(user.getLoginCount() + 1);
                    }
                    userService.update(user);

                    map.put("code","200");
                    map.put("data",JSON.toJSONString(user));
                    map.put("code_url","http://isd1.shunde.gov.cn/sdbst/public/codePage");
                    map.put("code_name","输入验证码");
                    map.put("businessType","corpQRCode_update");
                    return JSON.toJSONString(map);
                }else{
                    //注册新用户为企业用户，返回客户端，直接登录
                    User saveUser = new User();
                    saveUser.setLoginName(corpName);
                    saveUser.setPhone(phone);
                    //设置身份证，以及类型
                    saveUser.setIdentityNumber(corpCode);
                    saveUser.setIdentityType("mainland");
                    //saveUser.setAccount(swt.getAccount());
                    saveUser.setAccountType("corp");
                    saveUser.setClevel("L1");

                    saveUser.setLinkPersonCid(link_person_cid);

                    saveUser.setLinkPersonName(link_person_name);
                    //随机生成默认密码
                    String password = UUID.randomUUID().toString();
                    try {
                        saveUser.setPassword(EncryptUtil.entryptPassword(password));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    saveUser.preInsert();
                    saveUser.setLoginDate(new Date());
                    saveUser.setLoginCount(1);
                    saveUser.setLoginIp(HttpUtil.getClientIp(request));
                    //设置登录类型，android/ios
                    if ("android".equals(loginFlag) || "ios".equals(loginFlag)) {
                        saveUser.setLoginFlag(loginFlag);
                    }
                    userService.addUser(saveUser);
                    map.put("code","200");
                    map.put("data",JSON.toJSONString(saveUser));
                    map.put("businessType","corpQRCode_insert");
                    map.put("link_name","企业服务");
                    map.put("link_address","https://qyfw.shunde.gov.cn/sdtoon-enterprise-service-static/m/index.html#/");
                    return JSON.toJSONString(map);
                }
            }
        }

        return JSON.toJSONString(resp);
    }

    @FormCommit                     //防止数据重复提交
    @RequestMapping(value = "/sendQyLoginCode", method = RequestMethod.POST)
    @ResponseBody
    public Resp sendQyLoginCode(@RequestBody  Map<String,String> info, HttpServletRequest request) {
        LogEntity logEntity = new LogEntity();
        logEntity.setStartTime(new Date());
        if (info == null) {
            resp = new Resp(RespCode.DEFAULT);
            resp.setMsg("参数不能为空！");
            return resp;
        }

        String content = "";
        try {
            String phone = info.get("phone");
            StringBuffer codeBuffer = new StringBuffer();
            Random random = new Random();
            for (int i = 0; i < 6; i++) {
                int result = random.nextInt(10);
                codeBuffer.append(result);
            }
            String code = codeBuffer.toString();
           // System.out.println("phone： "+phone);
            if ("18707676996".equals(phone) || "12345678910".equals(phone)) {
                //如果是测试手机号，设置固定的验证码
                code = "888888";
            }
            content = "【i顺德】验证码" + code + "，10分钟内输入有效，请勿透露给其他人。";
            String result = "";
            result = HttpUtil.sendPost(GlobalURL.SMS_BASE_URL + "&phones=" + phone + "&content=" + URLEncoder.encode(content, "gb2312") + "&sendUserName=&sendUserUuid=&sendDepUuid=&sendDepName=&relateDocUuid=" + IdGen.uuid() + "&sendPhone=", null);
            System.out.println(result);
            //验证码存放在redis中并设置超时时间为10钟，
            redisUtil.set(phone, code, 10 * 60);
            //验证码发送日志记录
            logEntity.setTime(new Date());
            logEntity.setUsername(JSON.toJSONString(info));
            logEntity.setArgs("info:" + JSON.toJSONString(info));
            logEntity.setInterfacePath("/sendVerificationCode");
            logEntity.setStatus("成功！");
            logEntity.setContent(content);
            logEntity.setIp(HttpUtil.getClientIp(request));
            logService.saveLog(logEntity);

            resp = new Resp(RespCode.SUCCESS);
            resp.setMsg("验证码已发送！请注意查收！验证码有限时间为10分钟！");
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
            //失败日志记录
            logEntity.setTime(new Date());
            logEntity.setUsername(JSON.toJSONString(info));
            logEntity.setArgs("info:" + JSON.toJSONString(info));
            logEntity.setInterfacePath("/sendVerificationCode");
            logEntity.setStatus("失败！");
            logEntity.setContent(content);
            logEntity.setIp(HttpUtil.getClientIp(request));
            logService.saveLog(logEntity);

            resp = new Resp(RespCode.DEFAULT);
            resp.setMsg("发送失败！");
            return resp;
        }


    }

    //获取短信验证码接口
//    @ControlLog(operateType = "/sendVerificationCode", context = "获取验证码")
//    @RequestLimit(count = 5)        //限制同一接口每分钟n的访问次数
    @FormCommit                     //防止数据重复提交
    @RequestMapping(value = "/sendVerificationCode", method = RequestMethod.POST)
    @ResponseBody
    public Resp sendVerificationCode(String info, HttpServletRequest request) {
        LogEntity logEntity = new LogEntity();
        logEntity.setStartTime(new Date());

        //验证
        publicService.codeVerify(request);

        if (info == null || "".equals(info)) {
            resp = new Resp(RespCode.DEFAULT);
            resp.setMsg("参数不能为空！");
            return resp;
        }
        info = AES.decrypt(info, aesKey);
        JSONObject jsonObject = JSON.parseObject(info);
        String phone = jsonObject.getString("phone");

        /*//验证手机号是否正确
        if (!phone.matches("^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$")) {
            resp = new Resp(RespCode.DEFAULT);
            resp.setMsg("手机号不正确！");
            return resp;
        }*/

        String content = "";
        try {
            StringBuffer codeBuffer = new StringBuffer();
            Random random = new Random();
            for (int i = 0; i < 6; i++) {
                int result = random.nextInt(10);
                codeBuffer.append(result);
            }
            String code = codeBuffer.toString();
            if ("18688284239".equals(phone)) {
                //如果是测试手机号，设置固定的验证码
                code = "888888";
            }
            content = "【i顺德】验证码" + code + "，10分钟内输入有效，请勿透露给其他人。";
            String result = HttpUtil.sendPost(GlobalURL.SMS_BASE_URL + "&phones=" + phone + "&content=" + URLEncoder.encode(content, "gb2312") + "&sendUserName=&sendUserUuid=&sendDepUuid=&sendDepName=&relateDocUuid=" + IdGen.uuid() + "&sendPhone=", null);
            System.out.println(result);
            //验证码存放在redis中并设置超时时间为10钟，
            redisUtil.set(phone, code, 10 * 60);
            //验证码发送日志记录
            logEntity.setTime(new Date());
            logEntity.setUsername(phone);
            logEntity.setArgs("info:" + info);
            logEntity.setInterfacePath("/sendVerificationCode");
            logEntity.setStatus("成功！");
            logEntity.setContent(content);
            logEntity.setIp(HttpUtil.getClientIp(request));
            logService.saveLog(logEntity);

            resp = new Resp(RespCode.SUCCESS);
            resp.setMsg("验证码已发送！请注意查收！验证码有限时间为10分钟！");
            return resp;
        } catch (Exception e) {
            //失败日志记录
            logEntity.setTime(new Date());
            logEntity.setUsername(phone);
            logEntity.setArgs("info:" + info);
            logEntity.setInterfacePath("/sendVerificationCode");
            logEntity.setStatus("失败！");
            logEntity.setContent(content);
            logEntity.setIp(HttpUtil.getClientIp(request));
            logService.saveLog(logEntity);

            resp = new Resp(RespCode.DEFAULT);
            resp.setMsg("发送失败！");
            return resp;
        }
    }

    /*提供给企业补贴系统使用的短信接口*/
    @FormCommit                     //防止数据重复提交
    @RequestMapping(value = "/sendSms", method = RequestMethod.POST)
    @ResponseBody
    public Resp sendSms(String info, HttpServletRequest request) {
        LogEntity logEntity = new LogEntity();
        logEntity.setStartTime(new Date());
        //访问认证
        publicService.accessAuth(request);

        if (info == null || "".equals(info)) {
            resp = new Resp(RespCode.DEFAULT);
            resp.setMsg("参数不能为空！");
            return resp;
        }
        info = AES.decrypt(info, subsidyKey);
        JSONObject jsonObject = JSON.parseObject(info);
        String phone = jsonObject.getString("phone");
        String content = jsonObject.getString("content");
        try {
            String result = HttpUtil.sendPost(GlobalURL.SMS_BASE_URL + "&phones=" + phone + "&content=" + URLEncoder.encode(content, "gb2312") + "&sendUserName=&sendUserUuid=&sendDepUuid=&sendDepName=&relateDocUuid=" + IdGen.uuid() + "&sendPhone=", null);
            System.out.println(result);
            //验证码发送日志记录
            logEntity.setTime(new Date());
            logEntity.setUsername(phone);
            logEntity.setArgs("info:" + info);
            logEntity.setInterfacePath("/sendSms");
            logEntity.setStatus("成功！");
            logEntity.setContent(content);
            logEntity.setIp(HttpUtil.getClientIp(request));
            logService.saveLog(logEntity);
            resp = new Resp(RespCode.SUCCESS);
            resp.setMsg("发送成功!");
            return resp;
        } catch (Exception e) {
            //失败日志记录
            logEntity.setTime(new Date());
            logEntity.setUsername(phone);
            logEntity.setArgs("info:" + info);
            logEntity.setInterfacePath("/sendSms");
            logEntity.setStatus("失败！");
            logEntity.setContent(content);
            logEntity.setIp(HttpUtil.getClientIp(request));
            logService.saveLog(logEntity);

            resp = new Resp(RespCode.DEFAULT);
            resp.setMsg("发送失败！");
            return resp;
        }
    }

    //获取短信验证码接口
    @ControlLog(operateType = "/sendPhoneCode", context = "获取验证码")
    @RequestLimit(count = 5)
    @RequestMapping(value = "/sendPhoneCode", method = RequestMethod.POST)
    @ResponseBody
    public Resp sendPhoneCode(@NotNull String phone, HttpServletRequest request) {
//        resp = new Resp(RespCode.DEFAULT);
//        resp.setMsg("短信暂停使用！");
//        return resp;
        //验证
        //验证手机号是否正确
        if (!phone.matches("^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$")) {
            throw new MyException("手机号不正确！", 0);
        }
        try {
            StringBuffer codeBuffer = new StringBuffer();
            Random random = new Random();
            for (int i = 0; i < 6; i++) {
                int result = random.nextInt(10);
                codeBuffer.append(result);
            }
            String content = "【i顺德】验证码" + codeBuffer.toString() + "，10分钟内输入有效，请勿透露给其他人。";
            HttpUtil.sendPost(GlobalURL.SMS_BASE_URL + "&phones=" + phone + "&content=" + URLEncoder.encode(content, "gb2312") + "&sendUserName=&sendUserUuid=&sendDepUuid=&sendDepName=&relateDocUuid=" + IdGen.uuid() + "&sendPhone=", null);
            //验证码存放在redis中并设置超时时间为10分钟
            redisUtil.set(phone, codeBuffer.toString(), 10 * 60);
            System.out.println(content);
            resp = new Resp(RespCode.SUCCESS);
            resp.setMsg("验证码已发送！请注意查收！验证码有限时间为10分钟！");
            return resp;
        } catch (Exception e) {
            resp = new Resp(RespCode.DEFAULT);
            resp.setMsg("发送失败！");
            return resp;
        }
    }

    //时间列表
    @RequestMapping("/timeList")
    public String timeList(String titleName, Model model) {
        RequestInfo ri = new RequestInfo();

        String str;
        if (titleName.indexOf("@") >= 0) {
            str = titleName.substring(0, titleName.indexOf("@"));
        } else {
            str = titleName;
        }
        ri.setTitleName(str);
        ri.setHeadName(titleName);

        String url = InterfaceUrlService.getUrl(ri);
        ri.setUrl(url);
        model.addAttribute("ri", ri);
        return "app/government/timelist/list";
    }


    @RequestMapping(value = "/detail")
    public String detail(String id, String foldId, String choose, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("foldId", foldId);
        model.addAttribute("choose", choose);
        return "app/government/timelist/detail";
    }

    /**
     * @param function  功能名
     * @param :         json jsonp 网页 xml  /page:将返回一个页面/pagey：将返回一个页面，但json数据键不是data.obj
     * @param pageIndex 分页 页码
     * @param model
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/getList")
    public String getList(String function, Integer pageIndex, Model model) {
        RequestInfo ri = new RequestInfo();
        ri.setFunction(function);
        ri.setPageIndex(pageIndex);

        String url = RequestInfoService.getUrl(ri);
        if (url == null)
            return "/error/404";
        if (ri.getReturnType() != null && ri.getReturnType().equals("href")) {
            ri.setData("");
        } else {
            if (!"".equals(ri.getUrl())) {
                String json = HttpUtil.sendGet(ri.getUrl(), "", "");
                ri.setData(json);
            }
        }


        model.addAttribute("ri", ri);

        String fileName = function.substring(0, function.indexOf("/"));

        if (fileName.equals("petition") || fileName.equals("trade") || fileName.equals("inshunde")
                ) {
            return "/app/government/timelist/judgelist";
        } else if (fileName.equals("emergency")) {
            return "/app/government/timelist/emergencylist";
        } else if (fileName.equals("farmers") || fileName.equals("noheadfarmers")) {
            return "/app/government/timelist/farmerslist";
        }
        return "/app/government/" + fileName + "/list";
    }

    // 获取json数据   前端页面请求 GET
    @RequestMapping("/getJsonGet")
    @ResponseBody
    public String getJsonGet(String type, String url) {
        if (!"".equals(type)) {
            url = RequestInfoService.getUrl(type);
        }
        if ("".equals(url) || url == null) {
            throw new MyException("url不能为空");
        }
        url = url.replace("*", "&");
        url = url.replace("sdztc.shunde.gov.cn", "202.104.25.197");
        System.out.println(url);
//        String result = getJson(url, post);
        String result = HttpUtil.sendGet(url, "", "");
        return result;
    }

    // 获取json数据   控制器请求   后台请求 根据功能名
    @RequestMapping("/afterGetJson")
    @ResponseBody
    public String afterGetJson(String function, Integer pageIndex) throws UnsupportedEncodingException {
        String url = RequestInfoService.getUrlByFunction(function);
        if (url == null)
            return null;
        url = url + pageIndex;
        System.out.println(url);
        String result = HttpUtil.sendGet(url, "", "");
        System.out.println(result);
        return result;
    }

    // 获取json数据   POST/GET
    private String getJson(String url, Boolean post) throws UnsupportedEncodingException {
        String result;
        url = URLEncoder.encode(url, "UTF-8");
        if (post != null && post) {
            result = HttpUtil.sendPost(url, null);
        } else {
            result = HttpUtil.sendGet(url, null, "");
        }
        return result;
    }

    // 进入列表详情页的公共控制器
    @RequestMapping("getDetail")
    public String getDetail(String id, String fid, String headName, String titleName, String html, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("fid", fid);
        model.addAttribute("headName", headName);
        model.addAttribute("titleName", titleName);
        return html;
    }

    //跳转app下载页
    @RequestMapping(value = "/toDownload")
    public String toDownload() {
        return "/app/download";
    }

    /**
     * 　　* @description: app下载
     * 　　* @param ${tags}
     * 　　* @return ${return_type}
     * 　　* @throws
     * 　　* @author 张恭雨
     * 　　* @date 2019/9/12 15:38
     */
    @RequestMapping(value = {"/download"}, method = RequestMethod.GET)
    @ControlLog(context = "android客户端下载")
    public void download(@NotNull String type, HttpServletResponse response) throws UnsupportedEncodingException {
        //被下载的文件在服务器中的路径,
        String downloadFilePath = "/home/file/";
        String fileName = "";
        //判断下载类型
        if (type.equals("ios")) {
            //获取ios 文件
            fileName = "i顺德.ipa";
        } else if (type.equals("android")) {
            //获取android 文件
            fileName = "i顺德.apk";
        }

        File file = new File(downloadFilePath + fileName);
        if (file.exists()) {
            // 清空response
            response.reset();
            response.setHeader("Accept-Ranges", "bytes");
            response.setContentType("application/vnd.android.package-archive");
            //设置编码格式
            response.setCharacterEncoding("utf-8");
            // 设置文件大小
            response.addHeader("Content-Length", file.length() + "");
            response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "utf-8"));
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream outputStream = response.getOutputStream();
                int i = bis.read(buffer);


                while (i != -1) {
                    outputStream.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
    }
}
