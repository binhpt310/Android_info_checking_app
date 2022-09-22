package com.example.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.telephony.SubscriptionManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


public class MainActivity extends AppCompatActivity {
    private String PERMISSIONS[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PERMISSIONS = new String[] {

                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_PHONE_STATE
        };

        Button Camera = (Button) findViewById(R.id.check_camera_btn);
        Button Sim = (Button) findViewById(R.id.check_sim_btn);
        Button Wifi = (Button) findViewById(R.id.check_wifi_btn);

        TextView Connectiontype = (TextView) findViewById(R.id.connection_type_txt);
        TextView WifiSSID = (TextView) findViewById(R.id.wifi_ssid_txt);
        TextView Connectiontype_des = (TextView) findViewById(R.id.textView7);
        TextView IPaddress = (TextView) findViewById(R.id.ip_address_txt);
        TextView MACAddress = (TextView) findViewById(R.id.mac_address_txt);
        TextView Number = findViewById(R.id.sim_number_txt);
        TextView SimSlot1 = findViewById(R.id.sim_slot_number1_txt);
        TextView SimSlot2 = findViewById(R.id.sim_slot_number2_txt);
        TextView CameraNumber = findViewById(R.id.camera_numbers_txt);
        TextView CameraError = findViewById(R.id.camera_errors_checking_txt);

        TextView PID = findViewById(R.id.product_id_txt);
        TextView VID = findViewById(R.id.vendor_id_txt);
        TextView PNum = findViewById(R.id.product_name_txt);
        TextView DID = findViewById(R.id.device_id_txt);
        TextView PID_hex = findViewById(R.id.product_id_hexa_txt);

        Context context = getBaseContext();
        Intent intent = new Intent(this, MainActivity.class);
        SubscriptionManager subscriptionManager = (SubscriptionManager)
                getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);

        if (!hasPermissions(MainActivity.this,PERMISSIONS)) {

            ActivityCompat.requestPermissions(MainActivity.this,PERMISSIONS,1);
        }



        Camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                int cam_number;
                try {
                    if (cameraManager != null) {
                        cam_number = cameraManager.getCameraIdList().length;
                        CameraNumber.setText(Integer.toString(cam_number));
                    }
                }
                catch (CameraAccessException e) {
                    CameraError.setText("Unable to enumerate cameras");
                }

                Connectivity.getPIDVID(context,PNum,DID,PID_hex);


            }
        });

        Sim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int Sim_number = subscriptionManager.getActiveSubscriptionInfoCountMax();
                Number.setText(Integer.toString(Sim_number));

                String mainSimState = Connectivity.getSimState(context);
                SimSlot1.setText(mainSimState);
                String secondSimState = Connectivity.getSimState(context);
                SimSlot2.setText(secondSimState);
            }
        });

        Wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check connection of WIFI and Mobile data
                if (Connectivity.isConnected(context)==true) {
                    if (Connectivity.isConnectedWifi(context) == true) {
                        String mac = Connectivity.getMACAddress(context);
                        String ip = Connectivity.getDeviceIPAddress(context);
                        IPaddress.setText(ip);
                        Connectiontype.setText("Wifi connection");
                        WifiSSID.setText(Connectivity.getWifiSSID(context));
                        MACAddress.setText(mac);
                    }

                    //Mobile data connection
                    else if (Connectivity.isConnectedWifi(context) == false
                            && Connectivity.isConnectedMobile(context) == true){
                        String mobile_data = Connectivity.getMobileNetworkType(context);
                        Connectiontype.setText(mobile_data);
                        Connectiontype_des.setText("Mobile data info:");
                        String networkinfo = Connectivity.getNetworkInfo(context).getExtraInfo();
                        WifiSSID.setText(networkinfo);
                        String ip = Connectivity.getDeviceIPAddress(context);
                        IPaddress.setText(ip);
                    }
                }
                else
                    Connectiontype.setText("No connection");
            }
        });
    }

    private boolean hasPermissions(Context context, String... PERMISSIONS) {
        if (context != null && PERMISSIONS != null) {
            for (String permission: PERMISSIONS) {
                if (ActivityCompat.checkSelfPermission(context,permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true; //return true if the device has permissions granted
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "Access fine location is granted", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Access fine location is denied", Toast.LENGTH_SHORT).show();


            if (grantResults[1] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "Access coarse location is granted", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Access coarse location is denied", Toast.LENGTH_SHORT).show();


            if (grantResults[2] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "Read phone state is granted", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "read phone state is denied", Toast.LENGTH_SHORT).show();
        }
    }

}