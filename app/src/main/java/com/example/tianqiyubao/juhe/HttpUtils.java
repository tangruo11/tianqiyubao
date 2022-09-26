package com.example.tianqiyubao.juhe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtils {

    public static String getIndexJson(String path) throws MalformedURLException {
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream=null;
        String a=null;
        StringBuilder builder=new StringBuilder();
        URL url=new URL(path);
        try {
           httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(5000);
            if (httpURLConnection.getResponseCode()== HttpURLConnection.HTTP_OK) {
                 inputStream= httpURLConnection.getInputStream();
                BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));

                if ((a=reader.readLine())!=null) {
                    builder.append(a);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (httpURLConnection!=null) {
                httpURLConnection.disconnect();
            }
            if (inputStream!=null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            
        }
        return builder.toString();

    }

}
