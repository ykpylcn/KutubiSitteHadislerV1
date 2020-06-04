package com.ykpylcn.kutubisittehadisler_v1.ui;
import android.content.Context;
import android.widget.Toast;

public class Message {
    public static void show(Context context, String message){
        Toast toast = Toast.makeText(context,
                message,
                Toast.LENGTH_SHORT);
        toast.show();

    }
}