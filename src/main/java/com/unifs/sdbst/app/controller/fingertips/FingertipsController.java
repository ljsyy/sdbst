package com.unifs.sdbst.app.controller.fingertips;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.unifs.sdbst.app.base.Resp;
import com.unifs.sdbst.app.bean.fingertips.*;
import com.unifs.sdbst.app.enums.RespCode;
import com.unifs.sdbst.app.service.fingertips.FingertipsMessageService;
import com.unifs.sdbst.app.service.fingertips.FingertipsService;
import com.unifs.sdbst.app.service.fingertips.SysFingertipsLogService;
import com.unifs.sdbst.app.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @version V1.0
 * @title: FingertipsController
 * @projectName sdbst
 * @description: 指尖办控制层
 * @author： 张恭雨
 * @date 2019/9/7 13:56
 */
@Controller
@CrossOrigin(origins = "*")
@RequestMapping(value = "/app/fingertips")
public class FingertipsController {
    @Value("${zhijianban.item.url}")
    private String itemUrl;
    //private String itemUrl="http://isd1.shunde.gov.cn/sdbst/app/fingertips";

    @Value("${zhijianban.operation.url}")
    private String operUrl;
   // private String operUrl="http://isd1.shunde.gov.cn/sdbst/app/fingertips";

    @Value("${aes.key}")
    private String aesKey;

    @Value("${spring.mail.username}")
    private String sender;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private FingertipsService fingertipsService;
    @Autowired
    private FingertipsMessageService fingertipsMessageService;

    @Autowired
    private SysFingertipsLogService sysFingertipsLogService;

    @RequestMapping(value = "/detail")
    public String detail(String id,String link, Model model,HttpServletRequest request) throws Exception {
        model.addAttribute("id",id);
        if(link != null && link.length()>0){
            String string = EncryptUtil.aesDecrypt(link,aesKey);
            JSONObject jsonObject = JSONObject.parseObject(string);
            jsonObject.put("detailId",id);
            FingertipsUserInfo fingertipsUserInfo =  JsonToFingertipsUserInfo(jsonObject);
            sysFingertipsLogService.insertFingertipsUserInfo(fingertipsUserInfo);

            model.addAttribute("dePhone", fingertipsUserInfo.getMobile());
            model.addAttribute("dePhoneEnc", link);//整个加密串

            HttpSession session = request.getSession();
            session.setAttribute("dePhone", fingertipsUserInfo.getMobile());
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            session.setAttribute("deUUID", uuid);
        }else{
            model.addAttribute("dePhone", "");
            model.addAttribute("dePhoneEnc", "");
        }

        return "/app/fingertips/detail";
    }

    public FingertipsUserInfo JsonToFingertipsUserInfo(JSONObject object){
        FingertipsUserInfo fingertipsUserInfo = new FingertipsUserInfo();
        fingertipsUserInfo.setAccount(object.getString("account"));
        fingertipsUserInfo.setAccountType(object.getString("account_type"));
        fingertipsUserInfo.setCid(object.getString("cid"));
        fingertipsUserInfo.setCtype(object.getString("ctype"));
        fingertipsUserInfo.setClevel(object.getString("level"));
        if ("null".equals(object.getString("link_person_cid"))){
            fingertipsUserInfo.setLinkPersonCid("");
        }else {
            fingertipsUserInfo.setLinkPersonCid(object.getString("link_person_cid"));
        }
        fingertipsUserInfo.setLinkPersonCtype(object.getString("link_person_ctype"));
        fingertipsUserInfo.setLinkPersonName(object.getString("link_person_name"));
        fingertipsUserInfo.setLoginType(object.getString("login_type"));
        fingertipsUserInfo.setMobile(object.getString("mobile"));
        fingertipsUserInfo.setName(object.getString("name"));
        fingertipsUserInfo.setOrigin(object.getString("origin"));
        fingertipsUserInfo.setUuid(object.getString("uid"));
        fingertipsUserInfo.setUversion(object.getString("uversion"));
        fingertipsUserInfo.setUfrom(object.getString("from"));
        fingertipsUserInfo.setId(UUID.randomUUID().toString().replaceAll("-",""));
        fingertipsUserInfo.setInsertTime(new Date());
        fingertipsUserInfo.setRemark("指尖办事项调用"+object.getString("detailId"));
        return fingertipsUserInfo;
    }
    @RequestMapping(value = "/managerInfo")
    public String managerInfo(String matterId,String matterCode,String dePhone,String dePhoneEnc,Model model,String caseId) throws Exception {
        model.addAttribute("matterId",matterId);
        model.addAttribute("matterCode",matterCode);
        model.addAttribute("dePhone",dePhone);
        model.addAttribute("caseId",caseId == null ?"":caseId);

        System.out.println(dePhoneEnc);
        if(dePhoneEnc != null && dePhoneEnc.length()>0){
            String string = EncryptUtil.aesDecrypt(dePhoneEnc,aesKey);
            JSONObject jsonObject = JSONObject.parseObject(string);
            FingertipsUserInfo fingertipsUserInfo =  JsonToFingertipsUserInfo(jsonObject);
            model.addAttribute("personCid",fingertipsUserInfo.getLinkPersonCid());
            model.addAttribute("personName",fingertipsUserInfo.getLinkPersonName());
            model.addAttribute("dePhone",fingertipsUserInfo.getMobile());

            model.addAttribute("dePhoneEnc",EncryptUtil.aesEncrypt(fingertipsUserInfo.getMobile(),aesKey));
        }else{
            model.addAttribute("personCid","");
            model.addAttribute("personName","");
            model.addAttribute("dePhoneEnc","");
        }
        model.addAttribute("link",dePhoneEnc);

        return "/app/fingertips/managerInfo";
    }

    @RequestMapping(value = "/shenBao")
    public String shenBao(String matterId, String materialTitle, Model model) {
        model.addAttribute("matterId",matterId);
        model.addAttribute("materialTitle",materialTitle);
        return "/app/fingertips/shenBao";
    }



//    @RequestMapping(value = "/addressList")
//    public String addressList() {
//        return "/app/fingertips/addressList";
//    }
//
//    @RequestMapping(value = "/addAddress")
//    public String addAddress() {
//        return "/app/fingertips/addAddress";
//    }
//
//    @RequestMapping(value = "/editAddress")
//    public String editAddress() {
//        return "/app/fingertips/editAddress";
//    }

    //输入办件填写的基本信息，调用此API后输出创建后的办件编码
    @ResponseBody
    @RequestMapping(value = "/case/add")
    public String caseAdd(@RequestBody JSONObject object,HttpServletRequest request) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String bizId = "APPID" + uuid;
        object.put("bizId", bizId);
        String string = FingertipsHttpUtils.sendPostJson(operUrl + "/case/add", object);

