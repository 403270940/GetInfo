package com.liyongyue.getinfo;

import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Administrator on 2015/7/6.
 */
public class SilentInstall {



    public static boolean clear(String packageName){
        boolean result = false;
        String cmd = "LD_LIBRARY_PATH=/vendor/lib:/system/lib pm clear " + packageName;
        result = command(cmd);
        return result;
    }

    public static boolean stop(String packageName){
        boolean result = false;
        String cmd = "LD_LIBRARY_PATH=/vendor/lib:/system/lib pm force-stop " + packageName;
        result = command(cmd);
        return result;
    }


    public static boolean kill(String packageName){
        boolean result = false;
        String cmd = "LD_LIBRARY_PATH=/vendor/lib:/system/lib pm kill " + packageName;
        result = command(cmd);
        return result;
    }

    public static boolean uninstall(String packageName){
        boolean result = false;
        String cmd = "LD_LIBRARY_PATH=/vendor/lib:/system/lib pm uninstall -k " + packageName;
        result = command(cmd);
        return result;
    }

    public static boolean install(String filePath) {
        boolean result = false;
        String cmd = "LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install -r " + filePath;
        result = command(cmd);
        return result;
    }



    public static boolean command(String cmd){
        boolean result = false;
        Process process = null;
        OutputStream out = null;
        try {
            process = Runtime.getRuntime().exec("su");
            out = process.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(out);
            dataOutputStream.writeBytes(cmd);
            // 提交命令
            dataOutputStream.flush();
            // 关闭流操作
            dataOutputStream.close();
            out.close();
            int value = process.waitFor();

            // 代表成功
            if (value == 0) {
                result = true;
            } else if (value == 1) { // 失败
                result = false;
            } else { // 未知情况
                result = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

}
