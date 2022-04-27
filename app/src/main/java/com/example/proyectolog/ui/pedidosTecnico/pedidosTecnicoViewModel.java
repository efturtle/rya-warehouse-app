package com.example.proyectolog.ui.pedidosTecnico;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class pedidosTecnicoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public pedidosTecnicoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}