        insertSysFingertipsLog(request,object,uuid,"创建办件基本信息");
//        String string = "{\"code\":200,\"subCode\":20000,\"msg\":\"OK\",\"data\":12340}";
        JSONObject jsonObject = JSONObject.parseObject(string);
        String caseId = jsonObject.getString("data");
        FingertipsRecord fingertipsRecord = new FingertipsRecord();
        fingertipsRecord.setCaseId(caseId);
        String applicantType = object.getString("applicantType");
        String phone = "";
        String agentCertificateNum = "";
        String matterId = "";
        String agentName = "";
        if ("1".equals(applicantType)){
            phone = object.getJSONObject("basicApplicationVO").getString("applicantPhone");
            agentCertificateNum = object.getJSONObject("basicApplicationVO").getString("agentCertificateNum");
            matterId = object.getJSONObject("basicApplicationVO").getString("matterId");
            agentName = object.getJSONObject("basicApplicationVO").getString("agentName");
        }else {
            phone = object.getJSONObject("corporationApplicationVO").getString("applicantPhone");
            agentCertificateNum = object.getJSONObject("corporationApplicationVO").getString("agentCertificateNum");
            matterId = object.getJSONObject("corporationApplicationVO").getString("matterId");
            agentName = object.getJSONObject("corporationApplicationVO").getString("agentName");
        }
        fingertipsRecord.setPhone(phone);
        fingertipsRecord.setMatterId( matterId);
        fingertipsRecord.setAgentCertificatenum(agentCertificateNum);
        fingertipsRecord.setName(agentName);
        fingertipsService.insertFingertipsRecord(fingertipsRecord);
        if (string != null){
            return string;
        }else {
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("code",2000);
            jsonObject1.put("Msg","数据出错啦，请检查输入内容重新提交");
            return  jsonObject1.toJSONString();
        }
//        return object.toJSONString();
    }

    public void insertSysFingertipsLog(HttpServletRequest request,JSONObject object,String uuid,String name){
        HttpSession session = request.getSession();
        String phone = (String) session.getAttribute("dePhone");
        String deUUID = (String) session.getAttribute("deUUID");
        SysFingertipsLog sysFingertipsLog = new SysFingertipsLog();
        sysFingertipsLog.setId(uuid);
        sysFingertipsLog.setCreateDate(new Date());
        sysFingertipsLog.setJsonData(object.toString());
        sysFingertipsLog.setOperateName(name);
        sysFingertipsLog.setPhone(phone);
        sysFingertipsLog.setUuid(deUUID);
        sysFingertipsLogService.insertLog(sysFingertipsLog);
    }
//    @RequestMapping(value = "/keepOut")
//    public void keepOut(@RequestBody JSONObject object, HttpServletRequest request) {
//
//        for (Map.Entry<String, Object> entry : object.entrySet()) {
//            HttpSession session = request.getSession();
//            session.setAttribute(entry.getKey(), entry.getValue());
//        }
//
//    }

//    @ResponseBody
//    @RequestMapping(value = "/getIn")
//    public String getper(HttpServletRequest request) {
//
////        HttpSession session = request.getSession();
////        System.out.println("applicantType值:" + session.getAttribute("applicantType"));
////        System.out.println("corporationApplicationVO值:" + session.getAttribute("corporationApplicationVO"));
////        System.out.println("basicApplicationVO值:" + session.getAttribute("basicApplicationVO"));
//
//        return "";
//    }

    //查询事项详细信息
    @ResponseBody
    @RequestMapping("/matter/detail")
    public String matterDetail(String id) {

        Map map = new HashMap();
        map.put("id", id);
        String string = FingertipsHttpUtils.httpRequestToString(itemUrl + "/matter/detail", "get", map);
//        JSONObject jsonObject =  JSONObject.parseObject(string);
//        if ("200".equals(jsonObject.getString("code"))){
//            JSONObject data =  JSONObject.parseObject(jsonObject.getString("data"));
//            JSONArray json1 = JSONArray.parseArray(data.getString("outFlowPics"));
//            JSONObject outFlowPics =  JSONObject.parseObject(json1.getString(0));
//            String url = outFlowPics.getString("url");
//            System.out.println("图片url："+url);
//        }
        if (string != null){
            return string;
        }else {
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("code",2000);
            jsonObject1.put("Msg","数据出错啦，请检查输入内容重新提交");
            return  jsonObject1.toJSONString();
        }


//        { "code": 200, "subCode": 20000, "msg": "OK", "data": { "id": 87, "directoryId": 1113, "matterName": "123", "matterCode": "123456789012345676333020500000000", "matterCodeMain": null, "matterCodeSub": null, "matterType": "01", "matterTypeSub": null, "orgType": 1, "orgId": 3, "orgName": "省档案局 ", "sourceOfPower": 2, "firstOffice": "", "secondOffice": "",
//            "serviceType": 2, "handlingForm": "", "jointAgencyIds": null, "jointAgencys": "", "isBespoke": null, "isWebApply": null, "webApplyUrl": null, "starProperty": 3, "exeHierarchy": "3", "range": "", "pretrialDay": null, "pretrialType": 0, "receiveDay": null, "receiveType": null, "lawlimitDay": 3, "lawlimitType": 0, "lawlimitDayDescribe": null, "promiseDay": 2, "promiseType": 0, "promiseDayDescribe": "", "foruser": "1", "personalThemeClass": null, "organizationThemeClass": "005", "according": "fdsa", "exerciseContent": "", "permissionDivision": "", "applyTerm": "fdsa", "proceFlow": null, "countLimitNum": null, "countLimitDescribe": "", "isIntermediary": null, "intermediaryMatterId": null, "towinMaxNum": 0, "towinDescribe": "", "areaCode": "", "province": null, "city": null, "county": null, "acceptAddress": "fds", "contactPhone": "1234567", "monitorComplain": "1234567", "acceptTime": null, "officeTime": "", "trafficGuide": "", "chargeFlag": 0, "chargeType": "", "chargeStandard": "fdsa",
//            "chargeBasis": "", "finishGetType": "1", "finishDescribe": "", "issend": null, "sendUrlType": null, "sendUrl": null, "version": null } }
//        String testResult = "{ \"code\": 200, \"subCode\": 20000, \"msg\": \"OK\", \"data\": { \"id\": 87, \"directoryId\": 1113, \"matterName\": \"123\", \"matterCode\": \"123456789012345676333020500000000\", \"matterCodeMain\": null, \"matterCodeSub\": null, \"matterType\": \"01\", \"matterTypeSub\": null, \"orgType\": 1, \"orgId\": 3, \"orgName\": \"省档案局 \", \"sourceOfPower\": 2, \"firstOffice\": \"\", \"secondOffice\": \"\",\"serviceType\": 2, \"handlingForm\": \"\", \"jointAgencyIds\": null, \"jointAgencys\": \"\", \"isBespoke\": null, \"isWebApply\": null, \"webApplyUrl\": null, \"starProperty\": 3, \"exeHierarchy\": \"3\", \"range\": \"\", \"pretrialDay\": null, \"pretrialType\": 0, \"receiveDay\": null, \"receiveType\": null, \"lawlimitDay\": 3, \"lawlimitType\": 0, \"lawlimitDayDescribe\": null, \"promiseDay\": 2, \"promiseType\": 0, \"promiseDayDescribe\": \"\", \"foruser\": \"1\", \"personalThemeClass\": null, \"organizationThemeClass\": \"005\", \"according\": \"fdsa\", \"exerciseContent\": \"\", \"permissionDivision\": \"\", \"applyTerm\": \"fdsa\", \"proceFlow\": null, \"countLimitNum\": null, \"countLimitDescribe\": \"\", \"isIntermediary\": null, \"intermediaryMatterId\": null, \"towinMaxNum\": 0, \"towinDescribe\": \"\", \"areaCode\": \"\", \"province\": null, \"city\": null, \"county\": null, \"acceptAddress\": \"fds\", \"contactPhone\": \"1234567\", \"monitorComplain\": \"1234567\", \"acceptTime\": null, \"officeTime\": \"\", \"trafficGuide\": \"\", \"chargeFlag\": 0, \"chargeType\": \"\", \"chargeStandard\": \"fdsa\",\"chargeBasis\": \"\", \"finishGetType\": \"1\", \"finishDescribe\": \"\", \"issend\": null, \"sendUrlType\": null, \"sendUrl\": null, \"version\": null } }";
//        System.out.println(testResult);
//
//        JSONObject json_test = JSON.parseObject(testResult);
//        String code = json_test.getString("code");
//        if ("200".equals(code)) {
//            JSONObject data = JSON.parseObject(json_test.getString("data"));
//            System.out.println(data.getString("matterName"));
//        }
//        return json_test.toJSONString();
    }

    //通过事项查询条件，查询材料列表信息
    @ResponseBody
    @RequestMapping("/matter/material")
    public String matterMaterial(String matterId) {

        Map map = new HashMap();
        map.put("matterId", matterId);
        map.put("pageSize", 20);
        String string = FingertipsHttpUtils.httpRequestToString(itemUrl+"/matter/material/listbymatterid", "get", map);

        if (string != null){
            return string;
        }else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code",2000);
            jsonObject.put("Msg","数据出错啦，请检查输入内容重新提交");
            return  jsonObject.toJSONString();
        }

