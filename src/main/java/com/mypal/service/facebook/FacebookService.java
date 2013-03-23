package com.mypal.service.facebook;

import org.json.JSONObject;

import java.io.*;
import java.net.URL;

public class FacebookService {

    public static JSONObject getFacebookUserData(String accessToken) {
        try {

            JSONObject jsonObject = new JSONObject(readUrlFromString("https://graph.facebook.com/me?access_token=" + accessToken));
            return jsonObject;

        } catch (Throwable ex) {
            throw new RuntimeException("failed login", ex);
        }
    }

    private static String readUrlFromString(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    public static String readUrl(URL url) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        InputStream is = url.openStream();
        int r;
        while ((r = is.read()) != -1) {
            byteArrayOutputStream.write(r);
        }
        return new String(byteArrayOutputStream.toByteArray());
    }
}
