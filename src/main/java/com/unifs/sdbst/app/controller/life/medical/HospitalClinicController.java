package com.unifs.sdbst.app.controller.life.medical;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.unifs.sdbst.app.base.Resp;
import com.unifs.sdbst.app.bean.life.medical.HospitalClinic;
import com.unifs.sdbst.app.bean.life.medical.InfoRecord;
import com.unifs.sdbst.app.bean.life.medical.PersonRecord;
import com.unifs.sdbst.app.bean.user.User;
import com.unifs.sdbst.app.enums.RespCode;
import com.unifs.sdbst.app.service.life.medical.HospitalClinicService;
import com.unifs.sdbst.app.service.life.medical.SjMedicalDrugsService;
import com.unifs.sdbst.app.service.user.UserService;
import com.unifs.sdbst.app.utils.CookieUtil;
import com.unifs.sdbst.app.utils.EncryptUtil;
import com.unifs.sdbst.app.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: hzk
 * \* Date: 2020/2/16
 * \* Time: 15:52
 * \* To change this template use File | Settings | File Templates.
 * \* Description: 医院发热门诊
 * \
 */
@Controller
@RequestMapping(value = "data/hospital/clinic")
public class HospitalClinicController {
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private HospitalClinicService hospitalClinicService;

    @Autowired
    private SjMedicalDrugsService sjMedicalDrugsService;

    @Value("${hospital.key}")
    private String aesKey;


    @RequestMapping(value = "main")//登录页
    public String workBack(HttpServletRequest request, Model model) throws Exception {

        return "/app/life/hospital/workbackin";
    }

    @ResponseBody
    @RequestMapping(value = "mainSub", method = RequestMethod.POST)
    public Resp workSub(HttpServletResponse response, @NotNull String phone, @NotNull String Code, @NotNull String name, HttpServletRequest request) throws Exception {

//        //获取验证码
//        String sysCode = redisUtil.get("RANDOMVALIDATECODEKEY");
//        // 验证验证码是否过期
//        if (StringUtils.isEmpty(sysCode)) {
//            redisUtil.del("RANDOMVALIDATECODEKEY");
//            throw new MyException("验证码已失效！请重新获取", 0);
//        }
//        //判断验证码是否正确
//        if (!sysCode.equals(Code)) {
//            redisUtil.del("RANDOMVALIDATECODEKEY");
//            throw new MyException("验证码错误！", 0);
//        }
        HttpSession session = request.getSession();
//        String sessionId = session.getId();
//        System.out.println("sessionId是："+sessionId);
        User user = userService.verifyInfo(name, phone, request);
        session.setAttribute("sessionDePhone", EncryptUtil.aesEncrypt(user.getPhone(), aesKey));
        session.setMaxInactiveInterval(60 * 60 * 12);
        redisUtil.set(user.getId(), user.toString(), 60 * 60);
        CookieUtil.sendCookie(response, "cookieDePhone", EncryptUtil.aesEncrypt(user.getPhone(), aesKey));
        redisUtil.del("RANDOMVALIDATECODEKEY");
        List<PersonRecord> personRecord = sjMedicalDrugsService.getPersonByPhone(name);
        if (personRecord.size() > 0 && "4".equals(personRecord.get(0).getType())) {
            return new Resp(RespCode.SUCCESS, "/data/hospital/clinic/index");
        } else {
            return new Resp(RespCode.SUCCESS, "/data/hospital/clinic/noInvitation");
        }
    }

    @RequestMapping(value = "noInvitation")//无权限
    public String noInvitation(HttpServletRequest request, Model model) {

        return "/app/life/yiLiaoYaoPing/recordnull";
    }

    @RequestMapping(value = "luru")//录入页
    public String luru(HttpServletRequest request, Model model) throws Exception {
        return returnPae(request, "luru");

//        return "/app/life/hospital/luru";
    }

    @RequestMapping(value = "chaxun")//查询页
    public String chaxun(HttpServletRequest request, Model model) throws Exception {
        return returnPae(request, "chaxun");
//        return "/app/life/hospital/chaxun";
    }



    public String returnPae(HttpServletRequest request, String page) throws Exception {
        String userId = CookieUtil.getCookie(request, "cookieDePhone");
        if (StringUtils.isEmpty(userId)) {
            return "/app/life/hospital/workbackin";
        }
        String phone = EncryptUtil.aesDecrypt(userId, aesKey);
        if (StringUtils.isEmpty(phone)) {
            return "/app/life/hospital/workbackin";
        }
        List<PersonRecord> personRecord = sjMedicalDrugsService.getPersonByPhone(phone);//验证身份
        if (personRecord.size() > 0 && "4".equals(personRecord.get(0).getType())) {
            return "/app/life/hospital/" + page;
        } else {
            return "/app/life/hospital/workbackin";
        }
    }