//        {"code": 200, "subCode": 20000, "msg": "OK", "data": [ { "id": 112, "matterId": 83, "materialName": "身份证身份证", "sources": "", "type": 2, "getType": "1", "pageNum": 2, "pageCopyNum": 2, "needElectronic": 2, "supportElecCert": 2, "fileSharing": 1, "emptyFileUrl": "http://xxxx.com/123234/xxxx.doc", "demoFileUrl": "http://xxxx.com/123234/zzzz.doc" }, { "id": 113, "matterId": 83, "materialName": "卫生许可证卫生许可证", "sources": "", "type": 2, "getType": "1", "pageNum": 2, "pageCopyNum": 1, "needElectronic": 1, "supportElecCert": 1, "fileSharing": 1, "emptyFileUrl": "http://xxxx.com/123234/xxxx.doc", "demoFileUrl": "http://xxxx.com/123234/zzzz.doc" } ] }

//        String testResult = "{\"code\": 200, \"subCode\": 20000, \"msg\": \"OK\", \"data\": [ { \"id\": 112, \"matterId\": 83, \"materialName\": \"身份证身份证\", \"sources\": \"\", \"type\": 2, \"getType\": \"1\", \"pageNum\": 2, \"pageCopyNum\": 2, \"needElectronic\": 2, \"supportElecCert\": 2, \"fileSharing\": 1, \"emptyFileUrl\": \"http://xxxx.com/123234/xxxx.doc\", \"demoFileUrl\": \"http://xxxx.com/123234/zzzz.doc\" }, { \"id\": 113, \"matterId\": 83, \"materialName\": \"卫生许可证卫生许可证\", \"sources\": \"\", \"type\": 2, \"getType\": \"1\", \"pageNum\": 2, \"pageCopyNum\": 1, \"needElectronic\": 1, \"supportElecCert\": 1, \"fileSharing\": 1, \"emptyFileUrl\": \"http://xxxx.com/123234/xxxx.doc\", \"demoFileUrl\": \"http://xxxx.com/123234/zzzz.doc\" } ] }";
//        System.out.println(testResult);
//
//        JSONObject json_test = JSON.parseObject(testResult);
//
//        return json_test.toJSONString();
    }

    //查询证件类型列表，包括个人证件类型与企业证件类型。(页面选择证件类型)
    @ResponseBody
    @RequestMapping("/common/certificateTypes")
    public String commonCertificateTypes(String applicantType) {

        Map map = new HashMap();
        map.put("applicantType", applicantType);
        String string = FingertipsHttpUtils.httpRequestToString(operUrl + "/common/certificateTypes", "get", map);
        if (string != null){
            return string;
        }else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code",2000);
            jsonObject.put("Msg","数据出错啦，请检查输入内容重新提交");
            return  jsonObject.toJSONString();
        }

//        { "code": 200, "data": [ { "code": "111", "value": "居民身份证 " }, { "code": "113", "value": "户口簿 " } ], "msg": "", "subCode": 20000 }

//        String testResult = "{ \"code\": 200, \"data\": [ { \"code\": \"111\", \"value\": \"居民身份证 \" }, { \"code\": \"113\", \"value\": \"户口簿 \" } ], \"msg\": \"\", \"subCode\": 20000 }";
//        System.out.println(testResult);
//
//        JSONObject json_test = JSON.parseObject(testResult);
//
//        return json_test.toJSONString();
    }

    //上传文件。文件大小限制：
    //20M，支持文件扩展名 ：.rar .zip .doc .docx .xls .xlsx .wps .pdf .jpg
//    @ControlLog(operateType = "文件上传",context = "电子照证件上传")
    @RequestMapping("/common/upload")
    @ResponseBody
    public String commonUpload(@RequestParam("file") MultipartFile file,HttpServletRequest request) throws IOException {
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }
        String fileName=file.getOriginalFilename();
        // 获取文件后缀
        String prefix=fileName.substring(fileName.lastIndexOf("."));
        //生成唯一文件名
        String tempName = UUID.randomUUID().toString();
        String filePath = "/home/file/temp/";
        File dest = new File(filePath + tempName+prefix);
        file.transferTo(dest);
        //文件暂存成功，调用接口上传文件
        String result=FingertipsHttpUtils.uploadFilePost(operUrl+"/common/upload",filePath + tempName+prefix);
        HttpSession session = request.getSession();
        String phone = (String) session.getAttribute("dePhone");
        String deUUID = (String) session.getAttribute("deUUID");
        SysFingertipsLog sysFingertipsLog = new SysFingertipsLog();
        sysFingertipsLog.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        sysFingertipsLog.setCreateDate(new Date());
        sysFingertipsLog.setJsonData(dest.getAbsolutePath());
        sysFingertipsLog.setOperateName("上传文件接口："+ result);
        sysFingertipsLog.setPhone(phone);
        sysFingertipsLog.setUuid(deUUID);
        sysFingertipsLogService.insertLog(sysFingertipsLog);
        return result;

    }

    //输入办件填写的基本信息，调用此API后输出创建后的办件编码

