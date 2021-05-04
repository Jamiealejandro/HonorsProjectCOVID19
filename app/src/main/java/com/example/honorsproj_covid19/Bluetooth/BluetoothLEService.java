package com.example.honorsproj_covid19.Bluetooth;

import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothClass;
import android.content.BroadcastReceiver;

import android.content.Intent;
import android.os.Handler;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.UUID;


@SuppressLint("NewApi")
public class BluetoothLEService extends Service {
    private final static String TAG = BluetoothLEService.class.getSimpleName();
    String LOG_TAG = "BluetoothLEService";

    private BluetoothAdapter newBLEAdapter;
    private BluetoothManager manager;
    private String newBluetoothDeviceAddress;
    private BluetoothGatt newBluetoothGatt;
    private BluetoothGattService newBluetoothGattService;
    private int newConnectionState = STATE_DISCONNECTED;
    private final IBinder newBinder = new LocalBinder();
    private Handler scanHandler = new Handler();

    public class LocalBinder extends Binder {
        BluetoothLEService getService(){
            return BluetoothLEService.this;
        }
    }

    private static final int STATE_DISCONNECTED = 0;
    private static final int STATE_CONNECTING = 1;
    private static final int STATE_CONNECTED = 2;

    public final static String ACTION_GATT_CONNECTED = "com.example.honorsproj_covid19.Bluetooth.BluetoothLEService.ACTION_GATT_CONNECTED";
    public final static String ACTION_GATT_DISCONNECTED = "com.example.honorsproj_covid19.Bluetooth.BluetoothLEService.ACTION_GATT_DISCONNECTED";
    public final static String ACTION_GATT_SERVICES_DISCOVERED = "com.example.honorsproj_covid19.Bluetooth.BluetoothLEService.ACTION_GATT_SERVICES_DISCOVERED";
    public final static String ACTION_DATA_AVAILABLE = "com.example.honorsproj_covid19.Bluetooth.BluetoothLEService.ACTION_DATA_AVAILABLE";
    public final static String EXTRA_DATA = "com.example.honorsproj_covid19.Bluetooth.BluetoothLEService.EXTRA_DATA";


    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        turnonBLE();
        discoverBLEDevices();
    }

    @SuppressLint("NewApi")
    private void turnonBLE(){
        manager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        newBLEAdapter = manager.getAdapter();
        newBLEAdapter.enable();
        Toast.makeText(getApplicationContext(), "BTLE ON Service", Toast.LENGTH_LONG).show();
        Log.e("BLE_Scanner", "TurnOnBLE");
    }

    @SuppressLint("NewApi")
    private void discoverBLEDevices() {
        startScan.run();
        Log.e("BLE_Scanner", "DiscoverBLE");
    }

    private Runnable startScan = new Runnable() {
        @Override
        public void run(){
            scanHandler.postDelayed(stopScan, 500);
            newBLEAdapter.startLeScan(nLeScanCallBack);
        }
    };

    @SuppressLint("NewApi")
    private BluetoothAdapter.LeScanCallback nLeScanCallBack = new BluetoothAdapter.LeScanCallback() {

        @SuppressLint("NewApi")
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord){
            String Address = device.getAddress();
            String Name = device.getName();
            Log.e("nLeScanCallback", ""+Address+ " : " + Name);
        }
    };

    private Runnable stopScan = new Runnable() {
        @Override
        public void run(){
            newBLEAdapter.stopLeScan(nLeScanCallBack);
            scanHandler.postDelayed(startScan, 10);
        }
    };





}
