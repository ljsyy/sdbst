package com.unifs.sdbst.app.controller.fever;

import com.alibaba.fastjson.JSONObject;
import com.unifs.sdbst.app.base.Resp;
import com.unifs.sdbst.app.bean.fever.FeverAdmin;
import com.unifs.sdbst.app.bean.fever.FeverPatient;
import com.unifs.sdbst.app.bean.fever.FeverUser;
import com.unifs.sdbst.app.enums.RespCode;
import com.unifs.sdbst.app.service.fever.FeverService;
import com.unifs.sdbst.app.utils.AES;
import org.bouncycastle.math.raw.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping(value = "fever")
public class FeverController {

    @Autowired
    private FeverService feverService;
    private String key = "feverPatient2020";
    /**
     * 发热患者录入页面
     * @return
     */
    @GetMapping("home")
    public String home(String urlparams, Model model){
        try {
            //解析参数信息
            urlparams = AES.decrypt(urlparams, key);
            JSONObject object = JSONObject.parseObject(urlparams);
            String userId = object.getString("userId");
            String mobile = object.getString("mobile");
            String cid = object.getString("cid");
            String name = object.getString("name");

            FeverUser feverUser = feverService.findUserByMobileAndCid(mobile, cid);
            if (feverUser == null) {
                return "/app/fever/error";
            } else {
                model.addAttribute("userId", userId);
                model.addAttribute("mobile", mobile);
                model.addAttribute("cid", cid);
                model.addAttribute("name", name);
                return "/app/fever/home";
            }
        }catch (Exception e){
            return "/app/fever/error";
        }
    }

    /**
     * 发热患者信息录入
     * @param feverPatient
     * @return
     */
    @PostMapping(value = "patientInfoSubmit")
    @ResponseBody
    public Resp patientInfoSubmit(FeverPatient feverPatient){
        feverPatient.setId(UUID.randomUUID().toString().replace("-",""));
        Date date = new Date();
        feverPatient.setCreateTime(date);
        feverPatient.setUpdateTime(date);
        feverPatient.setDelFlag("0");
        feverPatient.setCheckFlag("0");
        feverService.patientInfoSubmit(feverPatient);
        return new Resp(RespCode.SUCCESS);
    }

    @GetMapping(value = "patientCount")
    @ResponseBody
    public Resp patientCount(String userId,String startTime,String endTime){
        List<Map<String,String>> list = feverService.patientCount(userId,startTime,endTime);
        return new Resp(RespCode.SUCCESS,list);
    }

    /**
     * 录入患者历史记录
     * @param userId
     * @param startTime
     * @param endTime
     * @return
     */
    @GetMapping(value = "patientRecord")
    @ResponseBody
    public Resp patientRecord(String userId,String startTime,String endTime){
        List<FeverPatient> list = feverService.patientRecord(userId,startTime,endTime);
        return new Resp(RespCode.SUCCESS,list);
    }

    /**
     * 更新记录
     * @param feverPatient
     * @return
     */
    @PostMapping(value = "updatePatientRecord")
    @ResponseBody
    public Resp updatePatientRecord(FeverPatient feverPatient){
        feverService.updatePatientRecord(feverPatient);
        return new Resp(RespCode.SUCCESS);
    }

}
