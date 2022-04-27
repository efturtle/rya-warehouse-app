package com.example.proyectolog.ui.miUser;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.proyectolog.R;
import com.example.proyectolog.cambiarFotoActivity;
import com.example.proyectolog.preferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    View root;
    HomeViewModel homeViewModel;

    Button button;

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

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState)
    {


        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);

        button = (Button) root.findViewById(R.id.button);

        ETPuesto = (EditText) root.findViewById(R.id.puesto);
        ETCorreo = (EditText) root.findViewById(R.id.correo);
        ETusername = (EditText) root.findViewById(R.id.nombre);
        ETTelegono = (EditText) root.findViewById(R.id.telefono);
        ETusername.setKeyListener(null);

        Editar = (Button) root.findViewById(R.id.Editar);

        updateDatos();


        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getContext(), cambiarFotoActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }


    public void updateDatos() {
        mref = FirebaseDatabase.getInstance().getReference();
        mref.child("login").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        username = ds.child("username").getValue().toString();

                        if (username.equals(preferences.getData_User(getContext()))) {
                            Puesto = ds.child("puesto").getValue().toString();
                            Correo = ds.child("Correo").getValue().toString();
                            username = ds.child("username").getValue().toString();
                            Telefono = ds.child("Telefono").getValue().toString();
                            Password = ds.child("password").getValue().toString();

                        }
                    }
                    switch (Puesto) {
                        case "Administrador_Almacen":
                            PuestoM = "Administrador de Almacen";
                            break;

                        case "Administrador":
                            PuestoM = "Administrador general";
                            break;

                        case "Tecnico":
                            PuestoM = "Tecnico general";
                            break;
                    }
                    ETPuesto.setText(PuestoM);
                    ETCorreo.setText(Correo);
                    ETusername.setText(preferences.getData_User(getContext()));
                    ETTelegono.setText(Telefono);


                    ETPuesto.setKeyListener(null);
                    ETusername.setKeyListener(null);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }
}