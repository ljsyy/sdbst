package com.unifs.sdbst.app.controller.life.health;

import com.unifs.sdbst.app.base.Resp;
import com.unifs.sdbst.app.enums.RespCode;
import com.unifs.sdbst.app.service.life.health.HealthTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "data/health")
public class HealthTableController {

    @Autowired
    private HealthTableService healthTableService;

    @ResponseBody
    @RequestMapping(value = "jiaotong")
    public Resp jiaotong( HttpServletRequest request) throws Exception {

        List<Map<String, Object >> list=healthTableService.selectByjiaotong();
        return new Resp(RespCode.SUCCESS,list);

    }

    @ResponseBody
    @RequestMapping(value = "selectBihuan"/*, method = RequestMethod.POST*/)
    public Resp selectBihuan( HttpServletRequest request) throws Exception {

        List<Map<String, Object >> list=healthTableService.selectBihuan();
        return new Resp(RespCode.SUCCESS,list);

    }

    @ResponseBody
    @RequestMapping(value = "selectByHubei"/*, method = RequestMethod.POST*/)
    public Resp selectByHubei( HttpServletRequest request) throws Exception {

        List<Map<String, Object >> list=healthTableService.selectByHubei();
        return new Resp(RespCode.SUCCESS,list);

    }


}
