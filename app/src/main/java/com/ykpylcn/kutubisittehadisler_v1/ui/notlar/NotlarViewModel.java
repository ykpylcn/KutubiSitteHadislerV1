package com.ykpylcn.kutubisittehadisler_v1.ui.notlar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotlarViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NotlarViewModel() {
        mText = new MutableLiveData<>();
        //mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}