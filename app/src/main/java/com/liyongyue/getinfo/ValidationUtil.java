package com.liyongyue.getinfo;

/**
 * Created by yli on 2015/7/9.
 */
public class ValidationUtil {

    public static boolean isMAC(String mac){
        int len = mac.length();
        if(len != 17)return false;
        String[] macs = mac.split(":");
        if(macs.length != 6) return false;

        for(int i = 0; i < 6; i ++){
            if(!GlobalValue.hex.contains(macs[i].charAt(0)+""))return false;
            if(!GlobalValue.hex.contains(macs[i].charAt(1)+""))return false;
        }

        return true;
    }

}
