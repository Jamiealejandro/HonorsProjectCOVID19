package com.example.honorsproj_covid19.Bluetooth;


import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BroadcastReceiver {
    private static final String TAG = "BroadcastReceiver";

    public static final String RECEIVE_UPDATE = "BLE_UPDATES";


    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Received broadcast");
        String action = intent.getAction();
        if (action.equals(RECEIVE_UPDATE))
        {
            Log.i(TAG, "Received ble update from service!");
        }
    }
}
