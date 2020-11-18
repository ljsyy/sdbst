package com.unifs.sdbst.app.controller.enterprise;

import com.alibaba.fastjson.JSONObject;
import com.unifs.sdbst.app.base.Resp;
import com.unifs.sdbst.app.bean.enterprise.EnterpriseReserve;
import com.unifs.sdbst.app.bean.enterprise.EnterpriseUser;
import com.unifs.sdbst.app.enums.RespCode;
import com.unifs.sdbst.app.service.enterprise.EnterpriseService;
import com.unifs.sdbst.app.utils.AES;
import com.unifs.sdbst.app.utils.CookieUtil;
import com.unifs.sdbst.app.utils.DateUtils;
import com.unifs.sdbst.app.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @version V1.0
 * @title: EnterpriseController
 * @projectName sdbst
 * @description: 企业口罩预约控制类
 * @author： 张恭雨
 * @date 2020/2/19 21:31
 */
@Controller
@RequestMapping(value = "/enterprise")
public class EnterpriseController {
    @Autowired
    private EnterpriseService enterpriseService;
    @Value("${AES.SUBSIDY}")
    private String key;

    private final String limitDate = "2020/04/02 00:00:00";

    @PostMapping(value = "/qyLogin")
    @ResponseBody
    public Resp login(@NotNull String creditCode, @NotNull String identityNumber, HttpServletRequest request, HttpServletResponse response) {
        EnterpriseUser user = enterpriseService.verifyLogin(creditCode, identityNumber);
        request.getSession().setAttribute("enterpriseUser", user);
        CookieUtil.sendCookie(response, "enterpriseUser", user.getCreditCode());

        //判断上次录入时间是4月2号前还是4月2号后
        EnterpriseReserve latestRecord = enterpriseService.getLatestEnterpriseReserveByCreditCode(creditCode);
        if (latestRecord != null) {
            List<EnterpriseReserve> list = null;
            Date latestDate = latestRecord.getCreateDate();
            //判断上次申请时间是否是4月2号后
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = null;
            try {
                date = simpleDateFormat.parse(limitDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (DateUtils.compareTime(latestDate, date)) {
                //上次申请时间是4月2号之后，按7天
                list = enterpriseService.getEnterpriseReserveByCreditCodeSeven(creditCode);
            } else {
                //上次申请时间是4月2号之前，还是3天
                list = enterpriseService.getEnterpriseReserveByCreditCode(creditCode);
            }
            if (list.size() > 0) {
                user.setIdentityNumber(String.valueOf(list.get(0).getNumberMasks()));
            } else {
                user.setIdentityNumber("");
            }
        } else {
            user.setIdentityNumber("");
        }

        Resp resp = new Resp(RespCode.SUCCESS);
        //隐藏身份证号信息
        resp.setData(user);
        return resp;
    }


    @PostMapping(value = "/reserveSubmit")
    @ResponseBody
    public Resp reserveSubmit(EnterpriseReserve reserve, HttpServletRequest request) {
        String creditCode = CookieUtil.getCookie(request, "enterpriseUser");
        if (StringUtils.isNullOrEmpty(creditCode)) {//无cookie，返回登录
            return new Resp(RespCode.DEFAULT);
        }
//        JSONObject jsonStr = JSONObject.parseObject(enterpriseUser);
//        String creditCode = jsonStr.getString("creditCode");
        if (!creditCode.equals(reserve.getCreditCode())) {//cookie 信息不对，返回登录
            return new Resp(RespCode.DEFAULT);
        }
        //判断上次录入时间是4月2号前还是4月2号后
        EnterpriseReserve latestRecord = enterpriseService.getLatestEnterpriseReserveByCreditCode(creditCode);
        if (latestRecord != null) {
            List<EnterpriseReserve> list = null;
            Date latestDate = latestRecord.getCreateDate();
            //判断上次申请时间是否是4月2号后
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = null;
            try {
                date = simpleDateFormat.parse(limitDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (DateUtils.compareTime(latestDate, date)) {
                //上次申请时间是4月2号之后，按7天
                list = enterpriseService.getEnterpriseReserveByCreditCodeSeven(creditCode);
            } else {
                //上次申请时间是4月2号之前，还是3天
                list = enterpriseService.getEnterpriseReserveByCreditCode(creditCode);
            }
            if (list.size() > 0) {
                return new Resp(RespCode.INPRIVILEGE);
            }
        }
//        reserve.setCreditCode(creditCode);
        enterpriseService.saveReserve(reserve);
        Resp resp = new Resp(RespCode.SUCCESS);
        return resp;
    }

    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    public String login(String urlparams, Model model, HttpServletRequest request, HttpServletResponse response) {
        //验证参数是否为空
        if (urlparams == null || "".equals(urlparams)) {
            return "/app/enterprise/login";
        }
        //解析参数信息
        urlparams = AES.decrypt(urlparams, key);
        JSONObject object = JSONObject.parseObject(urlparams);
        //判断是否为法人用户
        if (!"corp".equals(object.getString("account_type"))) {
            return "/app/enterprise/login";
        }
        //企业用户流程
        String creditCode = object.getString("cid");
        try {
            EnterpriseUser user = enterpriseService.verifyLogin(creditCode, null);
            request.getSession().setAttribute("enterpriseUser", user);
            CookieUtil.sendCookie(response, "enterpriseUser", user.getCreditCode());

            //判断上次录入时间是4月2号前还是4月2号后
            EnterpriseReserve latestRecord = enterpriseService.getLatestEnterpriseReserveByCreditCode(creditCode);
            if (latestRecord != null) {
                List<EnterpriseReserve> list = null;
                Date latestDate = latestRecord.getCreateDate();
                //判断上次申请时间是否是4月2号后。
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = null;
                try {
                    date = simpleDateFormat.parse(limitDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (DateUtils.compareTime(latestDate, date)) {
                    //上次申请时间是4月2号之后，按7天
                    list = enterpriseService.getEnterpriseReserveByCreditCodeSeven(creditCode);
                } else {
                    //上次申请时间是4月2号之前，还是3天
                    list = enterpriseService.getEnterpriseReserveByCreditCode(creditCode);
                }
                if (list.size() > 0) {
                    user.setIdentityNumber(String.valueOf(list.get(0).getNumberMasks()));
                } else {
                    user.setIdentityNumber("");
                }
            } else {
                user.setIdentityNumber("");
            }
            model.addAttribute("creditCode", user.getCreditCode());
            model.addAttribute("identityNumber", user.getIdentityNumber());
            model.addAttribute("name", user.getName());
            model.addAttribute("type", user.getType());
            model.addAttribute("address", user.getAddress());
            model.addAttribute("insuredNumber", user.getInsuredNumber());
            return "/app/enterprise/enter";
        } catch (Exception e) {
            //信息不存在用户表或其他错误，跳转登录页重新登录。
            model.addAttribute("flag", "false");
            return "/app/enterprise/login";
        }
    }

    @RequestMapping(value = "/enter", method = {RequestMethod.GET})
    public String enter(Model model, String creditCode, String identityNumber, String name, String type, String address, Integer insuredNumber, HttpServletRequest request) {

        String creditCode1 = CookieUtil.getCookie(request, "enterpriseUser");
        if (StringUtils.isNullOrEmpty(creditCode1)) {//无cookie，返回登录
            return "redirect:/enterprise/login";//return "/app/enterprise/login";
        } else {
            model.addAttribute("creditCode", creditCode);
            model.addAttribute("identityNumber", identityNumber);
            model.addAttribute("name", name);
            model.addAttribute("type", type);
            model.addAttribute("address", address);
            model.addAttribute("insuredNumber", insuredNumber);
            return "/app/enterprise/enter";
        }

    }

    /*跳转修改页面*/
    @GetMapping(value = "/toModify")
    public String toModify(HttpServletRequest request, Model model, Integer insuredNumber) {
        String creditCode = CookieUtil.getCookie(request, "enterpriseUser");
        if (StringUtils.isNullOrEmpty(creditCode)) {//无cookie，返回登录
            return "redirect:/enterprise/login";
        } else {
            //判断上次录入时间是4月2号前还是4月2号后
            EnterpriseReserve latestRecord = enterpriseService.getLatestEnterpriseReserveByCreditCode(creditCode);
            List<EnterpriseReserve> list = null;
            if (latestRecord != null) {
                Date latestDate = latestRecord.getCreateDate();
                //判断上次申请时间是否是4月2号后
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = null;
                try {
                    date = simpleDateFormat.parse(limitDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (DateUtils.compareTime(latestDate, date)) {
                    //上次申请时间是4月2号之后，按7天
                    list = enterpriseService.getEnterpriseReserveByCreditCodeSeven(creditCode);
                } else {
                    //上次申请时间是4月2号之前，还是3天
                    list = enterpriseService.getEnterpriseReserveByCreditCode(creditCode);
                }
                if (list == null || list.isEmpty()) {
                    //集合为空，说明登录判断有误，跳转登录页面
                    return "redirect:/enterprise/login";
                }
            }

            //获取集合对象
            EnterpriseReserve reserve = list.get(0);
            //判断是否是当日提交，设置可修改标志
            int days = DateUtils.differentDays(reserve.getCreateDate(), new Date());
            if (days == 0) {
                //可修改
                reserve.setIsModify("true");
            }
//            double days = DateUtils.getDistanceOfTwoDate(new Date(), reserve.getCreateDate());
//            if (days == 0) {
//                //当日提交，可修改
//                reserve.setIsModify("true");
//            }
            //将对象放入域对象中
            model.addAttribute("reserve", reserve);
            model.addAttribute("insuredNumber", insuredNumber);
            // 跳转修改页面
            return "/app/enterprise/modify";
        }

    }

    //修改信息提交
    @PostMapping(value = "/modifySubmit")
    @ResponseBody
    public Resp modifySubmit(EnterpriseReserve reserve, HttpServletRequest request) {
        //没有验证参保人数
        String creditCode = CookieUtil.getCookie(request, "enterpriseUser");
        if (StringUtils.isNullOrEmpty(creditCode)) {//无cookie，返回登录
            return new Resp(RespCode.DEFAULT);
        }
        reserve.setCreditCode(creditCode);
        int count = enterpriseService.updateReserve(reserve);
        if (count == 0) {
            Resp resp = new Resp(RespCode.DEFAULT);
            resp.setMsg("没有该记录");
            return resp;
        }
        return new Resp(RespCode.SUCCESS);
    }

}
