package com.girltalkradio.ui.multimedia;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MultimediaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MultimediaViewModel() {
    }

    public LiveData<String> getText() {
        return mText;
    }
}