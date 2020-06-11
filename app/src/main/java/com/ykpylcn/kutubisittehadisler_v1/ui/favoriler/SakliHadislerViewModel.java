package com.ykpylcn.kutubisittehadisler_v1.ui.favoriler;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ykpylcn.kutubisittehadisler_v1.App;
import com.ykpylcn.kutubisittehadisler_v1.db.Hadis;

import java.util.ArrayList;

public class SakliHadislerViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<Hadis>> mListHadisler;

    public SakliHadislerViewModel(Application application) {
        super(application);
        if(mListHadisler==null){
            loadHadisler(application);
        }
    }
    public void loadHadisler(Context context){
        mListHadisler= new MutableLiveData<>();
        ArrayList<Hadis> list=App.DbAdapter.getIsFavHadisler(1);
//        for(Hadis hadis : App.mArrayListHadisler) {
//
//            if (hadis.getIsFav())
//                list.add(hadis);
//        }

        mListHadisler.setValue(list);
    }
    public LiveData<ArrayList<Hadis>> getHadisler() {
        return mListHadisler;
    }
}