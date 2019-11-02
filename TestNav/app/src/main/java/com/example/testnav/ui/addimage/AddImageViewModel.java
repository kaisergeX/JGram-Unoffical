package com.example.testnav.ui.addimage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddImageViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AddImageViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is add image fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}