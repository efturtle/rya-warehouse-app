package com.example.proyectolog.ui.cambiarPuesto;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.proyectolog.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import id.zelory.compressor.Compressor;

public class cambiarPuestoFragment extends Fragment {

    private cambiarPuestoViewModel cambiarPuestoViewModel;

    String Puesto;
    String PuestoM;
    String Correo;
    String username;
    String Telefono;
    String Password;
    Button Editar;

    EditText ETPuesto, ETCorreo, ETusername, ETTelegono;
    ArrayList<String> AL = new ArrayList<>();
    DatabaseReference mref = null;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        cambiarPuestoViewModel =
                ViewModelProviders.of(this).get(com.example.proyectolog.ui.cambiarPuesto.cambiarPuestoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cambiarpuesto, container, false);




        return root;
    }
}