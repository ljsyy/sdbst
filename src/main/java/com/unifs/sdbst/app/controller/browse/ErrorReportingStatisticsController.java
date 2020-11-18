/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.unifs.sdbst.app.controller.browse;


import com.unifs.sdbst.app.annotation.ControlLog;
import com.unifs.sdbst.app.base.Resp;
import com.unifs.sdbst.app.bean.browse.ErrorReportingStatistics;
import com.unifs.sdbst.app.enums.RespCode;
import com.unifs.sdbst.app.service.browse.ErrorReportingStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 错误信息Controller
 *
 * @author yinxubo
 * @version 2017-12-25
 */
@Controller
@RequestMapping(value = "/browse/errorReportingStatistics")
public class ErrorReportingStatisticsController {

    @Autowired
    private ErrorReportingStatisticsService errorReportingStatisticsService;


    @ControlLog(operateType = "/save", context = "ios闪退错误记录")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Resp save(ErrorReportingStatistics errorReportingStatistics) {
        errorReportingStatisticsService.save(errorReportingStatistics);
        return new Resp(RespCode.SUCCESS);

    }


}