package com.example.jgram.ui.addimage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddImageViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AddImageViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Upload a image");
    }

    public LiveData<String> getText() {
        return mText;
    }
}