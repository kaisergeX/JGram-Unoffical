package com.example.jgram.ui.mypage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyPageViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyPageViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("My page");
    }

    public LiveData<String> getText() {
        return mText;
    }
}