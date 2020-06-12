package com.ykpylcn.kutubisittehadisler_v1;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ykpylcn.kutubisittehadisler_v1.db.Hadis;

import java.util.ArrayList;

public class TestViewModel extends AndroidViewModel {
    private MutableLiveData<ArrayList<Hadis>> mArrListHadisler;
    public TestViewModel(@NonNull Application application) {
        super(application);
        if(mArrListHadisler==null){
            loadHadisler(application);
        }
    }
    public void loadHadisler(Context context){
        mArrListHadisler= new MutableLiveData<>();

       // mArrListHadisler.setValue(App.mArrayListHadisler);
    }
    public LiveData<ArrayList<Hadis>> getHadisler() {
        return mArrListHadisler;
    }
}