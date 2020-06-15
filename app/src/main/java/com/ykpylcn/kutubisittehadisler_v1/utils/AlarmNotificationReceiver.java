package com.ykpylcn.kutubisittehadisler_v1.utils;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.ykpylcn.kutubisittehadisler_v1.App;
import com.ykpylcn.kutubisittehadisler_v1.db.Hadis;

public class AlarmNotificationReceiver extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {


        Hadis hadis=App.DbAdapter.getHadis(intent.getLongExtra("hadisid",1));
                if(hadis!=null) {
                    NotificationUtils mNotificationUtils=new NotificationUtils(context,hadis.getHadisNo());
                    Notification.Builder nb = mNotificationUtils.
                            getAndroidChannelNotification(hadis.AnaKonu, hadis.getHadis());
                    mNotificationUtils.getManager().notify(hadis.getHadisNo(), nb.build());
                }


    }
}
