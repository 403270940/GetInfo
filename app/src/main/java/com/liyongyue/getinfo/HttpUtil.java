package com.liyongyue.getinfo;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by yli on 2015/7/9.
 */
public class HttpUtil {
    private static HttpClient httpClient = new DefaultHttpClient();
    public static String getIP(){
        HttpGet get = new HttpGet("http://www.liyongyue.com/getip.php");
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,10000);
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,10000);
        Log.e("input", "get");
        try {
            HttpResponse response = httpClient.execute(get);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);
            Log.e("input","get end");
            return result.trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
