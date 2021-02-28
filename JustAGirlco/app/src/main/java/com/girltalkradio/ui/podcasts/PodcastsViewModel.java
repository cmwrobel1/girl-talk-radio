package com.girltalkradio.ui.podcasts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PodcastsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PodcastsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Authors Podcast Page");
    }

    public LiveData<String> getText() {
        return mText;
    }
}