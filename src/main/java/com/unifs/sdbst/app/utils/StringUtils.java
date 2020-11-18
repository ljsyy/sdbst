package com.unifs.sdbst.app.utils;


import rxframework.utility.bean.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    public static String firstLetterUpperCase(String str) {
        if (!hasText(str)) {
            return null;
        }
        str =
                str.substring(0, 1).toUpperCase() + str.substring(1, str.length());
        return str;
    }

    public static boolean hasText(String str) {
        if ((str == null) || ("".equals(str.trim()))) {
            return false;
        }
        return true;
    }

    public static String toStringNull(Object obj) {
        if (ObjectUtils.isNull(obj)) {
            return null;
        }
        return String.valueOf(obj);
    }

    public static String toStringSpace(Object obj) {
        if (ObjectUtils.isNull(obj)) {
            return "";
        }
        return String.valueOf(obj);
    }

    public static boolean isNullOrEmpty(Object str) {
        if ((ObjectUtils.isNull(str)) || (str.toString().trim().length() == 0)) {
            return true;
        }
        return false;
    }

    public static String toCameCase(String str) {
        if (ObjectUtils.isNull(str)) {
            return str;
        }
        str = str.toLowerCase();
        str = str.replaceAll("_$", "");
        int idx = -1;
        while ((idx = str.indexOf("_")) > -1) {
            String tmp = str.substring(idx + 1);
            str = str.substring(0, idx) + firstLetterUpperCase(tmp);
        }
        return str;
    }

    public static String toUnderLine(String str) {
        if (ObjectUtils.isNull(str)) {
            return str;
        }
        String retVal = "";
        for (Integer i = Integer.valueOf(0); i.intValue() < str.length(); i = Integer.valueOf(i.intValue() + 1)) {
            String tmp1 = str.substring(i.intValue(), i.intValue() + 1);
            String tmp2 = tmp1.toLowerCase();
            if (tmp2 != tmp1) {
                retVal = retVal + "_" + tmp2;
            } else {
                retVal = retVal + tmp2;
            }
        }
        return retVal;
    }

    public static String[] splitToArray(String str, String split) {
        if (isNullOrEmpty(str)) {
            return new String[0];
        }
        return str.split(split);
    }

    public static List<String> splitToList(String str, String split) {
        List<String> strList = new ArrayList();
        if (isNullOrEmpty(str)) {
            return strList;
        }
        String[] strArray = splitToArray(str, split);
        String[] arrayOfString1;
        int j = (arrayOfString1 = strArray).length;
        for (int i = 0; i < j; i++) {
            String s = arrayOfString1[i];
            strList.add(s);
        }
        return strList;
    }

    public static String convertListToStr(List<String> list, String split) {
        StringBuffer sb = new StringBuffer();
        for (String s : list) {
            sb.append(s);
            sb.append(split);
        }
        return sb.toString().replaceAll(split + "$", "");
    }

    public static void main(String[] args) {
        String str = toUnderLine("createTime");
        System.out.println(str);
    }

    public static String safeString(Object obj, String defVal) {
        String strVal = defVal;
        if (!isNullOrEmpty(obj)) {
            try {
                strVal = String.valueOf(obj);
            } catch (Exception localException) {
            }
        }
        return strVal;
    }

    public static String safeString(Object obj) {
        return safeString(obj, "");
    }

    public static int safeInt(Object obj, int defVal) {
        int intVal = defVal;
        if (!isNullOrEmpty(obj)) {
            try {
                intVal = Integer.parseInt(String.valueOf(obj));
            } catch (Exception localException) {
            }
        }
        return intVal;
    }

    //获取字符串中指定字符出现第n次的位置
    public static int strIndexByTime(String str, int times, String chr) {
        if("".equals(str)||str==null){
            return -1;
        }
        try{
            Pattern pattern = Pattern.compile(chr);
            Matcher findMatcher = pattern.matcher(str);
            int number = 0;
            while (findMatcher.find()) {
                number++;
                if (number == times) {//当“chr”第二次出现时停止
                    break;
                }
            }
            int i = findMatcher.start();//“chr”第二次出现的位置
            return i;
        }catch (Exception e){
            //e.printStackTrace();
            return -1;
        }
    }

    public static int safeInt(Object obj) {
        return safeInt(obj, 0);
    }
}
