package com.ykpylcn.kutubisittehadisler_v1.utils;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class RunService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AlertDialog.Builder alert=new AlertDialog.Builder(this);
        alert.setMessage("Not yet");
        alert.setTitle("Error");
        alert.setPositiveButton("OK", null);
        alert.create().show();
    }
}