//    /*****
//     *
//     * @param applicantType
//     * @param basicApplicationVO
//     * @param addressVO
//     * @param corporationApplicationVO
//     * @return
//     * { "code": 200, "data": 22, "msg": "", "subCode": 20000 }
//     * data办件 id  Long
//     */
//    @ResponseBody
//    @RequestMapping("/testAdd")
//    public String testAdd(String applicantType, BasicApplicationVO basicApplicationVO, AddressVO addressVO, CorporationApplicationVO corporationApplicationVO) {
//
//        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
//        System.out.println(uuid);
//        String bizId = "APPID" + uuid;
//        System.out.println(bizId);
////        basicApplicationVO.setAgentCertificateNum("330102199003075981");
////        basicApplicationVO.setAgentCertificateType("111");
////        basicApplicationVO.setAgentName("代理人王江峰代理人王江峰");
////        basicApplicationVO.setAgentPhone("15845845623");
////        basicApplicationVO.setApplicantName("代理人王江峰代理人王江峰");
//////        basicApplicationVO.setApplicantType(applicantType);
////        basicApplicationVO.setApplicantCertificateType("111");
////        basicApplicationVO.setApplicantCertificateNum("330102199003075981");
////        basicApplicationVO.setApplicantPhone("15845845623");
////        basicApplicationVO.setMatterId(35);
//        int applicationType = 5;
//        if ("1".equals(applicantType)) {//BasicApplicationVO 申报个人信息
//
//            String basicApplicationString = JSON.toJSONString(basicApplicationVO);
//            System.out.println(basicApplicationString);
//            Map map = new HashMap();
//            map.put("basicApplicationVO", basicApplicationVO);
//            map.put("bizId", bizId);
//            map.put("applicationType", applicationType);
//            map.put("applicantType", applicantType);
//
////            String string = FingertipsHttpUtils.httpRequestToString(operUrl+"/case/add","post",map);
////            return  string;
//
//            String jsonString = JSON.toJSONString(map);
//            System.out.println(jsonString);
//            return jsonString;
//
//
//        } else {// CorporationApplicationVO 申报法人信息
//            String s = JSON.toJSONString(corporationApplicationVO);
//            System.out.println(s);
//
//            Map map = new HashMap();
//            map.put("corporationApplicationVO", corporationApplicationVO);
//            map.put("bizId", bizId);
//            map.put("applicationType", applicationType);
//            map.put("applicantType", applicantType);
////            String string = FingertipsHttpUtils.httpRequestToString(operUrl+"/case/add","post",map);
////            return  string;
//        }
//        return "";
//    }

    //创建办件材料信息。
    @ResponseBody
    @RequestMapping("/case/material/add")
    public String caseMaterialAdd(@RequestBody JSONObject object,HttpServletRequest request) {
        //发送请求获取 查询事项材料列表 json
        String result=FingertipsHttpUtils.sendPostJson(operUrl+"/case/material/add",object);
        insertSysFingertipsLog(request,object,UUID.randomUUID().toString().replaceAll("-", ""),"创建办件材料信息");

        if (result != null){
            return result;
        }else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code",2000);
            jsonObject.put("Msg","数据出错啦，请检查输入内容重新提交");
            return  jsonObject.toJSONString();
        }
    }

    //重启预接件补件状态的办件，变成补件待重审状态。
    @ResponseBody
    @RequestMapping("/case/restartPreAuditSupplemented")
    public String caseRestartPreAuditSupplemented(String caseId,HttpServletRequest request) {
        System.out.println("重启预接件补件状态的办件caseId:"+caseId);
//        String result=FingertipsHttpUtils.sendPut(operUrl+"/case/restartPreAuditSupplemented",object);
        Map map = new HashMap();
        map.put("caseId", caseId);
        String result = FingertipsHttpUtils.httpRequestToString(operUrl+"/case/restartPreAuditSupplemented", "put", map);

        HttpSession session = request.getSession();
        String phone = (String) session.getAttribute("dePhone");
        String deUUID = (String) session.getAttribute("deUUID");
        SysFingertipsLog sysFingertipsLog = new SysFingertipsLog();
        sysFingertipsLog.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        sysFingertipsLog.setCreateDate(new Date());
        sysFingertipsLog.setJsonData(caseId);
        sysFingertipsLog.setOperateName("重启预接件补件");
        sysFingertipsLog.setPhone(phone);
        sysFingertipsLog.setUuid(deUUID);
        sysFingertipsLogService.insertLog(sysFingertipsLog);
        if (result != null){
            return result;
        }else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code",2000);
            jsonObject.put("Msg","数据出错啦，请检查输入内容重新提交");
            return  jsonObject.toJSONString();
        }
    }

    //输入办件填写的申报信息、服务信息，调用此API后返回输入的办件 id
    @ResponseBody
    @RequestMapping("/case/submit")
    public String caseSubmit(@RequestBody JSONObject object,HttpServletRequest request) throws Exception {

        //EMS保存
        JSONObject gotInfo  = object.getJSONObject("gotInfo");
        if(gotInfo != null){
            String id = UUID.randomUUID().toString().replaceAll("-","");
            gotInfo.put("txLogisticID",id);
//            gotInfo.put("custCode","90000011275444");
            fingertipsService.createOrder2(gotInfo);

//            记录提交的信息，状态
            FingertipsMessage fingertipsMessage=new FingertipsMessage();
            fingertipsMessage.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            fingertipsMessageService.insert(fingertipsMessage, gotInfo);
            object.remove("gotInfo");
        }

        //发送请求获取 查询事项材料列表 json
        String result=FingertipsHttpUtils.sendPutJson(operUrl+"/case/submit",object);
        //保存办件信息（申请信息applicationInfoVO，服务信息serviceInfoVO，办件 id caseId）
//        String applicationInfoVO = object.getString("applicationInfoVO");
//        String caseId  = object.getString("caseId");
//        String serviceInfoVO = object.getString("serviceInfoVO");

        HttpSession session = request.getSession();
        String phone = (String) session.getAttribute("dePhone");
        String deUUID = (String) session.getAttribute("deUUID");
        SysFingertipsLog sysFingertipsLog = new SysFingertipsLog();
        sysFingertipsLog.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        sysFingertipsLog.setCreateDate(new Date());
        sysFingertipsLog.setJsonData(object.toString());
        sysFingertipsLog.setOperateName("提交办件");
        sysFingertipsLog.setPhone(phone);
        sysFingertipsLog.setUuid(deUUID);
        sysFingertipsLogService.insertLog(sysFingertipsLog);



        if (result != null){
            return result;
        }else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code",2000);
            jsonObject.put("Msg","数据出错啦，请检查输入内容重新提交");
            return  jsonObject.toJSONString();
        }
    }

    //更新办件基本信息
    @ResponseBody
    @RequestMapping("/case/update")
    public String caseUpdate(@RequestBody JSONObject object,HttpServletRequest request) {
        //发送请求获取 查询事项材料列表 json
        String result=FingertipsHttpUtils.sendPutJson(operUrl+"/case/update",object);
        HttpSession session = request.getSession();
        String phone = (String) session.getAttribute("dePhone");
        String deUUID = (String) session.getAttribute("deUUID");
        SysFingertipsLog sysFingertipsLog = new SysFingertipsLog();
        sysFingertipsLog.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        sysFingertipsLog.setCreateDate(new Date());
        sysFingertipsLog.setJsonData(object.toString());
        sysFingertipsLog.setOperateName("更新办件基本信息");
        sysFingertipsLog.setPhone(phone);
        sysFingertipsLog.setUuid(deUUID);
        sysFingertipsLogService.insertLog(sysFingertipsLog);
        if (result != null){
            return result;
        }else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code",2000);
            jsonObject.put("Msg","数据出错啦，请检查输入内容重新提交");
            return  jsonObject.toJSONString();
        }
    }
    //查询指定人的所有办件列表。
    //输入一个身份证件编号或企业证件编号，调用此
    /****
     *
     * @param searcherType 查询者类型，，1代理人， ，2申请人
     * @param certificateNum 个人或企业证件编号，如果searcherType为 1此字段为代理人证件号码，如果为 2此字段为申请人或企业证件号码
     * @param statusString 办件状态（0待预接件 ,1预接件补件， 2补件待重审， ，3预接件退件， 4预受理， ，5收件， 6补齐补正， 7待受理， ，8受理， 9不予受理， ，10受理后补齐补正， 12退件， 13在办， ，14挂起， 15办结， ，16作废 17草稿 18上报）
     * @param pageNo 当前页，若未指定默认为1
     * @param pageSize 每页数量，若未指定默认为10
     * @return
     */
    @ResponseBody
    @RequestMapping("/case/page")
    public String casePage(String searcherType,String certificateNum,String statusString,int pageNo,int pageSize,HttpServletRequest request) {
//        //发送请求获取 查询事项材料列表 json
        Map map = new HashMap();
        map.put("searcherType", searcherType);
        map.put("certificateNum", certificateNum);
        if (statusString != null){
            map.put("statusString", statusString);
        }
        map.put("orderBy", "create_time desc");
        map.put("pageNo", pageNo);
        map.put("pageSize", pageSize);
        String string = FingertipsHttpUtils.httpRequestToString(operUrl + "/case/page", "get", map);
        HttpSession session = request.getSession();
        String phone = (String) session.getAttribute("dePhone");
        String deUUID = (String) session.getAttribute("deUUID");
        SysFingertipsLog sysFingertipsLog = new SysFingertipsLog();
        sysFingertipsLog.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        sysFingertipsLog.setCreateDate(new Date());
        sysFingertipsLog.setJsonData(map.toString());
        sysFingertipsLog.setOperateName("查询指定人的所有办件列表："+statusString);
        sysFingertipsLog.setPhone(phone);
        sysFingertipsLog.setUuid(deUUID);
        sysFingertipsLogService.insertLog(sysFingertipsLog);
        if (string != null){
            return string;
        }else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code",2000);
            jsonObject.put("Msg","数据出错啦，请检查输入内容重新提交");
            return  jsonObject.toJSONString();
        }
