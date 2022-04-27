package com.example.proyectolog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

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

import com.bumptech.glide.Glide;
import com.example.proyectolog.ui.cambiarFoto.cambiarFotoViewModel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import id.zelory.compressor.Compressor;

public class cambiarFotoActivity extends AppCompatActivity
{

    private cambiarFotoViewModel cambiarFotoViewModel;
    ImageView foto;
    Button subir, seleccionar;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    ProgressDialog Cargando;
    Bitmap thumb_Bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_foto);

        foto = (ImageView) findViewById(R.id.imgFoto);
        subir = (Button) findViewById(R.id.SubirFoto);
        seleccionar = (Button) findViewById(R.id.SeleccionarFoto);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("login");
        storageReference = FirebaseStorage.getInstance().getReference().child("img_compimidas");
        Cargando = new ProgressDialog(this);



        seleccionar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CropImage.startPickImageActivity(cambiarFotoActivity.this);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK)
        {
            Toast.makeText(this, "If 1", Toast.LENGTH_SHORT);
            Uri uri = CropImage.getPickImageResultUri(this,data);

            CropImage.activity(uri).setGuidelines(CropImageView.Guidelines.ON).setRequestedSize(480, 480).setAspectRatio(2,2).start(this);
        }
        else
        {
            Toast.makeText(this, "If no 1", Toast.LENGTH_SHORT);
        }
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            Toast.makeText(this, "If 2", Toast.LENGTH_SHORT);
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if(resultCode == Activity.RESULT_OK)
            {
                Uri resultUri = result.getUri();

                File url = new File(resultUri.getPath());

                Picasso.with(this).load(url).into(foto);
                try
                {
                    thumb_Bitmap = new Compressor(this).setMaxWidth(480).setMaxHeight(480).setQuality(90).compressToBitmap(url);
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

                        final StorageReference ref = storageReference.child(aleatorio);
                        UploadTask uploadTask = ref.putBytes(thumb_Byte);

                        Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>()
                        {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                            {
                                if(!task.isSuccessful())
                                {
                                    throw Objects.requireNonNull(task.getException());
                                }
                                return ref.getDownloadUrl();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                Uri downloadUri = task.getResult();
                                databaseReference.child(preferences.getData_User(cambiarFotoActivity.this)+"").child("urlfoto").setValue(downloadUri.toString());
                                Cargando.dismiss();
                                Toast.makeText(cambiarFotoActivity.this, "Imagen cargada con exito", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }
                });
            }
        }
    }
}



