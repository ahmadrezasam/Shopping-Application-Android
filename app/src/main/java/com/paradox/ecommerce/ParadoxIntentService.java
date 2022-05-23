package com.paradox.ecommerce;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.net.MalformedURLException;
import java.net.URL;

import static com.paradox.ecommerce.App.CHANNEL_ID;

public class ParadoxIntentService extends IntentService {

    private static final String TAG = "com.paradox.ecommerce";
    private PowerManager.WakeLock wakeLock;

    public ParadoxIntentService() {
        super("ParadoxIntentService");
        setIntentRedelivery(true);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                "ExampleApp:Wakelock");
        wakeLock.acquire();
        Log.d(TAG, "Wakelock acquired");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Paradox")
                    .setContentText("Running...")
                    .setSmallIcon(R.drawable.ic_cart_black)
                    .build();

            startForeground(1, notification);
        }
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i(TAG, "This Paradox IntentService is started");
        SystemClock.sleep(60000);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");

        wakeLock.release();
        Log.d(TAG, "Wakelock released");
    }
}
