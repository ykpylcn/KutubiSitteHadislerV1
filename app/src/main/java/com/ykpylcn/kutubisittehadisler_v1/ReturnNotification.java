package com.ykpylcn.kutubisittehadisler_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.ykpylcn.kutubisittehadisler_v1.db.DBAdapter;

import java.util.ArrayList;

public class ReturnNotification extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        CheckApp();
        int id=getIntent().getIntExtra("hadisno",1);
        SharedPreferences refindexVP=App.app_context.getSharedPreferences("refindexVP",0);
        SharedPreferences.Editor editor=refindexVP.edit();
        editor.putInt("refindexVPkey",App.DbAdapter.getHadisRowIndex(id));
        editor.commit();

        Log.d("YKPTAG", "hadisid:" +id);

        Intent intent=new Intent(ReturnNotification.this,MainActivity.class);
        startActivity(intent);
        finish();

    }
    private void CheckApp() {

        if(App.app_context==null){
            App.app_context=getApplicationContext();
            Log.d("YKPTAG", "app_context" );
        }

        if(App.DbAdapter==null){
            App.DbAdapter=new DBAdapter(getApplicationContext());
            Log.d("YKPTAG", "DbAdapter" );
        }
        if(App.mArrayListHadisler==null){
            App.mArrayListHadisler=App.DbAdapter.getAllHadislerArrList();
            Log.d("YKPTAG", "App.mArrayListHadisler" );
        }
        if(App.filteredListHadisler==null){
            App.filteredListHadisler = new ArrayList<>();
            Log.d("YKPTAG", "App.filteredListHadisler" );
        }

    }
}