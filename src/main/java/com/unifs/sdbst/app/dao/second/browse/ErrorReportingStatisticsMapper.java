/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.unifs.sdbst.app.dao.second.browse;


import com.unifs.sdbst.app.bean.browse.ErrorReportingStatistics;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 错误信息DAO接口
 *
 * @author yinxubo
 * @version 2017-12-25
 */
@Component
public interface ErrorReportingStatisticsMapper {
    // 总数
    List<ErrorReportingStatistics> ErrorRe(@Param("startDate") String startDate, @Param("endDate") String endDate);

    int insert(ErrorReportingStatistics errorReportingStatistics);
}