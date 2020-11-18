package com.unifs.sdbst.app.controller.life;

import com.unifs.sdbst.app.annotation.ControlLog;
import com.unifs.sdbst.app.base.Resp;
import com.unifs.sdbst.app.bean.life.ZtreeEntity;
import com.unifs.sdbst.app.enums.RespCode;
import com.unifs.sdbst.app.service.life.VideoMonitorService;
import com.unifs.sdbst.app.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @title: VideoMonitorController
 * @projectName sdbst
 * @description: 视频监控
 * @author： 张恭雨
 * @date 2019/8/27 9:56
 */
@Controller
@RequestMapping(value = "/app/videoMonitor")
public class VideoMonitorController {
    @Autowired
    private VideoMonitorService monitorService;
    private Resp resp;
    private static final String VIDEO_QUALITY_URL = "cms/rest/VideoQualityDefinition";
    private static final String PLAY_URL = "cms/rest/GetPlayUrl";
    /*注入配置参数*/
    @Value("${video.monitor.url}")
    private String URL;
    @Value("${video.monitor.username}")
    private String username;
    @Value("${video.monitor.password}")
    private String password;

    //跳转视频监控列表
    @ControlLog(context = "跳转视频监控列表",operateType = "/toList")
    @RequestMapping(value = "/toList", method = {RequestMethod.GET})
    public String toList() {
        return "/app/life/videoMonitorList";
    }

    //跳转视频播放页
    @ControlLog(context = "跳转视频播放页",operateType = "/playPage")
    @RequestMapping(value = "/playPage", method = {RequestMethod.GET})
    public String playPage(HttpServletRequest request, Model model) {
        String type = request.getParameter("type");
        String deviceId = request.getParameter("deviceId");
        model.addAttribute("type", type);
        model.addAttribute("deviceId", deviceId);
        return "/app/life/playPage";
    }

    /*获取视频监控列表*/
    @ControlLog(context = "获取视频监控列表",operateType = "/list")
    @RequestMapping(value = {"/list"}, method = {RequestMethod.GET})
    @ResponseBody
    public Resp VCUList() {
        if (URL != null && !URL.isEmpty() && username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
            List<ZtreeEntity> list = this.monitorService.getVcuList(URL, username, password);
            resp = new Resp(RespCode.SUCCESS);
            resp.setData(list);
            return resp;
        } else {
            resp = new Resp(RespCode.DEFAULT);
            resp.setMsg("帐号等信息不能为空！");
            return resp;
        }
    }


    //视频质量查询
    @ControlLog(context = "视频质量查询",operateType = "/quality")
    @RequestMapping(value = {"/quality"}, method = {RequestMethod.GET})
    @ResponseBody
    public Resp videoQuality() {
        if (!URL.endsWith("/")) {
            URL = URL + "/";
        }

        Map<String, Object> rsp = this.monitorService.getVideoQuality(URL + VIDEO_QUALITY_URL, username, password);
        resp = new Resp(RespCode.SUCCESS);
        resp.setData(rsp);
        return resp;
    }

    @ControlLog(context = "获取播放链接",operateType = "/playUrl")
    @RequestMapping(value = {"/playUrl"}, method = {RequestMethod.GET})
    @ResponseBody
    public Resp playUrl(HttpServletRequest request) {
        String deviceId = request.getParameter("deviceId");
        String type = request.getParameter("type");
        Long beginTime = HttpUtil.string2Long(request.getParameter("beginTime"));
        Long endTime = HttpUtil.string2Long(request.getParameter("endTime"));
        String videoQuality = request.getParameter("videoQuality");
        if (!URL.endsWith("/")) {
            URL = URL + "/";
        }

        Map<String, Object> params = new HashMap();
        params.put("deviceId", deviceId);
        params.put("type", type);
        params.put("beginTime", beginTime);
        params.put("endTime", endTime);
        params.put("videoQuality", videoQuality);

        Map<String, Object> result = this.monitorService.getPlayUrlRspFromCMS(URL + PLAY_URL, username, password, params);
        resp = new Resp(RespCode.SUCCESS);
        resp.setData(result);
        return resp;
    }
    @ControlLog(context = "",operateType = "/playList")
    @RequestMapping(value = {"/playList"},method = {RequestMethod.GET})
    @ResponseBody
    public Resp getPlayListByType(HttpServletRequest request) {
        String type = request.getParameter("type");
        String beginIndex = request.getParameter("beginIndex");
        String deviceId = request.getParameter("deviceId");
        new HashMap();
        Map<String, Object> params = new HashMap();
        if ("cv_vod".equals(type) || "pu_vod".equals(type)) {
            String command = "GetRecordInfoReq";
            if ("pu_vod".equals(type)) {
                command = "GetVcuRecordInfo";
            }

            if (beginIndex != null && beginIndex.length() > 0) {
                params.put("beginIndex", Integer.parseInt(beginIndex));
            } else {
                params.put("beginIndex", 1);
            }

            params.put("vapCommand", command);
            params.put("deviceId", deviceId);
            params.put("scId", "");
        }
        Map<String, Object> map = monitorService.getPlayList(URL, username, password, params);
        resp = new Resp(RespCode.SUCCESS);
        resp.setData(map);
        return resp;
    }
}
