package com.example.proyectolog.ui.tareasAdministrador;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.proyectolog.R;
import com.example.proyectolog.ui.pedidosTecnico.pedidosTecnicoViewModel;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.example.proyectolog.R.layout.support_simple_spinner_dropdown_item;

public class SlideshowFragment extends Fragment {

    private pedidosTecnicoViewModel slideshowViewModel;
    private Spinner mSpinner;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(pedidosTecnicoViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                mSpinner = root.findViewById(R.id.mSpinner);
                
                ArrayList<String> elementos = new ArrayList<>();
                elementos.add("Elemento numero 1");
                elementos.add("Elemento numero 2");
                elementos.add("Elemento numero 3");


            }
        });
        return root;
    }
}