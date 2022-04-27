package com.example.proyectolog.ui.agregarUsuarios;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class agregarUsuariosViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public agregarUsuariosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}