package com.unifs.sdbst.app.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentUtil {

    /**
     * 将联系电话和其它联系方式的字符串转为map输出
     * @param str
     * @return
     */
    public static List<Map<String,String>> stringToListMap(String str){
        List<Map<String,String>> strList = new ArrayList<>();
        if(str!=null){
            String[] strArray = str.trim().split("；");
            for(String strArrayItem :strArray){
                String[] detailStr = strArrayItem.split("：");
                Map<String,String> strMap = new HashMap<>();
                strMap.put("name",detailStr[0]);
                strMap.put("value",detailStr[1]);
                strList.add(strMap);
            }
        }
        return strList;
    }
}
