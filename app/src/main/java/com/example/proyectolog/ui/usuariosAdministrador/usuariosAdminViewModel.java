package com.example.proyectolog.ui.usuariosAdministrador;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class usuariosAdminViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public usuariosAdminViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is herramientas fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}