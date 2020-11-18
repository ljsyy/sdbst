package com.unifs.sdbst.app.controller.life.medical;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.unifs.sdbst.app.base.Resp;
import com.unifs.sdbst.app.bean.life.medical.InfoRecord;
import com.unifs.sdbst.app.bean.life.medical.InternetWord;
import com.unifs.sdbst.app.bean.life.medical.PersonRecord;
import com.unifs.sdbst.app.bean.life.medical.SjMedicalDrugs;
import com.unifs.sdbst.app.bean.user.User;
import com.unifs.sdbst.app.enums.RespCode;
import com.unifs.sdbst.app.exception.MyException;
import com.unifs.sdbst.app.service.life.medical.SjMedicalDrugsService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 医疗药品接口Controller
 *
 * @author liuzibo
 * @version 2018-02-07
 */
@Controller
@RequestMapping(value = "data/sjMedicalDrugs")
public class SjMedicalDrugsInter {

    @Autowired
    private SjMedicalDrugsService sjMedicalDrugsService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserService userService;

    @Value("${aes.key}")
    private String aesKey;

    @RequestMapping(value = "getDetail")
    @ResponseBody
    public Map<String, Object> getDetail(String id, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> date = Maps.newLinkedHashMap();

        if ("".equals(id)) {
            date.put("success", true);
            date.put("msg", "操作成功");
            date.put("obj", null);
            return date;
        }
        List<SjMedicalDrugs> list = new ArrayList<SjMedicalDrugs>();
        SjMedicalDrugs Dep_list = sjMedicalDrugsService.findMedId(id);
        if (Dep_list.getRemarks() == null) {
            Dep_list.setRemarks("");
        }
        list.add(Dep_list);
        date.put("success", true);
        date.put("msg", "操作成功");
        date.put("obj", list);
        return date;
    }

    @RequestMapping(value = "getlist")
    @ResponseBody
    public Map<String, Object> getlist(String bigClass, String englishName, String inClass, String chineseName, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> date = Maps.newLinkedHashMap();

        List<SjMedicalDrugs> list = new ArrayList<SjMedicalDrugs>();
        List<SjMedicalDrugs> Dep_list = sjMedicalDrugsService.findMedicalDrugs(bigClass, englishName, inClass, chineseName);

        date.put("success", true);
        date.put("msg", "操作成功");
        for (SjMedicalDrugs s : Dep_list) {
            list.add(s);
            date.put("obj", list);
        }
        return date;
    }

    @RequestMapping(value = "internet")
    public String internet() {
        return "/app/life/yiLiaoYaoPing/internet";
    }

    @RequestMapping(value = "internetWord")
    @ResponseBody
    public Resp internetWord(InternetWord internetWord) {

        sjMedicalDrugsService.insert(internetWord);
        Resp resp = new Resp(RespCode.SUCCESS);
        return resp;
    }

    @RequestMapping(value = "internet8180")
    public String internet8180() {
        return "/app/life/yiLiaoYaoPing/internet8180";
    }

    @RequestMapping(value = "internetWord8180")
    @ResponseBody
    public Resp internetWord8180(@NotNull String data, @NotNull String phnoe, @NotNull String
            Code, @NotNull String name) {
        //获取验证码
        String sysCode = redisUtil.get(phnoe);
        // 验证验证码是否过期
        if (StringUtils.isEmpty(sysCode)) {
            throw new MyException("验证码已失效！请重新获取", 0);
        }
        //判断验证码是否正确
        if (!sysCode.equals(Code)) {
            throw new MyException("验证码错误！", 0);
        }
        if ("22838180".equals(data) && WsClientTool.getMD5Str("Sdrx8180!@#").equals(name)) {
            List<InternetWord> list = sjMedicalDrugsService.findList();
            Resp resp = new Resp(RespCode.SUCCESS);
            resp.setData(list);
            return resp;
        } else {
            return new Resp(RespCode.DEFAULT);
        }
    }

