package com.liyongyue.getinfo;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Param extends ActionBarActivity {

    String IMEI = "";
    String MAC = "";
    String IMSI = "";
    String CS = "";
    String XH = "";
    String ID = "";
    String GPS = "";
    private Button IMEIButton = null;
    private Button MACButton = null;
    private Button IMSIButton = null;
    private Button CSButton = null;
    private Button XHButton = null;
    private Button IDButton = null;
    private Button GPSButton = null;
    private Button OKButton = null;
    private Button CancelButton = null;


    private EditText IMEIEditText = null;
    private EditText MACEditText = null;
    private EditText IMSIEditText = null;
    private EditText CSEditText = null;
    private EditText XHEditText = null;
    private EditText IDEditText = null;
    private EditText GPSEditText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_param);
        IMEIButton = (Button)findViewById(R.id.IMEIButton);
        MACButton = (Button)findViewById(R.id.MACButton);
        IMSIButton = (Button)findViewById(R.id.IMSIButton);
        CSButton = (Button)findViewById(R.id.CSButton);
        XHButton = (Button)findViewById(R.id.XHButton);
        IDButton = (Button)findViewById(R.id.IDButton);
        GPSButton = (Button)findViewById(R.id.GPSButton);

        OKButton = (Button)findViewById(R.id.OKButton);
        CancelButton = (Button)findViewById(R.id.CancelButton);


        IMEIEditText = (EditText)findViewById(R.id.IMEIEditText);
        MACEditText = (EditText)findViewById(R.id.MACEditText);
        IMSIEditText = (EditText)findViewById(R.id.IMSIEditText);
        CSEditText = (EditText)findViewById(R.id.CSEditText);
        XHEditText = (EditText)findViewById(R.id.XHEditText);
        IDEditText = (EditText)findViewById(R.id.IDEditText);
        GPSEditText = (EditText)findViewById(R.id.GPSEditText);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.IMEIButton:
                        IMEI = ConfigUtil.getRandomIMEI();
                        IMEIEditText.setText(IMEI);
                        break;

                    case R.id.MACButton:
                        MAC = ConfigUtil.getRandomMAC();
                        MACEditText.setText(MAC);
                        break;

                    case R.id.IMSIButton:
                        IMSI = ConfigUtil.getRandomIMSI();
                        IMSIEditText.setText(IMSI);
                        break;

                    case R.id.CSButton:
                        CS = ConfigUtil.getRandomCH();
                        CSEditText.setText(CS);
                        break;

                    case R.id.XHButton:
                        XH = ConfigUtil.getRandomXH();
                        XHEditText.setText(XH);
                        break;

                    case R.id.IDButton:
                        ID = ConfigUtil.getRandomID();
                        XHEditText.setText(ID);
                        break;

                    case R.id.GPSButton:
                        GPS = ConfigUtil.getRandomGPS();
                        GPSEditText.setText(GPS);
                        break;

                    case R.id.OKButton:
                        saveAllParam();
                        break;

                    case R.id.CancelButton:
                        initParam();
                        break;
                }
            }
        };

        initParam();


        IMEIButton.setOnClickListener(onClickListener);
        MACButton.setOnClickListener(onClickListener);
        IMSIButton.setOnClickListener(onClickListener);
        CSButton.setOnClickListener(onClickListener);
        XHButton.setOnClickListener(onClickListener);
        GPSButton.setOnClickListener(onClickListener);
        IDButton.setOnClickListener(onClickListener);
        OKButton.setOnClickListener(onClickListener);
        CancelButton.setOnClickListener(onClickListener);




    }

    private void initParam(){
        IMEIEditText.setText(ConfigUtil.get("IMEI"));
        MACEditText.setText(ConfigUtil.get("MAC"));
        IMSIEditText.setText(ConfigUtil.get("IMSI"));
        CSEditText.setText(ConfigUtil.get("MANU"));
        XHEditText.setText(ConfigUtil.get("MODEL"));
        IDEditText.setText(ConfigUtil.get("ID"));
        GPSEditText.setText(ConfigUtil.get("GPS"));
    }
    private boolean saveAllParam(){
        IMEI = IMEIEditText.getText().toString();
        MAC = MACEditText.getText().toString();
        IMSI = IMSIEditText.getText().toString();
        CS = CSEditText.getText().toString();
        XH = XHEditText.getText().toString();
        GPS = GPSEditText.getText().toString();
        ID = IDEditText.getText().toString();
        if(!ValidationUtil.check("IMEI",IMEI)){
            Toast.makeText(this, "IMEI格式错误", Toast.LENGTH_SHORT);
            return false;
        }
        if(!ValidationUtil.check("MAC",MAC)){
            Toast.makeText(this,"MAC格式错误",Toast.LENGTH_SHORT);
            return false;
        }
        if(!ValidationUtil.check("IMSI",IMSI)){
            Toast.makeText(this,"IMSI格式错误",Toast.LENGTH_SHORT);
            return false;
        }
        if(!ValidationUtil.check("MANU",CS)){
            Toast.makeText(this,"厂商格式错误",Toast.LENGTH_SHORT);
            return false;
        }
        if(!ValidationUtil.check("MODEL",XH)){
            Toast.makeText(this,"型号格式错误",Toast.LENGTH_SHORT);
            return false;
        }
        if(!ValidationUtil.check("ID",ID)){
            Toast.makeText(this,"ID格式错误",Toast.LENGTH_SHORT);
            return false;
        }
        if(!ValidationUtil.check("GPS",GPS)){
            Toast.makeText(this,"GPS格式错误",Toast.LENGTH_SHORT);
            return false;
        }

        boolean result = ConfigUtil.addParams(IMEI,MAC,IMSI,CS,XH,ID,GPS);
        return result;
    }



    private boolean resetAllParam(){
        IMEI = ConfigUtil.get("IMEI");
        return true;
    }
}
