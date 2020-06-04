package com.ykpylcn.kutubisittehadisler_v1.ui.home;



import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ykpylcn.kutubisittehadisler_v1.db.DBAdapter;
import com.ykpylcn.kutubisittehadisler_v1.db.Hadis;

import java.util.ArrayList;


public class HomeViewModel extends AndroidViewModel {


    private MutableLiveData<String> mText;
    private MutableLiveData<ArrayList<Hadis>> mArrListHadisler;
    private DBAdapter dbAdapter;
    public HomeViewModel(Application application) {
        super(application);

        if(mArrListHadisler==null){
            loadHadisler(application);
        }
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");


    }
    public void loadHadisler(Context context){
        mArrListHadisler= new MutableLiveData<>();
        dbAdapter=new DBAdapter(context);
//        Message.show(context,"loadHadisler(Context context) calisti");
        ArrayList<Hadis> list=dbAdapter.getAllHadislerArrList();
        mArrListHadisler.setValue(list);
    }
    public void setValue(String val){

        mText.setValue(val);
    }

    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<ArrayList<Hadis>> getHadisler() {
        return mArrListHadisler;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        dbAdapter.close();
    }
    //    public LiveData<ArrayList<Hadis>> getHadisArrayList() {
//        return mArrListHadisler;
//
//    }
}