    public Resp returnResp(HttpServletRequest request) throws Exception {
        String userId = CookieUtil.getCookie(request, "cookieDePhone");
        if (StringUtils.isEmpty(userId)) {
            return new Resp(RespCode.DEFAULT);
        }
        String phone = EncryptUtil.aesDecrypt(userId, aesKey);
        if (StringUtils.isEmpty(phone)) {
            return new Resp(RespCode.DEFAULT);
        }
        List<PersonRecord> personRecord = sjMedicalDrugsService.getPersonByPhone(phone);//验证身份
        if (personRecord.size() > 0 && "4".equals(personRecord.get(0).getType())) {
            return new Resp(RespCode.SUCCESS, personRecord.get(0));
        } else {
            return new Resp(RespCode.INPRIVILEGE);
        }
    }

    @RequestMapping(value = "index")//首页
    public String workBack(HttpServletRequest request) throws Exception {
        return returnPae(request, "clinic");
    }

    @ResponseBody
    @RequestMapping(value = "clinicSub", method = RequestMethod.POST)
    public Resp clinicSub(HospitalClinic hospitalClinic, HttpServletRequest request) throws Exception {
        Resp resp1 = returnResp(request);

        if (resp1.getCode() == 200) {
            PersonRecord personRecord = (PersonRecord) resp1.getData();
            hospitalClinic.setOperNo(personRecord.getPhone());
            hospitalClinic.setOperName(personRecord.getSiteName());
            hospitalClinic.setCol2(personRecord.getSiteCode());
            hospitalClinic.setCol5("1");
            hospitalClinicService.saveClinic(hospitalClinic);
            return new Resp(RespCode.SUCCESS);
        } else if (resp1.getCode() == 0) {
            return new Resp(RespCode.DEFAULT);
        } else {
            return new Resp(RespCode.INPRIVILEGE);
        }
    }

    @RequestMapping(value = "/import")
    @ResponseBody
    public String infoImport(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception{
        Resp resp1 = returnResp(request);
        HospitalClinic clinics = new HospitalClinic();
        if (resp1.getCode() == 200){
            PersonRecord personRecord = (PersonRecord) resp1.getData();
            clinics.setOperNo(personRecord.getPhone());
            clinics.setOperName(personRecord.getSiteName());
            clinics.setCol2(personRecord.getSiteCode());
        }

        /*获取user对象*/
        User user= (User) request.getSession().getAttribute("user");
        String fileName = file.getOriginalFilename();

        try {

            hospitalClinicService.batchImport(fileName, file,clinics);
            Resp respEntity = new Resp(RespCode.SUCCESS);
            String jsonObject = JSON.toJSONString(respEntity);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
            Resp respEntity = new Resp(RespCode.ERROR);
            String jsonObject = JSON.toJSONString(respEntity);
            return jsonObject;
        }

    }

    @ResponseBody
    @RequestMapping("/detailList")
    public Object pageNum(HttpServletRequest request, HospitalClinic hospitalClinic,
                          @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "limit", defaultValue = "10") Integer limit) throws Exception {

        Resp resp1 = returnResp(request);
        Map<String, Object> tableData = new HashMap<String, Object>();
        tableData.put("code", 0);

        if (resp1.getCode() == 200) {
            PersonRecord personRecord = (PersonRecord) resp1.getData();
            hospitalClinic.setOperNo(personRecord.getPhone());
            hospitalClinic.setOperName(personRecord.getSiteName());
            PageHelper.startPage(page, limit);
            List<HospitalClinic> projectByKey = hospitalClinicService.selectClilnic(hospitalClinic);
            PageInfo<HospitalClinic> pageInfo = new PageInfo<>(projectByKey);
            pageInfo.setList(projectByKey);
            tableData.put("msg", "成功");
            tableData.put("count", pageInfo.getTotal());
            tableData.put("data", pageInfo.getList());
            return tableData;
        } else {
            tableData.put("msg", "身份失效，请重新刷新页面");
            tableData.put("count", "");
            tableData.put("data", "");
            return tableData;
        }

    }


    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Resp delete(HospitalClinic hospitalClinic, HttpServletRequest request) throws Exception {
        Resp resp1 = returnResp(request);
        if (resp1.getCode() == 200) {
//            hospitalClinic.setId(hospitalClinic.getId());
            hospitalClinic.setCol5("0");
            hospitalClinicService.update(hospitalClinic);
            return new Resp(RespCode.SUCCESS);
        } else if (resp1.getCode() == 0) {
            return new Resp(RespCode.DEFAULT);
        } else {
            return new Resp(RespCode.INPRIVILEGE);
        }
    }



}
