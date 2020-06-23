package com.ykpylcn.kutubisittehadisler_v1.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.ykpylcn.kutubisittehadisler_v1.App;


public class Prefs {
    private static SharedPreferences refindexVP=App.app_context.getSharedPreferences("refindexVP",0);;
//    SharedPreferences refindexVP=App.app_context.getSharedPreferences("refindexVP",0);

    public static void PutIntValue(String key, int value) {
        SharedPreferences.Editor editor=refindexVP.edit();
        editor.putInt(key,value);
        editor.commit();
    }

    public static int GetIntValue(String key, int defaultvalue) {

        return refindexVP.getInt(key,defaultvalue);
    }

//    private static SharedPreferences get(final Context context) {
//        return context.getSharedPreferences(App.package_name, Context.MODE_PRIVATE);
//    }

//    static String getPref(final Context context, String pref, String def) {
//        SharedPreferences prefs = Prefs.get(context);
//        String val = prefs.getString(pref, def);
//
//        if (val == null || val.equals("") || val.equals("null"))
//            return def;
//        else
//            return val;
//    }
//
//    static void putPref(final Context context, String pref, String val) {
//        SharedPreferences prefs = Prefs.get(context);
//        SharedPreferences.Editor editor = prefs.edit();
//
//        editor.putString(pref, val);
//        editor.apply();
//    }
//
//    private static int getIntPref(final Context context, String pref, int def) {
//        SharedPreferences prefs = Prefs.get(context);
//        return prefs.getInt(pref, def);
//    }
//
//    private static void putIntPref(final Context context, String pref, int val) {
//        SharedPreferences prefs = Prefs.get(context);
//        SharedPreferences.Editor editor = prefs.edit();
//
//        editor.putInt(pref, val);
//        editor.apply();
//    }
}
