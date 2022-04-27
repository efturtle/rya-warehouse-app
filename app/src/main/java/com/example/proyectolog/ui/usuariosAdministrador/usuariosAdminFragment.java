package com.example.proyectolog.ui.usuariosAdministrador;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.proyectolog.R;
import com.example.proyectolog.ui.agregarUsuarios.agregarUsuariosFragment;
import com.example.proyectolog.ui.cambiarFoto.cambiarFotoFragment;
import com.example.proyectolog.ui.cambiarPuesto.cambiarPuestoFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class usuariosAdminFragment extends Fragment {

    private usuariosAdminViewModel galleryViewModel;

    ListView LV;
    ArrayList<String> AL = new ArrayList<>();
    Button Agregar;
    DatabaseReference mref;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(usuariosAdminViewModel.class);
        View root = inflater.inflate(R.layout.fragment_usuariosadmi, container, false);

        Agregar = (Button) root.findViewById(R.id.button);

        LV = (ListView) root.findViewById(R.id.LV);

        mref = FirebaseDatabase.getInstance().getReference();

        mref.child("login").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists())
                {
                    for(DataSnapshot ds : dataSnapshot.getChildren())
                    {
                        String texto = ds.child("username").getValue().toString();
                        String puesto = ds.child("puesto").getValue().toString();
                        if(puesto.equals("Administrador"))
                        {

                        }
                        else
                        {
                            AL.add(texto);
                        }
                    }

                    ArrayAdapter<String> adapter =
                            new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,AL);
                    LV.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Agregar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Fragment nuevoFragmento = new agregarUsuariosFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, nuevoFragmento);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                String nombre = (String) LV.getItemAtPosition(i);
                Bundle datosAEnviar = new Bundle();
                datosAEnviar.putString("nombre", nombre);

                Fragment fragmento = new cambiarPuestoFragment();

                fragmento.setArguments(datosAEnviar);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, fragmento);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return root;
    }
}