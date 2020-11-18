package com.unifs.sdbst.app.controller.life;

import com.unifs.sdbst.app.bean.life.BankEntity.BankInfo;
import com.unifs.sdbst.app.bean.life.BankEntity.BankService;
import com.unifs.sdbst.app.service.life.BankMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/bankMap")
public class BankMapController {

    @Autowired
    private BankMapService bankMapService;

    //银行分布地图
    @RequestMapping(value = "map")
    public String map() {
        return "app/traffic/bankMap";
    }

    //银行服务事项
    @RequestMapping(value = "serviceList")
    public String serviceList() {
        return "app/traffic/serviceList";
    }

    @PostMapping(value = "getAllBankInfo")
    @ResponseBody
    public Map<String,Object> getAllBankInfo(String lng, String lat){
        List<BankInfo> list = bankMapService.getAllBankInfo(lng,lat);
        Map<String,Object> map = new HashMap<>();
        map.put("list", list);
        return map;
    }

    @GetMapping(value = "getAllBankInfoList")
    @ResponseBody
    public Map<String,Object> getAllBankInfoList(){
        List<BankInfo> list = bankMapService.getAllBankInfoList();
        Map<String,Object> map = new HashMap<>();
        map.put("list", list);
        return map;
    }

    @GetMapping(value = "/getServiceList")
    @ResponseBody
    public List<BankService> getServiceList(){
        return bankMapService.getServiceList();
    }
}
