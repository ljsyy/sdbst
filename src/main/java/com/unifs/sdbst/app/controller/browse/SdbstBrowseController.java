package com.unifs.sdbst.app.controller.browse;

import com.unifs.sdbst.app.bean.browse.SdbstBrowse;
import com.unifs.sdbst.app.bean.browse.SdbstOffice;
import com.unifs.sdbst.app.service.browse.SdbstBrowseService;
import com.unifs.sdbst.app.service.browse.SdbstOfficeService;
import com.unifs.sdbst.app.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.net.www.http.HttpClient;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping(value = "browse/sdbstBrowse")
public class SdbstBrowseController {

    @Autowired
    private SdbstOfficeService sdbstOfficeService;
    @Autowired
    private SdbstBrowseService sdbstBrowseService;

    /**
     * 访问量保存接口
     *
     * @param sdbstBrowse
     * @param sex
     * @param name
     * @param model
     * @param redirectAttributes
     * @param response
     * @param request
     */
//    @SuppressWarnings("unused")
    @ResponseBody
    @RequestMapping(value = "save")
    public void save(SdbstBrowse sdbstBrowse, HttpServletRequest request) {
//        System.out.println(sdbstBrowse.getModelId());
        if (sdbstBrowse.getModelId() == null || sdbstBrowse.getModelId().length() == 0) {
            sdbstBrowse.setModelId("");
        } else if ("undefined".equals(sdbstBrowse.getModelId())) {
            sdbstBrowse.setModelId("");
        }
        if (sdbstBrowse.getPhoneCode() == null || sdbstBrowse.getPhoneCode().length() == 0) {
            sdbstBrowse.setPhoneCode("");
        }
//        sdbstBrowse.setPhoneCode();
//        sdbstBrowse.setModelId("undefined".equals(sdbstBrowse.getModelId())?"":sdbstBrowse.getModelId());
//        System.out.println(sdbstBrowse.toString());
//        System.out.println("============");
//        System.out.println(sdbstBrowse.getPhoneCode());
//        System.out.println(sdbstBrowse.getUserId());
//        System.err.println(sdbstBrowse.getmName()+"--"+sdbstBrowse.getModelId()+"--"+sdbstBrowse.getAppTypes()+"--"+sdbstBrowse.getPhoneCode()+"--"+sdbstBrowse.getOperatorIp()+"--"+sdbstBrowse.getArea()+"--"+sdbstBrowse.getmId()+"--"+sdbstBrowse.getmParentIds()+"--"+sdbstBrowse.getmUrl());
//        System.out.println(sdbstBrowse.getOperatorIp());
//        System.out.println(sdbstBrowse.getCurVersions());
//        System.out.println(sdbstBrowse.getUserId());
//        System.out.println(sdbstBrowse.getAppTypes());
//        System.out.println(sdbstBrowse.getModelId());
        //        User user = systemService.findPhoneCode(sdbstBrowse.getPhoneCode());
//        User user =new User();
//        user.setId("fb7e80028a5147518a8048de0b6e368f");
//        Date createTime = new Date();
//        SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String ctime = smf.format(createTime).substring(0, 7);
//        SdbstOffice sdbstOffice = new SdbstOffice();
//        sdbstOffice = sdbstOfficeService.findNewModelId(sdbstBrowse.getModelId());
//        // 查询当月是否存在访问
//        int one = sdbstBrowseService.getData(ctime, sdbstBrowse.getPhoneCode(),
//                sdbstBrowse.getModelId());
//        Object value;
//        int one = 0;
//        for (String key : map.keySet()) {
//            value = map.get(key);
//            one = Integer.parseInt(String.valueOf(value));
//        }
//        if (null != sdbstOffice) {
//            if (null != sdbstBrowse) {
//                sdbstBrowse.setModelId(sdbstOffice.getModelId());
//                sdbstBrowse.setCreateTime(createTime);
//                sdbstBrowse.setConfirmTime(createTime);
//                if (one >= 1) {
//                    sdbstBrowse.setInsertFlag("0");
//                }else {
//                    sdbstBrowse.setInsertFlag("1");
//                }
//                sdbstBrowse.setIsFalse("1");
//                sdbstBrowse.setFootPrint("1");
//                //转地域
//                sdbstBrowse.setmId(sdbstBrowse.getmId());
////                int op = sdbstBrowse.getOperatorIp().lastIndexOf(".");
////                String ipjq = sdbstBrowse.getOperatorIp().substring(0,(op+1));
////                List<SdbstBrowse> region=sdbstBrowseService.ipTo(ipjq);
////                if(region.size()==0){
////                    sdbstBrowse.setArea("其它");
////                }else{
////                    sdbstBrowse.setArea(""+region.get(0));
////                }
//                sdbstBrowse.setBrand(sdbstBrowse.getBrand());
//                sdbstBrowse.setmName(sdbstBrowse.getmName());
//                sdbstBrowseService.save(sdbstBrowse);
//            }
//        }  else {

        //足迹数据插入
        sdbstBrowse.setCreateTime(new Date());
        sdbstBrowse.setConfirmTime(new Date());
        sdbstBrowse.setmId(sdbstBrowse.getmId());
//            int op = sdbstBrowse.getOperatorIp().lastIndexOf(".");
//            String ipjq = sdbstBrowse.getOperatorIp().substring(0,(op+1));
//            List<SdbstBrowse> region=sdbstBrowseService.ipTo(ipjq);
//            if(region.size()==0){
        sdbstBrowse.setArea("其它");
//            }else{
//                sdbstBrowse.setArea(""+region.get(0));
//            }
        sdbstBrowse.setmName(sdbstBrowse.getmName());
        sdbstBrowse.setInsertFlag("0");
        sdbstBrowse.setIsFalse("1");
        sdbstBrowse.setFootPrint("1");//为1说明是足迹数据
        sdbstBrowse.setIp(HttpUtil.getClientIp(request));
        sdbstBrowseService.save(sdbstBrowse);
//        }

    }
}
