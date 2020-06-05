package com.ykpylcn.kutubisittehadisler_v1;

import android.app.Application;

import com.ykpylcn.kutubisittehadisler_v1.db.DBAdapter;

public class App extends Application {


    public static String package_name;
    public static DBAdapter DbAdapter;
    @Override
    public void onCreate() {
        super.onCreate();
        package_name = getPackageName();
    }
}