package com.liyongyue.getinfo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import java.util.List;


public class MainActivity extends ActionBarActivity {
    String filePath = Environment.getExternalStorageDirectory() + "/123.apk";
    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button showButton = (Button)findViewById(R.id.showButton);
        Button installButton = (Button)findViewById(R.id.InstallButton);
        Button uninstallButton = (Button)findViewById(R.id.UninstallButton);
        Button setParamButton = (Button)findViewById(R.id.SetParamButton);
        final TextView showTextView = (TextView)findViewById(R.id.showTextView);

        final android.os.Handler handler = new android.os.Handler(){
            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                String result = (String)msg.obj;
                Log.e("input",result);
            }
        };
        final Thread getThread = new Thread(){
            @Override
            public void run() {
                super.run();
                Log.e("input","start");
                String ip = HttpUtil.getIP();
                Message msg = new Message();
                msg.obj = ip;
                handler.handleMessage(msg);
                Log.e("input","end");
            }
        };

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneInfo = getInfo();
                showTextView.setText(phoneInfo);
            }
        });

        installButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SilentInstall.install(filePath);
                openApp(getAPKName(filePath));
            }
        });
        uninstallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SilentInstall.uninstall(getAPKName(filePath));
            }
        });

        Button getIPButton = (Button)findViewById(R.id.getIPButton);
        getIPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(getThread).start();
            }
        });


        Button clearButton = (Button)findViewById(R.id.ClearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SilentInstall.clear(getAPKName(filePath));
            }
        });

        setParamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Param.class);
                startActivity(intent);
                //如果不关闭当前的会出现好多个页面
                MainActivity.this.finish();

            }
        });


        MobclickAgent.updateOnlineConfig( this );

    }

    public Location getLocation(Context context) {
        LocationManager locMan = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        Location location = locMan
                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null) {
            location = locMan
                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        return location;
    }

    private String getAPKName(String filePath){
        PackageManager pm = this.getPackageManager();
        String name = "";
        try {
            PackageInfo pi = pm.getPackageArchiveInfo(filePath, PackageManager.GET_ACTIVITIES);
            ApplicationInfo applicationInfo = pi.applicationInfo;
            name = applicationInfo.packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("input", name);
        return name;
    }

    private String getInfo(){

        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = tm.getSubscriberId();
        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();

        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location location = getLocation(this);
        double strLati = 0;
        double strLong = 0;
        if(location != null){
            strLati = location.getLatitude();
            strLong = location.getLongitude();
        }

        String imei = tm.getDeviceId();
        String mac = info.getMacAddress();
        String ANDROID_ID = Settings.Secure.getString(getContentResolver(), Settings.System.ANDROID_ID);
        String manufacturer = android.os.Build.MANUFACTURER;
        String gps = strLati + "/" + strLong;
        String model = android.os.Build.MODEL;
        String brand = android.os.Build.BRAND;
        String sdk = android.os.Build.VERSION.SDK;


        String expectedIMEI = ConfigUtil.get("IMEI");
        String expectedMAC = ConfigUtil.get("MAC");



        String phoneInfo = "IMEI: " + imei + ":" + expectedIMEI + "\n";
        phoneInfo += "MAC: " + mac + ":" + expectedMAC + "\n";
        phoneInfo += "Android_id: " + ANDROID_ID + "\n";
        phoneInfo += "MANUFACTURER: " + manufacturer + "\n";
        phoneInfo += "GPS: " + gps + "\n";
        phoneInfo += "MODEL: " + model + "\n";
        phoneInfo += "BRAND: " + brand + "\n";
        phoneInfo += "SDK: " + sdk + "\n";
        phoneInfo += "IMSI: " + imsi + "\n";
        phoneInfo += "VERSION.RELEASE: " + android.os.Build.VERSION.RELEASE + "\n";


//        String phoneInfo = "Product: " + android.os.Build.PRODUCT + "\n";
//        phoneInfo += "CPU_ABI: " + android.os.Build.CPU_ABI + "\n";
//        phoneInfo += "TAGS: " + android.os.Build.TAGS + "\n";
//        phoneInfo += "VERSION_CODES.BASE: " + android.os.Build.VERSION_CODES.BASE + "\n";
//        phoneInfo += "MODEL: " + android.os.Build.MODEL + "\n";
//        phoneInfo += "SDK: " + android.os.Build.VERSION.SDK + "\n";
//        phoneInfo += "VERSION.RELEASE: " + android.os.Build.VERSION.RELEASE + "\n";
//        phoneInfo += "DEVICE: " + android.os.Build.DEVICE + "\n";
//        phoneInfo += "DISPLAY: " + android.os.Build.DISPLAY + "\n";
//        phoneInfo += "BRAND: " + android.os.Build.BRAND + "\n";
//        phoneInfo += "BOARD: " + android.os.Build.BOARD + "\n";
//        phoneInfo += "FINGERPRINT: " + android.os.Build.FINGERPRINT + "\n";
//        phoneInfo += "ID: " + android.os.Build.ID + "\n";
//
//        phoneInfo += "USER: " + android.os.Build.USER + "\n";

        return phoneInfo;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    private void openApp(String packageName) {
        PackageManager pm = this.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(pi.packageName);

        List<ResolveInfo> apps = pm.queryIntentActivities(resolveIntent, 0);

        ResolveInfo ri = apps.iterator().next();
        if (ri != null ) {
            String packName = ri.activityInfo.packageName;
            String className = ri.activityInfo.name;

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            ComponentName cn = new ComponentName(packName, className);

            intent.setComponent(cn);
            startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


}
