package com.liyongyue.getinfo;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button showButton = (Button)findViewById(R.id.showButton);
        final TextView showTextVeiw = (TextView)findViewById(R.id.showTextView);
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneInfo = getInfo();
                showTextVeiw.setText(phoneInfo);
            }
        });
        MobclickAgent.updateOnlineConfig( this );

    }
    private String getInfo(){

        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        String imsi = tm.getSubscriberId();
        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();

        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        String strLati = "";
        String strLong = "";
        if(location != null){
            strLati = Double.toString(location.getLatitude());
            strLong = Double.toString(location.getLongitude());
        }

        String imei = tm.getDeviceId();
        String mac = info.getMacAddress();
        String ANDROID_ID = Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID);
        String manufacturer = android.os.Build.MANUFACTURER;
        String gps = strLati + "/" + strLong;
        String model = android.os.Build.MODEL;
        String brand = android.os.Build.BRAND;
        String sdk = android.os.Build.VERSION.SDK;

        String phoneInfo = "IMEI: " + imei + "\n";
        phoneInfo += "MAC: " + mac + "\n";
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
