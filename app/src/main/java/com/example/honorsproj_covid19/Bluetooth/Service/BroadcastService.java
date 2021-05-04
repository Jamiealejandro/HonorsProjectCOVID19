package com.example.honorsproj_covid19.Bluetooth;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.example.honorsproj_covid19.Bluetooth.BroadcastReceiver;
import com.example.honorsproj_covid19.R;

public class BroadcastService extends Service{
    private NotificationManager newManager;

    private  int Notification = R.string.local_service_started;

    public class LocalBinder extends Binder {
        BroadcastService getService(){
            return BroadcastService.this;
        }
    }

    @Override
    public void onCreate(){
        newManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        showNotification();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("BroadcastService", "Received start id " + startId + ": " + intent);
        Intent i = new Intent(BroadcastReceiver.RECEIVE_UPDATE);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(i);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {

        newManager.cancel(Notification);

        Toast.makeText(this, R.string.local_service_stopped, Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private final IBinder mBinder = new LocalBinder();

    private void showNotification(){
        CharSequence text = getText(R.string.local_service_started);

        Notification notification = new Notification.Builder(this)
        .setTicker(text)  // the status text
                .setWhen(System.currentTimeMillis())  // the time stamp
                .setContentTitle(getText(R.string.local_service_label))  // the label of the entry
                .setContentText(text)  // the contents of the entry
                .build();

        newManager.notify(Notification, notification);
    }
}
