package com.mypal.service;

import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class DecodeService {

    public String decodePassword(String password) throws NoSuchAlgorithmException {

        MessageDigest mdigest = null;
        mdigest = MessageDigest.getInstance("MD5");
        mdigest.reset();
        mdigest.update(password.getBytes());

        byte[] digest = mdigest.digest();
        BigInteger big = new BigInteger(1,digest);

        String hash = big.toString(16);

        return hash;
    }
}
