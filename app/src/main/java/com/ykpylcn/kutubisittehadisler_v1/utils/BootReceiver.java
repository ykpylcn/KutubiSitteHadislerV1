package com.ykpylcn.kutubisittehadisler_v1.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ykpylcn.kutubisittehadisler_v1.App;
import com.ykpylcn.kutubisittehadisler_v1.db.Notif;

import java.util.Calendar;
import java.util.List;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        Log.d("YKPTAG", "BOOT_COMPLETED onReceive Started" );
        String action = intent.getAction();
        if(action!=null)
        if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
            // Do my stuff
//            Log.d("YKPTAG", "BOOT_COMPLETED Started" );
            List<Notif> notifList= App.DbAdapter.getAllNotifs();
            intent.setClassName(context.getPackageName(),"AlarmNotificationReceiver.class");
            for (Notif notif:notifList) {


                intent.putExtra("hadisid",notif.HadisID);
                intent.setAction(String.valueOf(notif.HadisID));

                if(!NotificationUtils.checkNatification(intent,context)){
//                    Log.d("YKPTAG", "BOOT__NotificationUtils.checkNatification(intent,context) is false" );
                    AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
                    PendingIntent pendingIntent;

                    // SET TIME HERE
                    Calendar calendar= Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY,notif.Hour);
                    calendar.set(Calendar.MINUTE,notif.Minute);
                    calendar.set(Calendar.SECOND,0);

                    pendingIntent = PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

                    if(!notif.IsDaily)
                        manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pendingIntent);
                    else
                        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY,pendingIntent);
                }
            }


        }
    }
}
