package com.unifs.sdbst.app.utils;
import com.google.gson.Gson;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSA {
    /**
     * 还原公钥，X509EncodedKeySpec 用于构建公钥的规范
     *
     * @param keyBytes
     * @return
     */
    public static PublicKey restorePublicKey(byte[] keyBytes) {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        try {
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = factory.generatePublic(x509EncodedKeySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密，三步走。
     *
     * @param key
     * @param plainText
     * @return
     */
    public static byte[] RSAEncode(PublicKey key, byte[] plainText) {

        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(plainText);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }

    public static void main(String[] args){

        String key  ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDBgb9kjNuM+FUDpkz5wUTommFodB10HoMPNb4V8XII0JQR/PtunmddL34Rc7DI2FO1Q0Zr/rX7LstwJhHrl6gYlv6qKl7ftwXCKyEJwWeoUGjRn7t697DZfnqfZIodQwmwYnTY72H3YyKq5F2QqRbcrwoEwE97gGhwMpLc7XawwwIDAQAB";

        Map<String,String> map = new HashMap<>();
        map.put("name","李家升");
        map.put("id","445381199407156317");
        map.put("phonenuber","18707676996");

        Gson gson = new Gson();
        String s = gson.toJson(map);

        System.out.println("auth json "+s);
        PublicKey publicKey = restorePublicKey(java.util.Base64.getDecoder().decode(key));
        byte[] encodedText = RSAEncode(publicKey, s.getBytes());
        String auth = java.util.Base64.getEncoder().encodeToString(encodedText);
        System.out.println("auth加密："+auth);


        String jylx = "03021";
        String tzsbh = "2001850757807";
        String zxdwbh = "107613";
        String xmbh = "440606";
        String sqlybm = "400023";
        String ddbh = "20200811172346ceshi1";
        String time = DateUtils.getDate("yyyyMMddHHmmss");
        System.out.println("time ："+time);
       String md5key = "66jhtyd0t3kyt6p";

       String encryStr = jylx+tzsbh+zxdwbh+xmbh+sqlybm+ddbh+time+auth+md5key;
        System.out.println("sign加密前 "+encryStr);
        String s1 = MD5.md5(encryStr).toUpperCase();

       System.out.println("md5 sign："+s1);

    }
}