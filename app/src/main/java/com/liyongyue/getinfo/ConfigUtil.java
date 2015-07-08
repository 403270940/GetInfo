package com.liyongyue.getinfo;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Administrator on 2015/7/6.
 */
public class ConfigUtil {
    private static Properties properties = new Properties();
    public static void init(){
        String fileName = "/assets/config.properties";
        try {
            properties.load(ConfigUtil.class.getResourceAsStream(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String get(String key){

        String result = (String)properties.get(key);
        return result;
    }
}
