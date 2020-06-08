package com.ykpylcn.kutubisittehadisler_v1.ui;
import android.content.Context;
import android.widget.Toast;

import com.ykpylcn.kutubisittehadisler_v1.App;

public class Message {
    public static void show(String message){
        Toast toast = Toast.makeText(App.app_context,
                message,
                Toast.LENGTH_SHORT);
        toast.show();

    }
}