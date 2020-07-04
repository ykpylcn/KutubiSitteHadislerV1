package com.ykpylcn.kutubisittehadisler_v1;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.MutableLiveData;

import com.ykpylcn.kutubisittehadisler_v1.db.DBAdapter;
import com.ykpylcn.kutubisittehadisler_v1.db.Hadis;

import java.util.ArrayList;

public class App extends Application {


    public static Context app_context;
    public static ArrayList<Hadis> mArrayListHadisler;
    public static ArrayList<Hadis> filteredListHadisler;
    public static String package_name;
    public static DBAdapter DbAdapter;
    @Override
    public void onCreate() {
        super.onCreate();
        package_name = getPackageName();
    }
    public static void Share(Hadis hadis) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody =hadis.getHadis();
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, hadis.getAnaKonu());
        sharingIntent.putExtra(Intent.EXTRA_TITLE, hadis.getAltKonu());
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Intent chooserIntent = Intent.createChooser(sharingIntent, "Paylaş: ");
        chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        App.app_context.startActivity(chooserIntent);
    }







}


