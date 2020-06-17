package com.ykpylcn.kutubisittehadisler_v1.utils;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.JobIntentService;

import com.ykpylcn.kutubisittehadisler_v1.App;
import com.ykpylcn.kutubisittehadisler_v1.db.DBAdapter;
import com.ykpylcn.kutubisittehadisler_v1.db.Hadis;

public class RunService extends JobIntentService {
    final Handler mHandler = new Handler();
    private static final int JOB_ID = 2;
    public static Context context1;
    public static void enqueueWork(Context context, Intent intent) {
        enqueueWork(context, RunService.class, JOB_ID, intent);
        context1=context;
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


                DBAdapter db=new DBAdapter(this);
                Hadis hadis=db.getHadis(intent.getLongExtra("hadisid",1));
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
