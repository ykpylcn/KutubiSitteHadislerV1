package com.ykpylcn.kutubisittehadisler_v1.utils;

import android.app.Notification;
import android.app.job.JobService;
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

        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            Intent mIntent2 = new Intent(context, RunService.class);

            mIntent2.putExtra("hadisid", 500);
            RunService.enqueueWork(context, mIntent2);
//            Intent serviceLauncher = new Intent(context, RunService.class);
//            context.startService(serviceLauncher);
//            Log.v("TEST", "Service loaded at start");
        }else {
            Intent mIntent = new Intent(context, RunService.class);

            mIntent.putExtra("hadisid", intent.getLongExtra("hadisid",1));
            RunService.enqueueWork(context, mIntent);
        }

//        Hadis hadis=App.DbAdapter.getHadis(intent.getLongExtra("hadisid",1));
//                if(hadis!=null) {
//                    NotificationUtils mNotificationUtils=new NotificationUtils(context,hadis.getHadisNo());
//                    Notification.Builder nb = mNotificationUtils.
//                            getAndroidChannelNotification(hadis.AnaKonu, hadis.getHadis());
//                    mNotificationUtils.getManager().notify(hadis.getHadisNo(), nb.build());
//                }
//                else {
//                    NotificationUtils mNotificationUtils=new NotificationUtils(context,100);
//                    Notification.Builder nb = mNotificationUtils.
//                            getAndroidChannelNotification("Hadis yok", "hadis bulunamadi null");
//                    mNotificationUtils.getManager().notify(hadis.getHadisNo(), nb.build());
//                }


    }
}
