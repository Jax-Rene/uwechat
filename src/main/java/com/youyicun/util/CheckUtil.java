package com.youyicun.util;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by johnny on 16/4/4.
 */
public class CheckUtil {
    private static final String token = "zhuangjingyang";
    public static boolean check(String signature,String timestamp,String nonce){
        String[] arr = new String[]{token,timestamp,nonce};
        Arrays.sort(arr);
        StringBuffer sb = new StringBuffer();
        for(String a:arr){
            sb.append(a);
        }
        if(SHA1(sb.toString()).equals(signature))
            return true;
        return false;
    }

    public static String SHA1(String decript) {
        try {
            MessageDigest digest = MessageDigest
                    .getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
