package com.girltalkradio.ui.multimedia;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MultimediaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MultimediaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed neque diam, venenatis a tincidunt eu, laoreet id metus. Donec vel convallis justo. Morbi eget odio et augue tincidunt iaculis sit amet quis quam. Quisque elit tellus, pharetra eget nulla quis, iaculis pulvinar lacus. Nullam semper elementum est vel dignissim. Sed suscipit cursus mattis.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}