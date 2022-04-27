package com.example.proyectolog.ui.agregarUsuarios;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.proyectolog.R;
import com.example.proyectolog.ui.usuariosAdministrador.usuariosAdminFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class agregarUsuariosFragment extends Fragment {

    private agregarUsuariosViewModel agregarUsuariosViewModel;

    private EditText Nombre, Correo, Telefono, Password, PasswordConfirm;
    private RadioGroup grupo;
    private Button registrar;
    private DatabaseReference mDatabase;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        agregarUsuariosViewModel =
                ViewModelProviders.of(this).get(agregarUsuariosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_agregarusuarios, container, false);

        Nombre = (EditText) root.findViewById(R.id.Nombre);
        Telefono = (EditText) root.findViewById(R.id.Telefono);
        Correo = (EditText) root.findViewById(R.id.Correo);
        Password = (EditText) root.findViewById(R.id.Password);
        PasswordConfirm = (EditText) root.findViewById(R.id.PasswordConfirm);
        registrar = (Button) root.findViewById(R.id.Registrar);

        grupo = (RadioGroup) root.findViewById(R.id.grupo);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        registrar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (Nombre.getText().toString().isEmpty() || Telefono.getText().toString().isEmpty() || Correo.getText().toString().isEmpty() || Password.getText().toString().isEmpty() || PasswordConfirm.getText().toString().isEmpty())
                {
                    Toast.makeText(getContext(), "Complete todos los campos", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(Password.getText().toString().equals(PasswordConfirm.getText().toString()))
                    {
                        String puesto = null;
                        switch (grupo.getCheckedRadioButtonId())
                        {
                            case R.id.ADMA:
                                puesto = "Administrador_Almacen";
                                break;

                            case R.id.Admin:
                                puesto = "Administrador";
                                break;

                            case R.id.Tecnico:
                                puesto = "Tecnico";
                                break;

                        }

                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + Correo.getText().toString()));
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Bienvenido " + Nombre.getText());
                        intent.putExtra(Intent.EXTRA_TEXT, "Tu usuario para ingresar sera...\n'" + Nombre.getText() + "'\nY la contraseña es...\n'"+Password.getText()+"'");
                        startActivity(intent);

                        Map<String, Object> DatosUsuario = new HashMap<>();
                        DatosUsuario.put("username",Nombre.getText().toString());
                        DatosUsuario.put("Correo", Correo.getText().toString());
                        DatosUsuario.put("Telefono", Telefono.getText().toString());
                        DatosUsuario.put("password", Password.getText().toString());
                        DatosUsuario.put("urlfoto", "https://firebasestorage.googleapis.com/v0/b/rtdb-6fe7e.appspot.com/o/img_compimidas%2Fno%20perfil.png?alt=media&token=2de01c7a-77e1-4970-bf91-126c138f3b98");
                        DatosUsuario.put("puesto", puesto);


                        mDatabase.child("login").child(Nombre.getText().toString()).setValue(DatosUsuario);

                        Fragment nuevoFragmento = new usuariosAdminFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.nav_host_fragment, nuevoFragmento);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                    else
                    {
                        Toast.makeText(getContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return root;
    }
}