    @RequestMapping(value = "personRecord")
    public String personRecord() {
        return "/app/life/yiLiaoYaoPing/huBei";
    }

    @ResponseBody
    @RequestMapping(value = "personRecordSubmit")
    public Resp personRecordSubmit(InfoRecord infoRecord, String startTime, String startTime2,@NotNull String code) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //获取验证码
        String sysCode = redisUtil.get(infoRecord.getPhone());
        // 验证验证码是否过期
        if (StringUtils.isEmpty(sysCode)) {
            throw new MyException("验证码已失效！请重新获取", 0);
        }
        //判断验证码是否正确
        if (!sysCode.equals(code)) {
            throw new MyException("验证码错误！", 0);
        }
        if (startTime != null && startTime != "") {
            infoRecord.setReturnDate(sdf.parse(startTime));
        } else {
            infoRecord.setReturnDate(new Date());
        }
        if (startTime2 != null && startTime2 != "") {
            infoRecord.setZjDate(sdf.parse(startTime2));
        } else {
            infoRecord.setZjDate(new Date());
        }
        sjMedicalDrugsService.saveInfoRecord(infoRecord);
        return new Resp(RespCode.SUCCESS);
    }

    @RequestMapping(value = "workIn")
    public String workIn(HttpServletRequest request, Model model) throws Exception {
//        HttpSession session = request.getSession();
//        String ctm = (String) session.getAttribute("loginName");
//        if (!StringUtils.isEmpty(ctm)){
//            String phone = EncryptUtil.aesDecrypt(ctm, aesKey);
//            if (StringUtils.isEmpty(phone)) {
//                return "/app/life/yiLiaoYaoPing/recordnull";
//            }
//            List<PersonRecord> personRecord = sjMedicalDrugsService.getPersonByPhone(phone);
//            if (personRecord.size() == 0) {
//                return "/app/life/yiLiaoYaoPing/recordnull";
//            } else {
//                model.addAttribute("list", ctm);
//                return "/app/life/yiLiaoYaoPing/record";
//            }
//        }
//        System.out.println("session id 为："+session.getId());
//        System.out.println("session 值："+session.getAttribute("loginName"));
        return "/app/life/yiLiaoYaoPing/workin";
    }

    @RequestMapping(value = "workBack")
    public String workBack(HttpServletRequest request, Model model) throws Exception {

        return "/app/life/yiLiaoYaoPing/reportBack";
    }

    @RequestMapping(value = "workTotal")
    public String workTotal(HttpServletRequest request, Model model) throws Exception {

        return "/app/life/yiLiaoYaoPing/workTotal";
    }
    @RequestMapping(value = "workPage")
    public String workPage(HttpServletRequest request, Model model) throws Exception {

        return "/app/life/yiLiaoYaoPing/workPage";
    }

    @ResponseBody
    @RequestMapping("/pageNum")
    public Object pageNum(HttpServletRequest request,
                               @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "limit", defaultValue = "10") Integer limit) throws ParseException {

        PageHelper.startPage(page, limit);
        List<InfoRecord> projectByKey = sjMedicalDrugsService.selectInfoRecord();
        PageInfo<InfoRecord> pageInfo = new PageInfo<>(projectByKey);
        pageInfo.setList(projectByKey);
        Map<String, Object> tableData = new HashMap<String, Object>();
        tableData.put("code", 0);
        tableData.put("msg", "");
        tableData.put("count", pageInfo.getTotal());
        tableData.put("data", pageInfo.getList());
        return tableData;
    }
    @ResponseBody
    @RequestMapping(value = "totalNum")
    public List<Map<String, Object >> totalNum(HttpServletRequest request, Model model) throws Exception {

        List<Map<String, Object >>  map= sjMedicalDrugsService.getTotalNumber();
//        for(int i=0;i<map.size();i++){
//            for (Object key : map.get(i).keySet()) {
//                Object value =  map.get(i).get(key);
//                System.out.println(key + " : " + String.valueOf(value ));
//                System.out.println(key + " : " + value);
//            }
//        }
        return map;
    }
    @ResponseBody
    @RequestMapping(value = "workSub",method = RequestMethod.POST)
    public Resp workSub(HttpServletResponse response,@NotNull String phone, @NotNull String Code, @NotNull String name, HttpServletRequest request) throws Exception {

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
        session.setAttribute("loginName", EncryptUtil.aesEncrypt(user.getPhone(), aesKey));
        session.setMaxInactiveInterval(60 * 60 * 12);
        redisUtil.set(user.getId(), user.toString(), 60 * 60);
        CookieUtil.sendCookie(response,"cookei", EncryptUtil.aesEncrypt(user.getPhone(), aesKey));
        redisUtil.del("RANDOMVALIDATECODEKEY");
        return new Resp(RespCode.SUCCESS);
    }


    @RequestMapping("/createImg")
    public void createImg(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
            response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            VerifyUtils randomValidateCode = new VerifyUtils();
            randomValidateCode.getRandcode(request, response);//输出验证码图片
            //将生成的随机验证码存放到redis中
            redisUtil.set("RANDOMVALIDATECODEKEY", (String) request.getSession().getAttribute("RANDOMREDISKEY"), 1 * 60);
        } catch (Exception e) {
            System.out.println(" 获取验证码异常：" + e);
        }
    }
    @RequestMapping(value = "ywRecord")
    public String ywRecord(Model model,HttpServletRequest request) throws Exception {
        String userId = CookieUtil.getCookie(request, "cookei");
        String ctm = userId;
        if (StringUtils.isEmpty(ctm)) {
            return "/app/life/yiLiaoYaoPing/workin";
        }
        String phone = EncryptUtil.aesDecrypt(ctm, aesKey);
        if (StringUtils.isEmpty(phone)) {
            return "/app/life/yiLiaoYaoPing/workin";
        }
        List<PersonRecord> personRecord = sjMedicalDrugsService.getPersonByPhone(phone);
        if (personRecord.size() == 0) {
            return "/app/life/yiLiaoYaoPing/recordnull";
        } else {
//            model.addAttribute("list", ctm);
            return "/app/life/yiLiaoYaoPing/record";
        }
    }

    @ResponseBody
    @RequestMapping(value = "ywRecordsMore",method = RequestMethod.POST)
    public Resp ywRecordsMore( String names, String phones,HttpServletRequest request,
                              @RequestParam(value = "page", defaultValue = "1") Integer
                                      page, @RequestParam(value = "limit", defaultValue = "10") Integer limit) throws Exception {
        String userId = CookieUtil.getCookie(request, "cookei");
        String ctm = userId;

        if (StringUtils.isEmpty(ctm)) {
            return new Resp(RespCode.DEFAULT);
        }
        String phone = EncryptUtil.aesDecrypt(ctm, aesKey);
        if (StringUtils.isEmpty(phone)) {
            return new Resp(RespCode.DEFAULT);
        }
        List<PersonRecord> personRecord = sjMedicalDrugsService.getPersonByPhone(phone);
        if (personRecord.size() == 0) {
            return new Resp(RespCode.INPRIVILEGE);
        } else {
            List<InfoRecord> list = sjMedicalDrugsService.getInfoRecordBySite(page, limit, personRecord.get(0).getSiteCode(), names, phones, personRecord.get(0).getType());
            return new Resp(RespCode.SUCCESS, list);
        }
    }

    @RequestMapping(value = "recordDetail")
    public String recordDetail(String rid, Model model,HttpServletRequest request) throws Exception {
        String userId = CookieUtil.getCookie(request, "cookei");
        String dh = userId;
        if (StringUtils.isEmpty(userId) ) {
            return "/app/life/yiLiaoYaoPing/workin";
        }
        String phone = EncryptUtil.aesDecrypt(dh, aesKey);
        if (StringUtils.isEmpty(rid)) {
            return "/app/life/yiLiaoYaoPing/recordnull";
        }
        List<PersonRecord> personRecord = sjMedicalDrugsService.getPersonByPhone(phone);
        if (personRecord.size() == 0) {
            return "/app/life/yiLiaoYaoPing/recordnull";
        } else {
            InfoRecord infoRecord = sjMedicalDrugsService.getInfoRecordById(rid);
            model.addAttribute("info", infoRecord);
//            model.addAttribute("delPhone", dh);
            model.addAttribute("type", personRecord.get(0).getType());
        }
        return "/app/life/yiLiaoYaoPing/recordDetail";
    }

    @ResponseBody
    @RequestMapping(value = "ywRecordsUpdate",method = RequestMethod.POST)
    public Resp ywRecordsUpdate(InfoRecord infoRecord,HttpServletRequest request,String code) throws Exception {
        String userId = CookieUtil.getCookie(request, "cookei");
        String delPhone = userId;
        if (StringUtils.isEmpty(userId)) {
            return new Resp(RespCode.DEFAULT,"身份失效");
        }
        String phone = EncryptUtil.aesDecrypt(delPhone, aesKey);
        if (StringUtils.isEmpty(phone)) {
            return new Resp(RespCode.DEFAULT,"身份失效");
        }
        List<PersonRecord> personRecord = sjMedicalDrugsService.getPersonByPhone(phone);
        if (personRecord.size() == 0) {
            return new Resp(RespCode.DEFAULT);
        } else {
            InfoRecord infoRecord1 = sjMedicalDrugsService.getInfoRecordById(infoRecord.getId());
            if ("1".equals(personRecord.get(0).getType())) {
                if (infoRecord1.getSiteCode().equals(personRecord.get(0).getSiteCode())) {
                    infoRecord.setResplonTime(new Date());
                    infoRecord.setFromPlace(personRecord.get(0).getPhone());
                } else {
                    return new Resp(RespCode.INPRIVILEGE);
                }
            } else if ("2".equals(personRecord.get(0).getType())) {
                if (infoRecord1.getSiteCode().equals(personRecord.get(0).getSiteCode())) {
                    infoRecord.setPoliceDate(new Date());
                    infoRecord.setCol6(personRecord.get(0).getPhone());
                } else {
                    return new Resp(RespCode.INPRIVILEGE);
                }
            } else if ("3".equals(personRecord.get(0).getType())) {
                //获取验证码
                String sysCode = redisUtil.get(infoRecord.getCol12());
                // 验证验证码是否过期
                if (StringUtils.isEmpty(sysCode)) {
                    throw new MyException("验证码已失效！请重新获取", 400);
                }
                //判断验证码是否正确
                if (!sysCode.equals(code)) {
                    throw new MyException("验证码错误！", 400);
                }
                if (infoRecord1.getTown().equals(personRecord.get(0).getSiteCode())) {
                    infoRecord.setCunDate(new Date());
                    infoRecord.setCol8(personRecord.get(0).getPhone());
                } else if (infoRecord1.getTownNew().equals(personRecord.get(0).getSiteCode())) {//错误的镇街处理
                    if ("是".equals(infoRecord.getBenOwn())){
                        infoRecord.setTownDate(new Date());
                    }else {
                        infoRecord.setCunDate(new Date());
                    }
                    if (StringUtils.isEmpty(infoRecord1.getTownNum())) {
                        infoRecord.setTownNum(personRecord.get(0).getPhone());
                    } else {//已经转派过
                        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss");
                        infoRecord.setTownNum(infoRecord1.getTownNum()+"/" + infoRecord1.getCol11()+"/" +infoRecord1.getCol12()+"/" +sdf.format(infoRecord1.getCunDate()) + "；" + personRecord.get(0).getPhone());
                    }
                } else {
                    return new Resp(RespCode.INPRIVILEGE);
                }
            } else {

            }
            sjMedicalDrugsService.updateInfoRecordById(infoRecord, personRecord.get(0).getType());
            return new Resp(RespCode.SUCCESS);
        }
    }
}
