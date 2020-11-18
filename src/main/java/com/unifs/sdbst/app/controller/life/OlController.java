package com.unifs.sdbst.app.controller.life;


import com.unifs.sdbst.app.bean.life.OlEntity;
import com.unifs.sdbst.app.service.life.OlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/ol")
public class OlController {
    @Autowired
    private OlService olService;

    //添加
    @RequestMapping("add")
    @ResponseBody
    public Map<String, Object> olfirst(String identification, String sex, String name, String phone, String qq, String email) {
        Map<String, Object> map = new HashMap<String, Object>();
        int num = olService.insert(identification, sex, name, phone, qq, email);
        map.put("result", "success");
        return map;
    }

    @RequestMapping("all")
    @ResponseBody
    public Map<String, Object> findAll(){
        Map<String, Object> map = new HashMap<String, Object>();
        List<OlEntity> ol = olService.queryByType();
        map.put("result", ol);
        return map;
    }

    //web index
    @RequestMapping(value = "web/index")
    public String index() {
        return "app/life/OnlineRegistration/index";
    }
}
