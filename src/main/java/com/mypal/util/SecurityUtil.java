package com.mypal.util;

import org.springframework.stereotype.Service;

import java.security.MessageDigest;

@Service("secUtil")
public class SecurityUtil {

    public static String passwordEncoder(String plainPassword) {
        MessageDigest md5 = null;
        StringBuffer  hexString = new StringBuffer();

        try {
            md5 = MessageDigest.getInstance("md5");
        } catch (Exception e) { }

        md5.reset();
        md5.update(plainPassword.getBytes());

        byte messageDigest[] = md5.digest();

        for (int i = 0; i < messageDigest.length; i++) {
            hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
        }

        return hexString.toString();
    }
}
/*

String md5 = null;

if(null == input) return null;

try {

        MessageDigest digest = MessageDigest.getInstance("MD5");

digest.update(input.getBytes(), 0, input.length());

md5 = new BigInteger(1, digest.digest()).toString(16);

} catch (NoSuchAlgorithmException e) {

        e.printStackTrace();
}
        return md5;*/