//        JSONObject jsonObject = new JSONObject();
//        JSONObject jsonObject2 = new JSONObject();
//        JSONArray data = new JSONArray();
//        for(int i=1;i<=5;i++){
//            int item = i+pageSize*(pageNo-1);
//            JSONObject jsonObject1 = new JSONObject();
//            jsonObject1.put("id",i+item);
//            jsonObject1.put("applicantName","申请人"+item);
//            jsonObject1.put("matterName","事项"+item);
//            jsonObject1.put("status",statusString);
//            data.add(jsonObject1);
//        }
//        jsonObject2.put("data",data);
//        jsonObject.put("code",2000);
//        jsonObject.put("pageNo",1);
//        jsonObject.put("pageSize",5);
//        jsonObject.put("total",13);
//        jsonObject.put("data",jsonObject2);
//        return  jsonObject.toJSONString();
    }

    //查询指定办件的详细信息。
    //输入一个办件id，调用此 API后输出此办件的详细信息
    @ResponseBody
    @RequestMapping("/case/detail")
    public String caseDetail(String caseId,HttpServletRequest request) {
        //发送请求获取 查询事项材料列表 json
        Map map = new HashMap();
        map.put("caseId", caseId);
        String string = FingertipsHttpUtils.httpRequestToString(operUrl + "/case/detail", "get", map);
        HttpSession session = request.getSession();
        String phone = (String) session.getAttribute("dePhone");
        String deUUID = (String) session.getAttribute("deUUID");
        SysFingertipsLog sysFingertipsLog = new SysFingertipsLog();
        sysFingertipsLog.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        sysFingertipsLog.setCreateDate(new Date());
        sysFingertipsLog.setJsonData(map.toString());
        sysFingertipsLog.setOperateName("根据"+caseId+"查询指定办件的详细信息");
        sysFingertipsLog.setPhone(phone);
        sysFingertipsLog.setUuid(deUUID);
        sysFingertipsLogService.insertLog(sysFingertipsLog);
        if (string != null){
            return string;
        }else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code",2000);
            jsonObject.put("Msg","数据出错啦，请检查输入内容重新提交");
            return  jsonObject.toJSONString();
        }
    }

    //查询指定办件的详细信息。
    //输入一个办件id，调用此 API后输出此办件的详细信息
    @RequestMapping("/case/detail/page")
    public String caseDetailPage(String caseId,Model model,String link) {

        model.addAttribute("caseId",caseId);
        model.addAttribute("link",link);
        return "/app/fingertips/caseDetail";
    }

    /***
     * 获取电子表单URL
     * @param formId 表单id
     * @param caseId 办件id
     * @param materialId 材料ID
     * @param urlType 表单样式：0：可编辑 1只读
     * @param idCardNo 证件号
     * @param dfrom 表单来源，，1 pc端， 2 app 7：一窗
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/case/getFormUrl")
    public String caseGetFormUrl(String formId,String caseId,String materialId,String urlType,String idCardNo,String dfrom) {
        Map map = new HashMap();
        map.put("formId", formId);
        map.put("caseId", caseId);
        map.put("materialId", materialId);
        map.put("urlType", urlType);
        map.put("idCardNo", idCardNo);
        map.put("dfrom", dfrom);
        String string = FingertipsHttpUtils.httpRequestToString(operUrl + "/case/getFormUrl", "get", map);
        return string;
    }

    @RequestMapping(value = "/case/getForm")
    public String caseGetForm(String formUrl,Model model) {
        model.addAttribute("formUrl",formUrl);
        return "/app/fingertips/testurl4";
    }

    /****
     * 通过事项查询条件，查询材料场景信息
     * @param matterId 事项id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/matter/scene")
    public String matterScene(String matterId) {
        Map map = new HashMap();
        map.put("matterId", matterId);
        String string = FingertipsHttpUtils.httpRequestToString(itemUrl + "/matter/material/scene", "get", map);
        if (string != null){
            return string;
        }else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code",2000);
            jsonObject.put("Msg","数据出错啦，请检查输入内容重新提交");
            return  jsonObject.toJSONString();
        }
    }
    /****
     * 通过事项查询条件，查询事项材料邮寄地址信息。 取第一条使用。
     * @param matterId 事项id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/matter/Addess")
    public String matterAddess(String matterId) {
        Map map = new HashMap();
        map.put("matterId", matterId);
        String string = FingertipsHttpUtils.httpRequestToString(itemUrl + "/matter/material/listAddessByMatterid", "get", map);
        if (string != null){
            return string;
        }else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code",2000);
            jsonObject.put("Msg","数据出错啦，请检查输入内容重新提交");
            return  jsonObject.toJSONString();
        }
    }

    /****
     * 获取办件材料信息
     * @param caseId 办件 id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/case/material/all")
    public String caseMaterialAll(String caseId) {
        Map map = new HashMap();
        map.put("caseId", caseId);
        String string = FingertipsHttpUtils.httpRequestToString(operUrl + "/case/material/all", "get", map);
        if (string != null){
            return string;
        }else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code",2000);
            jsonObject.put("Msg","数据出错啦，请检查输入内容重新提交");
            return  jsonObject.toJSONString();
        }
    }
    /***
     * 通过事项id查询条件，查询事项大厅信息列表 。
     * @param matterId 事项ID
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/common/hallInfoList")
    public String commonHallInfoList(String matterId) {
        Map map = new HashMap();
        map.put("matterId", matterId);
        String string = FingertipsHttpUtils.httpRequestToString(itemUrl + "/common/hallInfoList", "get", map);
        if (string != null){
            return string;
        }else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code",2000);
            jsonObject.put("Msg","数据出错啦，请检查输入内容重新提交");
            return  jsonObject.toJSONString();
        }
    }

    /***
     * 删除草稿状态办件。。
     * @param caseId 办件id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/case/delete")
    public String commonDelete(String caseId,HttpServletRequest request) {
        Map map = new HashMap();
        map.put("caseId", caseId);
        String string = FingertipsHttpUtils.httpRequestToString(operUrl + "/case/delete", "delete", map);
        HttpSession session = request.getSession();
        String phone = (String) session.getAttribute("dePhone");
        String deUUID = (String) session.getAttribute("deUUID");
        SysFingertipsLog sysFingertipsLog = new SysFingertipsLog();
        sysFingertipsLog.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        sysFingertipsLog.setCreateDate(new Date());
        sysFingertipsLog.setJsonData(map.toString());
        sysFingertipsLog.setOperateName("删除草稿状态办件："+caseId);
        sysFingertipsLog.setPhone(phone);
        sysFingertipsLog.setUuid(deUUID);
        sysFingertipsLogService.insertLog(sysFingertipsLog);
        if (string != null){
            return string;
        }else {
            Map map1 = new HashMap();
            map1.put("code", 5000);
            map1.put("msg","数据出错啦，请检查输入内容重新提交");
            return  map1.toString();
        }
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("code",2000);
//        jsonObject.put("Msg","");
//        return  jsonObject.toJSONString();
    }


    @RequestMapping(value = "/testMy")
    public String testMy(String authKey,Model model) throws Exception {
        String phone = EncryptUtil.aesDecrypt(authKey,aesKey);
        model.addAttribute("dePhone", phone);
        UserHistoryVO userHistoryVO = fingertipsService.findUserHistory(phone);
        System.out.println("userHistoryVO："+userHistoryVO);
        model.addAttribute("userPhone", userHistoryVO);

        return "/app/fingertips/testmy";
    }

    @RequestMapping(value = "/testdetail")
    public String testDetail(String caseId) throws Exception {

        return "/app/fingertips/testmy2";
    }


    @RequestMapping(value = "/testUrlJson")
    public String testUrlJson() {
        return "/app/fingertips/testurl";
    }

    @RequestMapping(value = "/testUrl2")
    public String testUrl2() {
        return "/app/fingertips/testurl2";
    }

    @RequestMapping(value = "/testUrlPut")
    public String testUrlPut() {
        return "/app/fingertips/testurl3";
    }


    @RequestMapping(value = "/testUpload")
    public String testUpload() {
        return "/app/fingertips/testUpload";
    }

    @ResponseBody
    @RequestMapping("/testUrlInterface")
    public String testUrlInterface(String parm, String url) {

        System.out.println(parm);
        JSONObject jsonObject = JSONObject.parseObject(parm);
        String string = null;

        try {
            string = FingertipsHttpUtils.sendPostJson(url, jsonObject);
            return string;

        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();

        } finally {
            return string;
        }

//        return "";
    }

    @ResponseBody
    @RequestMapping("/testUrlInterface3")
    public String testUrlInterface3(String parm, String url) {

        System.out.println(parm);
        JSONObject jsonObject = JSONObject.parseObject(parm);
        String string = null;

        try {
            string = FingertipsHttpUtils.sendPutJson(url, jsonObject);
            return string;

        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();

        } finally {
            return string;
        }

//        return "";
    }
    @ResponseBody
    @RequestMapping("/testUrlInterface2")
    public String testUrlInterface2(String type, String parm, String url) {

        System.out.println(type);
        System.out.println(url);
        System.out.println(parm);
        Map map = queryString2Map(parm);

        String string = null;

        try {
            if ("get".equals(type)) {
                string = FingertipsHttpUtils.sendGet(url, parm);
            } if ("post".equals(type)) {
                string = FingertipsHttpUtils.sendPost(url, parm);
            } else {
                string = FingertipsHttpUtils.httpRequestToString(url, type,map);
            }

//            string = FingertipsHttpUtils.httpRequestToString(url,type,map);
            return string;

        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();

        } finally {
            return string;
        }

//        return "";
    }

    /***
     * 查询字符串转换成Map
     * name1=key1&name2=key2&...
     * @param queryString
     */
    public static Map queryString2Map(String queryString) {
        if (null == queryString || "".equals(queryString)) {
            return null;
        }
        Map m = new HashMap();
        String[] strArray = queryString.split("&");
        for (int index = 0; index < strArray.length; index++) {
            String pair = strArray[index];
            System.out.println();
            putMapByPair(pair, m);
        }
        return m;
    }

    /**
     * 把键值添加至Map<br/>
     * pair:name=value
     *
     * @param pair name=value
     * @param m
     */
    public static void putMapByPair(String pair, Map m) {
        if (null == pair || "".equals(pair)) {
            return;
        }
        int indexOf = pair.indexOf("=");
        if (-1 != indexOf) {
            String k = pair.substring(0, indexOf);
            String v = pair.substring(indexOf + 1, pair.length());
            if (null != k && !"".equals(k)) {
                m.put(k, v);
            }
        } else {
            m.put(pair, "");
        }
    }

    /**
     * 插入更新用户资料
     * @param object
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateUserHistory")
    public String updateUserHistory(@RequestBody JSONObject object){
        Resp entity = null;
        UserHistoryVO userHistoryVO = new UserHistoryVO();
        userHistoryVO.setAgentCertificateNum(object.getString("agentCertificateNum"));
        userHistoryVO.setAgentName(object.getString("agentName"));
        userHistoryVO.setAgentPhone(object.getString("agentPhone"));
        userHistoryVO.setApplicantCertificateNum(object.getString("applicantCertificateNum"));
        userHistoryVO.setApplicantName(object.getString("applicantName"));
        userHistoryVO.setApplicantPhone(object.getString("applicantPhone"));
        userHistoryVO.setApplicantType(object.getString("applicantType"));

        userHistoryVO.setEnApplicantName(object.getString("enApplicantName"));
        userHistoryVO.setEnApplicantCertificateNum(object.getString("enApplicantCertificateNum"));
        userHistoryVO.setEnApplicantPhone(object.getString("enApplicantPhone"));

        try {
            //判断是否已有记录，有就更新，没有就新增
            int existCount = fingertipsService.findUserHistoryByPhone(userHistoryVO);
            if(existCount > 0){
                int updateCount = fingertipsService.updateUserHistoryByPhone(userHistoryVO);
                if (updateCount > 0) {
                    entity = new Resp(RespCode.SUCCESS);
                } else {
                    entity = new Resp(RespCode.DEFAULT);
                }
            }else {
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                userHistoryVO.setId(uuid);
                int insertCount = fingertipsService.addUserHistory(userHistoryVO);
                if (insertCount > 0) {
                    entity = new Resp(RespCode.SUCCESS);
                } else {
                    entity = new Resp(RespCode.DEFAULT);
                }
            }
        }catch (Exception e){
            entity = new Resp(RespCode.DEFAULT);
            e.printStackTrace();
        }
        return JSON.toJSONString(entity);
    }

    /**
     * 查询用户资料
     * @param encPhone
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findUserHistory")
    public String findUserHistory(String encPhone){
        Resp entity = null;
        try{
            String phone = EncryptUtil.aesDecrypt(encPhone,aesKey);
            UserHistoryVO userHistoryVO = fingertipsService.findUserHistory(phone);
            if(userHistoryVO != null) {
                entity = new Resp(RespCode.SUCCESS, userHistoryVO);
            }else{
                entity = new Resp(RespCode.DEFAULT);
            }
        }catch (Exception e){
            e.printStackTrace();
            entity = new Resp(RespCode.DEFAULT);
        }
        return JSON.toJSONString(entity);
    }

    /**
     * 插入用户联系地址
     * @param object
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateUserAddress")
    public String updateUserAddress(@RequestBody JSONObject object){
        Resp entity = null;
        Map<String,String> userAddress = new HashMap<>();
        userAddress.put("agentPhone",object.getString("agentPhone"));
        userAddress.put("provinceName",object.getString("provinceName"));
        userAddress.put("province",object.getString("province"));
        userAddress.put("cityName",object.getString("cityName"));
        userAddress.put("city",object.getString("city"));
        userAddress.put("district",object.getString("district"));
        userAddress.put("districtName",object.getString("districtName"));
        userAddress.put("detail",object.getString("detail"));
        userAddress.put("postcode",object.getString("postcode"));
        userAddress.put("flag","0");
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        userAddress.put("id",uuid);
        try {
            int existCount = fingertipsService.findUserAddressCountByAgentPhone(userAddress);
            if(existCount > 0){
                int updateCount = fingertipsService.updateUserAddress(userAddress);
                if (updateCount > 0) {
                    entity = new Resp(RespCode.SUCCESS);
                } else {
                    entity = new Resp(RespCode.DEFAULT);
                }
            }else {
                int insertCount = fingertipsService.addUserAddress(userAddress);
                if (insertCount > 0) {
                    entity = new Resp(RespCode.SUCCESS);
                } else {
                    entity = new Resp(RespCode.DEFAULT);
                }
            }
        }catch (Exception e){
            entity = new Resp(RespCode.DEFAULT);
            e.printStackTrace();
        }
        return JSON.toJSONString(entity);
    }

    /**
     * 查询用户联系地址
     * @param encPhone
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findUserAddressByAgentPhone")
    public String findUserAddressByAgentPhone(String encPhone){
        Resp entity = null;
        try{
            String agentPhone = EncryptUtil.aesDecrypt(encPhone,aesKey);
            Map<String,String> map = fingertipsService.findUserAddressByAgentPhone(agentPhone);
            entity = new Resp(RespCode.SUCCESS,map);
        }catch (Exception e){
            e.printStackTrace();
            entity = new Resp(RespCode.DEFAULT);
        }
        return JSON.toJSONString(entity);
    }

    @ResponseBody
    @RequestMapping(value = "/SendMail")
    public String sendMail(@RequestBody JSONObject params,HttpServletRequest request) {
//String subject,  Map<String, String> attachmentMap,String receiver,String sender
        Map<String, String> attachmentMap = new HashMap<>();

        String subject = params.getString("title");
//        System.out.println(subject);
        String text = "此邮件不需要回复，请勿回复";
        String fileInfo = params.getString("fileInfo");
//        System.out.println(fileInfo);
        JSONArray jsonArray = params.getJSONArray("fileInfo");
//        JSONArray jsonArray= new JSONArray(fileInfo);

        for(int i=0;i<jsonArray.size();i++) {
            String url = (String) jsonArray.getJSONObject(i).get("url");
            String fileName = (String) jsonArray.getJSONObject(i).get("fileName");
            attachmentMap.put(fileName,url);
        }
//        attachmentMap.put("附件1.doc","http://sdbst2.shunde.gov.cn:8090/sdbstImages/userfiles/123.doc");
        String receiver = params.getString("sendEmail");
//        System.out.println(receiver);
        Resp entity = null;
        try {
            MailUtils mailUtils = new MailUtils();
            String result = mailUtils.sendHtmlMail(javaMailSender,subject,text,attachmentMap,receiver,sender);

            HttpSession session = request.getSession();
            String phone = (String) session.getAttribute("dePhone");
            String deUUID = (String) session.getAttribute("deUUID");
            SysFingertipsLog sysFingertipsLog = new SysFingertipsLog();
            sysFingertipsLog.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            sysFingertipsLog.setCreateDate(new Date());
            sysFingertipsLog.setJsonData(attachmentMap.toString());
            sysFingertipsLog.setOperateName("发送邮件："+subject);
            sysFingertipsLog.setPhone(phone);
            sysFingertipsLog.setUuid(deUUID);
            sysFingertipsLogService.insertLog(sysFingertipsLog);

//            System.out.println("结果："+result);
            if ("200".equals(result)){
                entity = new Resp(RespCode.SUCCESS);
            }else {
                entity = new Resp(RespCode.DEFAULT);
                entity.setMsg(result);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
            entity = new Resp(RespCode.DEFAULT);
        }
        return JSON.toJSONString(entity);
    }

    @RequestMapping(value = "/getFingertipsHistory")
    public String getFingertipsHistory(String link,Model model,HttpServletRequest request) throws Exception {
        String phone =null;
        String idCard = "";
        String agentName = "";
        String agentPhone = "";
        if(link == null || link == ""){
            phone = "";
        }else {
//            phone = EncryptUtil.aesDecrypt(authKey,aesKey);
            String string = EncryptUtil.aesDecrypt(link,aesKey);
            JSONObject jsonObject = JSONObject.parseObject(string);
            phone = jsonObject.getString("mobile");
            if ("null".equals(jsonObject.getString("link_person_cid"))){
                idCard = "";
            }else {
                idCard = jsonObject.getString("link_person_cid");
            }
            if (!"null".equals(jsonObject.getString("link_person_name"))){
                agentName = jsonObject.getString("link_person_name");
            }
            if (!"null".equals(jsonObject.getString("mobile"))){
                agentPhone = jsonObject.getString("mobile");
            }
        }
        model.addAttribute("dePhone", phone);
        UserHistoryVO userHistoryVO = fingertipsService.findUserHistory(phone);

        if (userHistoryVO == null){
            UserHistoryVO userHistoryVO1 = new UserHistoryVO();
            userHistoryVO1.setAgentCertificateNum(idCard);
            userHistoryVO1.setAgentName(agentName);
            userHistoryVO1.setAgentPhone(agentPhone);
            model.addAttribute("userPhone", userHistoryVO1);
        }else {
            if (idCard != "" && idCard.length() > 0 ){
                userHistoryVO.setAgentCertificateNum(idCard);
            }
            model.addAttribute("userPhone", userHistoryVO);
        }

////        System.out.println("userHistoryVO："+userHistoryVO);
//        model.addAttribute("userPhone", userHistoryVO);
        model.addAttribute("link", link);

        HttpSession session = request.getSession();
        session.setAttribute("dePhone",phone);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        session.setAttribute("deUUID", uuid);
//        String phone1 = (String) session.getAttribute("dePhone");
//        String deUUID = (String) session.getAttribute("deUUID");
        SysFingertipsLog sysFingertipsLog = new SysFingertipsLog();
        sysFingertipsLog.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        sysFingertipsLog.setCreateDate(new Date());
        sysFingertipsLog.setJsonData(phone+"指尖办图片跳转"+phone);
        sysFingertipsLog.setOperateName(phone+"指尖办图片跳转");
        sysFingertipsLog.setPhone(phone);
        sysFingertipsLog.setUuid(uuid);
        sysFingertipsLogService.insertLog(sysFingertipsLog);
        return "/app/fingertips/history";

//        return "redirect:http://support.shunde.gov.cn";
    }

    @RequestMapping(value = "/addCase")//补件
    public String addCase(String caseId,String link, String matterId,Model model,HttpServletRequest request) throws Exception {
        model.addAttribute("caseId",caseId);
        model.addAttribute("matterId", matterId);

        String string = EncryptUtil.aesDecrypt(link,aesKey);
        JSONObject jsonObject = JSONObject.parseObject(string);
        String phone = jsonObject.getString("mobile");

        model.addAttribute("dePhone", phone);
        model.addAttribute("dePhoneEnc", link);

        return "/app/fingertips/bujian";
    }

    @RequestMapping(value = "/addCaogao")//草稿重启
    public String addCaogao(String caseId,String link, String matterId,Model model,HttpServletRequest request) throws Exception {
        model.addAttribute("caseId",caseId);
        model.addAttribute("matterId", matterId);

        String string = EncryptUtil.aesDecrypt(link,aesKey);
        JSONObject jsonObject = JSONObject.parseObject(string);
        String phone = jsonObject.getString("mobile");

        model.addAttribute("dePhone", phone);
        model.addAttribute("dePhoneEnc", link);

        return "/app/fingertips/";
    }

    @ResponseBody
    @RequestMapping(value = "/ems/createOrder")
    public Resp createOrder(@RequestBody JSONObject object) throws Exception {
        System.out.println(object);
//      JSONObject jsonObject = new JSONObject();

        String caseId = object.getString("caseId");
        //通过caseId查询ems，若有则更新，没有则新建
        FingertipsEMS ems = fingertipsService.getFingertipsEMSByCaseId(caseId);
        if(ems != null){
            //更新ems
            ems.setCollects(object.getJSONObject("collect").toString());
            ems.setSender(object.getJSONObject("sender").toString());
            ems.setReceiver(object.getJSONObject("receiver").toString());
            fingertipsService.updateEMS(ems);
        }else{
            String id = UUID.randomUUID().toString().replaceAll("-","");
            object.put("txLogisticID",id);
            object.put("custCode","90000010850273");
            fingertipsService.createOrder2(object);
        }
//        Map map = new HashMap();
//        map.put("matterId", object.getString("matterId"));
//        String string = FingertipsHttpUtils.httpRequestToString(itemUrl + "/matter/material/listAddessByMatterid", "get", map);
//        String string = FingertipsHttpUtils.httpRequestToString("http://isd1.shunde.gov.cn/sdbst/app/fingertips/matter/Addess", "get", map);
//        JSONObject jsonAddress = JSONObject.parseObject(string);
//        String dataAddress = jsonAddress.getString("data");
//        JSONObject address = JSONObject.parseObject(dataAddress);
//
//        System.out.println(address.getString("addresseeName"));
//        JSONObject collect = new JSONObject();
//        collect.put("name",address.getString("addresseeName"));
//        collect.put("postCode",address.getString("postCode"));
//        collect.put("prov",address.getString("province"));
//        collect.put("city",address.getString("city"));
//        collect.put("address",address.getString("district"));
//        collect.put("mobile",address.getString("detailAddress"));
//
//        object.put("collect",collect);

//        System.out.println(id);
//        jsonObject.put("txLogisticID",id);
//
////        揽收地址信息（上门取件地址）
//        JSONObject collect = new JSONObject();
//        collect.put("name","测试");
//        collect.put("postCode","580000");
//        collect.put("prov","广东省");
//        collect.put("city","佛山市");
//        collect.put("address","具体地址");
//        collect.put("mobile","18688281234");
//
////        寄件人地址信息（面单使用）
//        JSONObject sender = new JSONObject();
//        sender.put("name","测试1");
//        sender.put("postCode","580001");
//        sender.put("prov","广东省");
//        sender.put("city","广州市");
//        sender.put("address","具体地址1");
//        sender.put("mobile","18688281111");
//
//
//        JSONObject receiver = new JSONObject();
//        receiver.put("name","测试2");
//        receiver.put("postCode","580002");
//        receiver.put("prov","广东省");
//        receiver.put("city","深圳市");
//        receiver.put("address","具体地址2");
//        receiver.put("mobile","18688282222");
//
//        jsonObject.put("collect",collect);
//        jsonObject.put("sender",sender);
//        jsonObject.put("receiver",receiver);
//        jsonObject.put("orderType",1);


        return new Resp(RespCode.SUCCESS);
//        JSONObject resp = new JSONObject();
//        resp.put("success","T");
//        resp.put("errorCode","");
//        resp.put("errorMsg","");
//        return resp;
    }


    @ResponseBody
    @RequestMapping(value = "/cancellation")
    public Resp cancellation(String txLogisticID,String code){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("txLogisticID",txLogisticID);

        jsonObject.put("cancelCode",code);

        fingertipsService.cancellation(jsonObject);
        return new Resp(RespCode.SUCCESS);
    }

    @ResponseBody
    @RequestMapping(value = "/reminder")
    public Resp reminder(String encPhone){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("txLogisticID",encPhone);

        fingertipsService.reminder(jsonObject);
        return new Resp(RespCode.SUCCESS);
    }
//EMS订单状态反馈接口
    @ResponseBody
    @RequestMapping(value = "/ems/statuspush")
    public JSONObject statuspush(String timestamp,String version,String  method,String  format,String  app_key,String  authorization,String  charset,
                            String  txLogisticID,String  desc,String  status,String  mailNum,
                             String  partner_id,String sign){

//        Map<String,String> params = new HashedMap();
//
//        //构造必要参数
//        params.put("timestamp", timestamp);
//        params.put("version",version);
//        params.put("method",method);
//        params.put("format",format);
//        params.put("app_key",app_key);
//
//        params.put("txLogisticID",txLogisticID);
//        params.put("status",status);
//
//        String charset1 = "UTF-8";
//        if (charset != null){
//            params.put("charset",charset);
//            charset1 = charset;
//        }
//
//        if (partner_id != null){
//            params.put("partner_id",partner_id);
//        }
//        if (desc != null){
//            params.put("desc",desc);
//        }
//        if (mailNum != null){
//            params.put("mailNum",mailNum);
//        }
//        params.put("authorization","408a6c32e61d3ad5cb5c4e0cb3d2b089");
//
//        //合成签名
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append(CreateEMSParamUtils.getSortParams(params));
//        stringBuilder.append("45a9fdfbea2b22d8cbdf559f419d69ba");
//
//        //签名加密
//        String signOwn = CreateEMSParamUtils.sign(stringBuilder.toString(),charset1);
//        System.out.println("sign:============="+sign);
//        System.out.println("timestamp:"+timestamp);
//        System.out.println("version:"+version);
//        System.out.println("method:"+method);
//        System.out.println("format:"+format);
//        System.out.println("app_key:"+app_key);
//        System.out.println("charset:"+charset);
//        System.out.println("txLogisticID:"+txLogisticID);
//        System.out.println("desc:"+desc);
//        System.out.println("status:"+status);
//        System.out.println("mailNum:"+mailNum);
//        System.out.println("partner_id:"+partner_id);
//        if (signOwn.equals(sign)){
            fingertipsService.updateEMSById(txLogisticID,status,desc,mailNum,new Date());
            System.out.println("验证成功");
//            return new Resp(RespCode.SUCCESS);
        JSONObject resp = new JSONObject();
        resp.put("success","T");
        resp.put("errorCode","");
        resp.put("errorMsg","");
        return resp;
//        }else {
//            return  new Resp(RespCode.DEFAULT,"验证失败");
//        }

    }

//    public void sendEMSTask(){
//        List<FingertipsEMS> list = fingertipsService.getFingertipsEMSByNotEms();//获取所有没调用EMS接口的订单
//        for (int i = 0; i < list.size(); i++) {
//            FingertipsEMS fingertipsEMS = list.get(i);
//            Map map = new HashMap();
//            map.put("caseId", fingertipsEMS.getCaseId());
//            String string = FingertipsHttpUtils.httpRequestToString(operUrl + "/case/detail", "get", map);
//            JSONObject jsonObject = JSONObject.parseObject(string);
//            JSONObject data = jsonObject.getJSONObject("data");
//            String status = data.getString("status");
//            if ("预受理".equals(status)){
////揽收地址信息（上门取件地址）
//                JSONObject collect = JSONObject.parseObject(fingertipsEMS.getCollects());
////寄件人地址信息（面单使用）
//                JSONObject sender = JSONObject.parseObject(fingertipsEMS.getSender());
////收件人地址
//                JSONObject receiver = JSONObject.parseObject(fingertipsEMS.getReceiver());
//                JSONObject gotInfo = new JSONObject();
//                gotInfo.put("collect",collect);
//                gotInfo.put("sender",sender);
//                gotInfo.put("receiver",receiver);
//                gotInfo.put("orderType",1);
//                gotInfo.put("remark",fingertipsEMS.getRemark());
//
//                gotInfo.put("txLogisticID",fingertipsEMS.getId());
//                gotInfo.put("custCode","90000010850273");
//                fingertipsService.createOrder(gotInfo);
//            }
//        }
//    }

    //通过事项查询条件，查询事项信息。
    @ResponseBody
    @RequestMapping("/matter/all")
    public String matterAll(String orgId,String starProperty,String org,@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(value = "pageSize", defaultValue = "100") Integer pageSize) {

        Map map = new HashMap();
        map.put("orgId", orgId);
        map.put("starProperty", starProperty);
        map.put("pageNo", pageNo);
        map.put("pageSize", pageSize);
        String string = FingertipsHttpUtils.httpRequestToString(itemUrl + "/matter/all", "get", map);

        if (string != null){
            return string;
        }else {
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("code",2000);
            jsonObject1.put("Msg","数据出错啦，请检查输入内容重新提交");
            return  jsonObject1.toJSONString();
        }
    }

    //跳转更多
    @RequestMapping("/matter/all/page")
    public String matterAllPage(Model model,String orgId,String orgName,String link) {

        model.addAttribute("orgId",orgId);
        model.addAttribute("orgName",orgName);
        model.addAttribute("link",link==null?"":link);

        return "/app/fingertips/allPage";
    }

}
