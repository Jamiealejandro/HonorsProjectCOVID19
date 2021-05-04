package com.example.honorsproj_covid19.Bluetooth;



public class BluetoothDevice {

    public String name;
    public int rssi;
    public String scanRecord;

    public BluetoothDevice(String name, int rssi, byte[] scanRecord){
        this.name = name;
        this.rssi = rssi;
        this.scanRecord = BluetoothDevice.bytesToHex(scanRecord);
    }

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }


}
