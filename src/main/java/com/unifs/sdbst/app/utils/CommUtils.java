package com.unifs.sdbst.app.utils;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.util.JSONPObject;
import rxframework.utility.bean.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CommUtils
{
    public static String safeString(Object obj, String defVal)
    {
        String strVal = defVal;
        if (!rxframework.utility.string.StringUtils.isNullOrEmpty(obj)) {
            try
            {
                strVal = String.valueOf(obj);

                strVal = FilterScriptUtil.stripSql(strVal);
            }
            catch (Exception localException) {}
        }
        return strVal;
    }

    public static String safeString(Object obj)
    {
        return safeString(obj, "");
    }

    public static int safeInt(Object obj, int defVal)
    {
        int intVal = defVal;
        if (!rxframework.utility.string.StringUtils.isNullOrEmpty(obj)) {
            try
            {
                intVal = Integer.parseInt(String.valueOf(obj));
            }
            catch (Exception localException) {}
        }
        return intVal;
    }

    public static int safeInt(Object obj)
    {
        return safeInt(obj, 0);
    }

    public static double safeDouble(Object obj, double defVal)
    {
        double intVal = defVal;
        if (!rxframework.utility.string.StringUtils.isNullOrEmpty(obj)) {
            try
            {
                intVal = Double.parseDouble(String.valueOf(obj));
            }
            catch (Exception localException) {}
        }
        return intVal;
    }

    public static double safeDouble(Object obj)
    {
        return safeDouble(obj, 0.0D);
    }

    public static Object getJsonp(HttpServletRequest request, Object obj)
    {
        String jsonp = request.getParameter("jsonpcallback");
        if ((jsonp == null) || (jsonp.length() <= 0)) {
            return obj;
        }
        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        mapper.configure(DeserializationConfig.Feature.FAIL_ON_NUMBERS_FOR_ENUMS, true);

        JSONPObject jsonpObj = new JSONPObject(jsonp, obj);
        String reVal = "";
        try
        {
            reVal = mapper.writeValueAsString(jsonpObj);
        }
        catch (JsonGenerationException e)
        {
            e.printStackTrace();
        }
        catch (JsonMappingException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return reVal;
    }

    public static String toUnderLine(String str)
    {
        if (ObjectUtils.isNull(str)) {
            return str;
        }
        String retVal = "";
        for (Integer i = Integer.valueOf(0); i.intValue() < str.length(); i = Integer.valueOf(i.intValue() + 1))
        {
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

    public static Timestamp parseTimestamp(String src, String pattern)
            throws ParseException
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = simpleDateFormat.parse(src);
        return new Timestamp(date.getTime());
    }

    public static HashMap<String, Object> getUrlParams(String param)
    {
        HashMap<String, Object> map = new HashMap(0);
        if (rxframework.utility.string.StringUtils.isNullOrEmpty(param)) {
            return map;
        }
        String[] params = param.split("&");
        for (int i = 0; i < params.length; i++)
        {
            String[] p = params[i].split("=");
            if (p.length >= 2) {
                map.put(p[0], p[1]);
            } else {
                map.put(p[0], "");
            }
        }
        return map;
    }

    public static String getUrlParamsByMap(Map<String, Object> map)
    {
        if (map == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Object> entry : map.entrySet())
        {
            sb.append((String)entry.getKey() + "=" + entry.getValue());
            sb.append("&");
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = org.apache.commons.lang.StringUtils.substringBeforeLast(s, "&");
        }
        return s;
    }

    /*public static String encode_params(HashMap<String, Object> hashMap)
    {
        hashMap.put("timestamp", create_timestamp());

        String params = getUrlParamsByMap(hashMap);

        String encrypt = "";
        try
        {
            encrypt = DESUtil.encrypt(params);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("url��������" + encrypt);
        return encrypt;
    }*/

    /*public static HashMap<String, Object> decode_params(String params)
    {
        String decrypt = "";
        try
        {
            params = params.replace(" ", "+");
            decrypt = DESUtil.decrypt(params);
            decrypt = UrlUtil.getURLDecoderString(decrypt);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("url��������" + decrypt);

        HashMap<String, Object> hashMap = getUrlParams(decrypt);
        return hashMap;
    }*/

    public static String create_timestamp()
    {
        return Long.toString(System.currentTimeMillis() / 1000L);
    }
}
