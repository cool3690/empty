package com.example.empty.mydb;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.content.Intent;
import android.os.Bundle;

public class carin {

    public static String executeQuery(String car,String date,String time,String address,
                                      String latitude,String longitude)
    {
        String result ="";


        try {
            HttpClient httpClient = new DefaultHttpClient();
            //HttpPost httpPost = new HttpPost("203.69.42.186/carin.php");
            //HttpPost httpPost = new HttpPost("http://192.168.1.247/A/carin.php");
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            HttpPost httpPost = new HttpPost("http://www.chansing.com.tw/carin.php");
            params.add(new BasicNameValuePair("car", car));

            params.add(new BasicNameValuePair("date", date));
            params.add(new BasicNameValuePair("time", time));
            params.add(new BasicNameValuePair("address", address));
            params.add(new BasicNameValuePair("latitude", latitude));
            params.add(new BasicNameValuePair("longitude", longitude));
            /**/
            httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            //view_account.setText(httpResponse.getStatusLine().toString());
            HttpEntity httpEntity = httpResponse.getEntity();
            InputStream inputStream = httpEntity.getContent();

            BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
            StringBuilder builder = new StringBuilder();
            String line = null;
            while((line = bufReader.readLine()) != null) {
                builder.append(line + "\n");
            }
            inputStream.close();
            result = builder.toString();
        }
        catch(Exception e) {}

        return result;
    }

}

