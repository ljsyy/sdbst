package com.unifs.sdbst.app.utils;

import sun.misc.BASE64Encoder;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class CreateEMSParamUtils {

    private static final String DEFAULTCHARSET = "UTF-8";

    public static String getSortParams(Map<String, String> params) {
        params.remove("sign");
        String contnt = "";
        Set<String> keySet = params.keySet();
        List<String> keyList = new ArrayList<String>();
        //这个接口做sign签名时，值为空的参数也传
        for (String key : keySet) {
            keyList.add(key);
        }
        Collections.sort(keyList, new Comparator<String>() {

            @Override
            public int compare(String o1, String o2) {
                int length = Math.min(o1.length(), o2.length());
                for (int i = 0; i < length; i++) {
                    char c1 = o1.charAt(i);
                    char c2 = o2.charAt(i);
                    int r = c1 - c2;
                    if (r != 0) {
                        // char值小的排前边
                        return r;
                    }
                }
                // 2个字符串关系是str1.startsWith(str2)==true
                // 取str2排前边
                return o1.length() - o2.length();
            }
        });
        //将参数和参数值按照排序顺序拼装成字符串
        for (int i = 0; i < keyList.size(); i++) {
            String key = keyList.get(i);
            contnt += key + params.get(key);
        }
        return contnt;
    }

    public static String sign(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            charset = "UTF-8";
        }
        String sign = "";
        try {
            content = new String(content.getBytes(), charset);
            MessageDigest md5 = MessageDigest.getInstance("SHA-256");//MD5
            BASE64Encoder base64Encoder = new BASE64Encoder();
            sign = base64Encoder.encode(md5.digest(content.getBytes(charset)));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sign;
    }

    public static String toKey(Map<String, String> params){
        String content = "";
        Set<String> keySet = params.keySet();
        List<String> keyList = new ArrayList<String>();
        //这个接口做sign签名时，值为空的参数也传
        for (String key : keySet) {
            String value = params.get(key);
            // 将值为空的参数排除
			/*if (!StringUtil.isNull(value)) {
				keyList.add(key);
			}*/
            keyList.add(key);
        }
        //将参数和参数值按照排序顺序拼装成字符串
        for (int i = 0; i < keyList.size(); i++) {
            String key = keyList.get(i);
            if(i == keyList.size()-1){
                content +=key + "=" + params.get(key);
                return content;
            }
            content += key + "=" + params.get(key) + "&";

        }
        return content;
    }

}
