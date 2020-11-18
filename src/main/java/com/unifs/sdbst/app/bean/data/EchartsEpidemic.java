package com.unifs.sdbst.app.bean.data;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @version V1.0
 * @title: EchartsEpidemic
 * @projectName sdbst
 * @description: 疫情echarts数据封装
 * @author： 张恭雨
 * @date 2020/2/4 16:52
 */
@Data
@ToString
public class EchartsEpidemic {
    private List<Integer> amounts;
    private List<String> dates;
    private String newCount;
}
