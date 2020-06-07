package com.ykpylcn.kutubisittehadisler_v1;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.ykpylcn.kutubisittehadisler_v1.db.DBAdapter;
import com.ykpylcn.kutubisittehadisler_v1.db.Hadis;

import java.util.ArrayList;

public class App extends Application {


    public static ArrayList<Hadis> mArrayListHadisler;
    public static String package_name;
    public static DBAdapter DbAdapter;
    @Override
    public void onCreate() {
        super.onCreate();
        package_name = getPackageName();
    }
}