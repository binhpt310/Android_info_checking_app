package com.example.myapplication;
import com.example.myapplication.Connectivity;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.usb.*;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.hardware.usb.UsbDevice;
import android.widget.TextView;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView PID = findViewById(R.id.product_id_txt);
        TextView VID = findViewById(R.id.vendor_id_txt);

        Button Camera = (Button) findViewById(R.id.check_camera_btn);
        Button Sim = (Button) findViewById(R.id.check_sim_btn);
        Button Wifi = (Button) findViewById(R.id.check_wifi_btn);

        TextView Connectiontype = (TextView) findViewById(R.id.connection_type_txt);
        TextView WifiSSID = (TextView) findViewById(R.id.wifi_ssid_txt);
        TextView Connectiontype_des = (TextView) findViewById(R.id.textView7);
        TextView IPaddress = (TextView) findViewById(R.id.ip_address_txt);
        Context context = getBaseContext();


        Intent intent = getIntent();
        if (intent != null) {
            if (intent.getAction().equals(UsbManager.ACTION_USB_DEVICE_ATTACHED)) {
                UsbDevice usbDevice = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                if (usbDevice != null) {

                    int vid = usbDevice.getVendorId();
                    int pid = usbDevice.getProductId();
                    VID.setText(Integer.toString(vid));
                    PID.setText(Integer.toString(pid));
                }
            }
        }



        Camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent camera = new Intent(MainActivity.this, Check_Camera.class);
                startActivity(camera);
            }
        });


        Sim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sim = new Intent(MainActivity.this, Check_Sim.class);
                startActivity(sim);
            }
        });




        Wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Connectivity.isConnected(context)==true) {
                    if (Connectivity.isConnectedWifi(context) == true) {
                        String ip = Connectivity.getDeviceIPAddress(context);
                        IPaddress.setText(ip);
                        Connectiontype.setText("Wifi connection");
                    }

                    //Mobile data connection
                    else if (Connectivity.isConnectedWifi(context) == false && Connectivity.isConnectedMobile(context) == true){
                        String mobile_data = Connectivity.getMobileNetworkType(context);
                        Connectiontype.setText(mobile_data);
                        Connectiontype_des.setText("Mobile data info:");
                        String networkinfo = Connectivity.getNetworkInfo(context).getExtraInfo();
                        WifiSSID.setText(networkinfo);
                        String ip = Connectivity.getDeviceIPAddress(context);
                        IPaddress.setText(ip);

                    }
                }
                else if (Connectivity.isConnected(context)==false)
                    Connectiontype.setText("No connection");


            }
        });



    }
}