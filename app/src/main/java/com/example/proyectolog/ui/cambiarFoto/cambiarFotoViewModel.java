package com.example.proyectolog.ui.cambiarFoto;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class cambiarFotoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public cambiarFotoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}