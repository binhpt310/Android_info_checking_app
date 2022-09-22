package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.nfc.Tag;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Iterator;

public class Connectivity {

    public static NetworkInfo getNetworkInfo(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    public static boolean isConnected(Context context){
        NetworkInfo info = Connectivity.getNetworkInfo(context);
        return (info != null && info.isConnected());
    }

    public static boolean isConnectedWifi(Context context){
        NetworkInfo info = Connectivity.getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
    }


    public static boolean isConnectedMobile(Context context){
        NetworkInfo info = Connectivity.getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
    }

    public static String getMobileNetworkType(Context context) {
        TelephonyManager mTelephonyManager = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") int networkType = mTelephonyManager.getNetworkType();
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return "2G";
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return "3G";
            case TelephonyManager.NETWORK_TYPE_LTE:
                return "4G";
            default:
                return "Unknown";
        }
    }

    public static String getDeviceIPAddress (Context context){
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        return ip;
    }

    public static String getWifiSSID (Context context){
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        String ssid = "";
        WifiInfo wifiInfo = wm.getConnectionInfo();
        if (wifiInfo.getSupplicantState() == SupplicantState.COMPLETED) {
            ssid = wifiInfo.getSSID();
        }
        return ssid;
    }

    public static String getSimState(Context context){
        TelephonyManager telephony_manager = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);

        int simState = telephony_manager.getSimState(0); //slotIndex = 0 or 1
        String result = "";
        switch (simState) {
            case TelephonyManager.SIM_STATE_ABSENT:
                result = "No SIM card is available";

            case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
                result = "Requires a network PIN to unlock";

            case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                result = "Requires the user's SIM PIN to unlock";

            case TelephonyManager.SIM_STATE_PUK_REQUIRED:
                result = "Requires the user's SIM PUK to unlock";

            case TelephonyManager.SIM_STATE_READY:
                result =  "Sim slot is ready";

            case TelephonyManager.SIM_STATE_UNKNOWN:
                result = "Unknown Sim state";
            default:
                result = "Unknown Sim";
        }
        return result;
    }

    public static String getMACAddress(Context context){
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wm.getConnectionInfo();
        @SuppressLint("MissingPermission") String macAddress = wInfo.getMacAddress();
        return macAddress;
    }

//    public static int getPID (Intent intent) {
//        int result = 1;
//        if (intent != null) {
//           if (intent.getAction().equals(UsbManager.ACTION_USB_DEVICE_ATTACHED)) {
//
//                UsbDevice usbDevice = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
//           if (usbDevice != null) {
//                    int pid = usbDevice.getProductId();
//                    result = pid;
//                    return result;
//                }
//                else
//                    result = 2;
//
//            }
//            else
//                result = 3;
//        }
//        return result;
//    }

    public static void getPIDVID (Context context, TextView textView1, TextView textView2,
                                  TextView textView3) {
        UsbManager usbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> deviceList = usbManager.getDeviceList();
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
        StringBuilder stringbuilder1 = new StringBuilder();
        StringBuilder stringbuilder2 = new StringBuilder();

        StringBuilder stringBuilder1 = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();

        if (deviceIterator != null) {
            int Length = deviceList.size();
            textView1.setText(Integer.toString(Length));
            String Tag = "VendorID";

            while (deviceIterator.hasNext()) {
                UsbDevice usbDevice = deviceIterator.next();
                int pid1 = usbDevice.getProductId();
                String pid2 = Integer.toHexString(usbDevice.getProductId());
                stringbuilder1.append(pid1 + ", ");
                stringbuilder2.append(pid2 + ", ");
                //textView2.setText(stringbuilder1.toString());
                //textView3.setText(stringbuilder2);

                int vid1 = usbDevice.getDeviceId();
                String vid2 = Integer.toHexString(usbDevice.getDeviceId());
                Log.i(Tag = "VendorID", String.valueOf(vid1));
                Log.i(Tag = "VendorID hexa", String.valueOf(vid2));

                Log.i(Tag = "ProductID", String.valueOf(pid1));
                Log.i(Tag = "ProductID hexa", String.valueOf(pid2));  
            }
        }
    }
}

