package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.os.Bundle;
import android.widget.TextView;

public class Check_Sim extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_sim);
        TextView Number = findViewById(R.id.sim_number_txt);
        TextView Sim_Slot1 = findViewById(R.id.sim_slot_number1_txt);
        TextView Sim_Slot2 = findViewById(R.id.sim_slot_number2_txt);

        TelephonyManager telephony_manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        SubscriptionManager subscriptionManager = (SubscriptionManager) getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);

        @SuppressLint("MissingPermission") int Sim_number = subscriptionManager.getActiveSubscriptionInfoCountMax();
        Number.setText(Integer.toString(Sim_number));
        int simStateMain = telephony_manager.getSimState(0);
        switch (simStateMain) {
            case TelephonyManager.SIM_STATE_ABSENT:
                Sim_Slot1.setText("No SIM card is available");

            case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
                Sim_Slot1.setText("Requires a network PIN to unlock");

            case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                Sim_Slot1.setText("Requires the user's SIM PIN to unlock");

            case TelephonyManager.SIM_STATE_PUK_REQUIRED:
                Sim_Slot1.setText("Requires the user's SIM PUK to unlock");

            case TelephonyManager.SIM_STATE_READY:
                Sim_Slot1.setText("Sim slot is ready");

            case TelephonyManager.SIM_STATE_UNKNOWN:
                Sim_Slot1.setText("Unknown Sim");


                int simStateSecond = telephony_manager.getSimState(1);
                switch (simStateSecond) {
                    case TelephonyManager.SIM_STATE_ABSENT:
                        Sim_Slot2.setText("No SIM card is available");
                    case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
                        Sim_Slot2.setText("Requires a network PIN to unlock");
                    case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                        Sim_Slot2.setText("Requires the user's SIM PIN to unlock");

                    case TelephonyManager.SIM_STATE_PUK_REQUIRED:
                        Sim_Slot2.setText("Requires the user's SIM PUK to unlock");

                    case TelephonyManager.SIM_STATE_READY:
                        Sim_Slot2.setText("Sim slot is ready");

                    case TelephonyManager.SIM_STATE_UNKNOWN:
                        Sim_Slot2.setText("Unknown Sim");

                }
        }
    }
}


