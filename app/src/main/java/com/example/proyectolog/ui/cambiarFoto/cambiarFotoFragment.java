package com.example.proyectolog.ui.cambiarFoto;


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

import id.zelory.compressor.Compressor;

public class cambiarFotoFragment extends Fragment {

    private cambiarFotoViewModel cambiarFotoViewModel;
    ImageView foto;
    Button subir, seleccionar;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    ProgressDialog Cargando;

    Bitmap thumb_Bitmap = null;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        cambiarFotoViewModel =
                ViewModelProviders.of(this).get(cambiarFotoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cambiarfoto, container, false);

        foto = (ImageView) root.findViewById(R.id.imgFoto);
        subir = (Button) root.findViewById(R.id.SubirFoto);
        seleccionar = (Button) root.findViewById(R.id.SeleccionarFoto);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("fotos_subidas");
        storageReference = FirebaseStorage.getInstance().getReference().child("img_compimidas");
        Cargando = new ProgressDialog(getContext());

        seleccionar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CropImage.startPickImageActivity(getActivity());
            }
        });


        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK)
        {
            Toast.makeText(getContext(), "If 1", Toast.LENGTH_SHORT);
            Uri uri = CropImage.getPickImageResultUri(getActivity(),data);

            CropImage.activity(uri).setGuidelines(CropImageView.Guidelines.ON).setRequestedSize(640, 480).setAspectRatio(2,1).start(getActivity());
        }
        else
        {
            Toast.makeText(getContext(), "If no 1", Toast.LENGTH_SHORT);
        }
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            Toast.makeText(getContext(), "If 2", Toast.LENGTH_SHORT);
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if(resultCode == Activity.RESULT_OK)
            {
                Uri resultUri = result.getUri();

                File url = new File(resultUri.getPath());

                Picasso.with(getContext()).load(url).into(foto);
                try
                {
                    thumb_Bitmap = new Compressor(requireContext()).setMaxWidth(640).setMaxHeight(480).setQuality(90).compressToBitmap(url);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                thumb_Bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
                final byte [] thumb_Byte = byteArrayOutputStream.toByteArray();

                int p = (int) (Math.random() * 25 + 1);
                int s = (int) (Math.random() * 25 + 1);
                int t = (int) (Math.random() * 25 + 1);
                int c = (int) (Math.random() * 25 + 1);

                int numero1 = (int) (Math.random() +1012 + 2111);
                int numero2 = (int) (Math.random() +1012 + 2111);

                String[] elementos =
                {
                        "a","b","c","d","e","f","g","h","i","j","k","l","m",
                        "n","o","p","q","r","s","t","u","v","w","x","y","z"
                };

                final String aleatorio = elementos[p] + elementos[s] + numero1 + elementos[t] + elementos[c] + numero2 + " comprimido.jpg";

                subir.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Cargando.setTitle("Subiendo foto...");
                        Cargando.setMessage("Espere unos segundos...");
                        Cargando.show();
                    }
                });

            }
        }
    }
}