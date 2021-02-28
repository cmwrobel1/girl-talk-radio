package com.example.justagirlco.ui.top10;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Top10ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Top10ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Top Rated Should go Here");
    }

    public LiveData<String> getText() {
        return mText;
    }
}