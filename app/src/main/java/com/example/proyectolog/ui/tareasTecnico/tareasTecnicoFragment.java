package com.example.proyectolog.ui.tareasTecnico;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.proyectolog.R;

public class tareasTecnicoFragment extends Fragment {

    private tareasTecnicoViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(tareasTecnicoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tareastecnico, container, false);

        return root;
    }
}