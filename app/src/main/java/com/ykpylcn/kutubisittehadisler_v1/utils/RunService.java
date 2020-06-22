package com.ykpylcn.kutubisittehadisler_v1.utils;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import com.ykpylcn.kutubisittehadisler_v1.db.DBAdapter;
import com.ykpylcn.kutubisittehadisler_v1.db.Hadis;
import com.ykpylcn.kutubisittehadisler_v1.db.Notif;

public class RunService extends JobIntentService {
    final Handler mHandler = new Handler();
    private static final int JOB_ID = 2;

    public static void enqueueWork(Context context, Intent intent) {
        enqueueWork(context, RunService.class, JOB_ID, intent);

    }

    @Override
    public void onCreate() {
        super.onCreate();
//        showToast("Job Execution Started");
        Log.d("YKPTAG", "Started" );
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
//        showToast("Job Execution Finished");
        Log.d("YKPTAG", "Stopped" );
    }
    @Override
    protected void onHandleWork(@NonNull Intent intent) {


        try {

                Hadis hadis=null;
                DBAdapter db=new DBAdapter(this);
                Notif notif=db.getNotifByHadisID(intent.getLongExtra("hadisid",1));
                if(notif!=null){
                    if(notif.HadisShowType==0)
                        hadis=db.getHadis(intent.getLongExtra("hadisid",1));
                    else if(notif.HadisShowType==1)
                        hadis=db.getOneHadisRandom(true);
                    else
                        hadis=db.getOneHadisRandom(false);
                }

                if(hadis!=null) {
                    NotificationUtils mNotificationUtils=new NotificationUtils(this,hadis.getHadisNo());
                    Notification.Builder nb = mNotificationUtils.
                            getAndroidChannelNotification(hadis.getAnaKonu(), hadis.getHadis());
                    mNotificationUtils.getManager(this).notify(hadis.getHadisNo(), nb.build());
                }

        } catch (Exception e) {
            showToast("Bildirimler Hata : "+e.getMessage());
            e.printStackTrace();
            Log.d("YKPTAG", "Err: " + e.getMessage());
        }

    }
    // Helper for showing tests
    void showToast(final CharSequence text) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(RunService.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
