package com.ykpylcn.kutubisittehadisler_v1.ui.kirkhadis;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ykpylcn.kutubisittehadisler_v1.App;
import com.ykpylcn.kutubisittehadisler_v1.db.Hadis;

import java.util.ArrayList;

public class KirkHadisViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<Hadis>> mListKirkHadis;

    public KirkHadisViewModel(Application application) {
        super(application);
        if(mListKirkHadis==null){
            loadHadisler(application);
        }
    }
    public void loadHadisler(Context context){
        mListKirkHadis= new MutableLiveData<>();


        mListKirkHadis.setValue(App.DbAdapter.getKirkHadis());
    }
    public LiveData<ArrayList<Hadis>> getHadisler() {
        return mListKirkHadis;
    }
}