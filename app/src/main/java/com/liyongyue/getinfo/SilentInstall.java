package com.liyongyue.getinfo;

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
    public static void install(){
        Log.e("input","install");
        String fileName = Environment.getExternalStorageDirectory()+"/" + "123.apk";
        File file = new File(fileName);
        if(file.exists()){
            Log.e("input",fileName +"yes");
            slientInstall(file);
        }else{
            Log.e("input",fileName +"no");
        }
    }


    public static boolean slientInstall(File file) {
        boolean result = false;
        Process process = null;
        OutputStream out = null;
        try {
            process = Runtime.getRuntime().exec("su");
            out = process.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(out);
            dataOutputStream.writeBytes("chmod 777 " + file.getPath() + "\n");
            dataOutputStream.writeBytes("LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install -r " +
                    file.getPath());
            // �ύ����
            dataOutputStream.flush();
            // �ر�������
            dataOutputStream.close();
            out.close();
            int value = process.waitFor();

            // ����ɹ�
            if (value == 0) {
                result = true;
            } else if (value == 1) { // ʧ��
                result = false;
            } else { // δ֪���
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
