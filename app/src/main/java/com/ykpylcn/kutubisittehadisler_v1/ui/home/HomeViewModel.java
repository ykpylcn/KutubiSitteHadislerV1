package com.ykpylcn.kutubisittehadisler_v1.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment \n DEnmeme dnwjbdhewvhcb n cjwebhucehj cjd vjb ehuvhbedsj v\nkrehgbhrjewjg brejwbhjgbr ehjwbhjbnejn");
    }

    public LiveData<String> getText() {
        return mText;
    }
}