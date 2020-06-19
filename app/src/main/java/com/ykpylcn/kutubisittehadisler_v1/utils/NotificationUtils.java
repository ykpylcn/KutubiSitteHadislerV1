package com.ykpylcn.kutubisittehadisler_v1.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.ykpylcn.kutubisittehadisler_v1.App;
import com.ykpylcn.kutubisittehadisler_v1.MainActivity;
import com.ykpylcn.kutubisittehadisler_v1.R;
import com.ykpylcn.kutubisittehadisler_v1.ReturnNotification;
import com.ykpylcn.kutubisittehadisler_v1.SplashScreen;

public class NotificationUtils extends ContextWrapper {
    private NotificationManager mManager;
    public static final String ANDROID_CHANNEL_ID = "com.ykpylcn.kutubisittehadisler";
    public static final String ANDROID_CHANNEL_NAME = "KÜTÜB-İ SİTTE HADİSLER";
    long when;
    Intent intent;
    PendingIntent pIntent;

    public NotificationUtils(Context base,int id) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel(base);
        }
        else
            createChannel2(base);
        intent = new Intent(this, ReturnNotification.class);
        intent.putExtra("hadisno",id);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pIntent = PendingIntent.getActivity(base, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        when = System.currentTimeMillis();
    }
    public static boolean checkNatification(Intent intent, Context activity){

        boolean alarmUp = (PendingIntent.getBroadcast(activity, 0,
                intent,
                PendingIntent.FLAG_NO_CREATE) != null);
        return  alarmUp;
    }
    private void createChannel2(Context base) {
        getManager(base);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createChannel(Context base) {

        // create android channel
        NotificationChannel androidChannel = new NotificationChannel(ANDROID_CHANNEL_ID,
                ANDROID_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        // Sets whether notifications posted to this channel should display notification lights
        androidChannel.enableLights(true);
        // Sets whether notification posted to this channel should vibrate.
        androidChannel.enableVibration(true);
        // Sets the notification light color for notifications posted to this channel
        androidChannel.setLightColor(Color.GREEN);
        // Sets whether notifications posted to this channel appear on the lockscreen or not
        androidChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager(base).createNotificationChannel(androidChannel);


    }
    public NotificationManager getManager(Context base) {
        if (mManager == null) {
            mManager = (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    public Notification.Builder getAndroidChannelNotification(String title, String body) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return new Notification.Builder(getApplicationContext(), ANDROID_CHANNEL_ID)
                    .setContentTitle(title)
                    .setContentText(body)

                    .setStyle(new Notification.BigTextStyle().bigText(body))
                    .setSmallIcon(R.mipmap.ic_launcher_foreground)
                    .setContentIntent(pIntent)
//                    .setWhen(when)
                    .setAutoCancel(true);
        }
        return new Notification.Builder(getApplicationContext())
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(new Notification.BigTextStyle().bigText(body))
                .setSmallIcon(R.mipmap.ic_launcher_foreground)
//                .setWhen(when)
                .setContentIntent(pIntent)
                .setAutoCancel(true);

    }
}
