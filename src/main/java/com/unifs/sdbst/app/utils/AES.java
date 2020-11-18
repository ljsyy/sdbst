package com.unifs.sdbst.app.utils;


import com.unifs.sdbst.app.exception.MyException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @创建人 张恭雨
 * @创建时间 2018/12/19
 * @描述：
 */
public class AES {

    public static String encrypt(String input, String key) {
        byte[] crypted = null;
        try {

            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skey);
            crypted = cipher.doFinal(input.getBytes());
        } catch (Exception e) {
            throw new MyException(e.getMessage());
        }

        //return new String(Base64.encodeBase64(crypted));
        return new String(Hex.encodeHex(crypted));
    }

    /**
     * decrypt input text
     *
     * @param input
     * @param key
     * @return
     */
    public static String decrypt(String input, String key) {
        byte[] output = null;
        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skey);
//            output = cipher.doFinal(Base64.decodeBase64(input));
            output = cipher.doFinal(Hex.decodeHex(input.toCharArray()));
        } catch (Exception e) {
            System.out.println(e.toString());
            throw new MyException(e.getMessage());
        }
        return new String(output);
    }
}