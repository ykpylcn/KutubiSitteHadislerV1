package com.ykpylcn.kutubisittehadisler_v1.ui.search;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ykpylcn.kutubisittehadisler_v1.App;
import com.ykpylcn.kutubisittehadisler_v1.db.Hadis;

import java.util.ArrayList;

public class SearchViewModel extends AndroidViewModel {
    private MutableLiveData<ArrayList<Hadis>> mArrListHadisler;
    public SearchViewModel(Application application) {
        super(application);
        if(mArrListHadisler==null){
            loadHadisler(application);
        }
    }
    public void loadHadisler(Context context){
        mArrListHadisler= new MutableLiveData<>();

//        Message.show(context,"loadHadisler(Context context) calisti");
//        ArrayList<Hadis> list= App.DbAdapter.getAllHadislerArrList();
        mArrListHadisler.setValue(App.mArrayListHadisler);
    }
    public LiveData<ArrayList<Hadis>> getHadisler() {
        return mArrListHadisler;
    }
}
