package com.example.proyectolog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity
{
    private EditText UserName, Password;
    private Button Siguiente;
    Switch active;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        UserName = (EditText) findViewById(R.id.UserName);
        Password = (EditText) findViewById(R.id.Password);

        Siguiente = (Button) findViewById(R.id.Siguiente);
        active = (Switch) findViewById(R.id.switch1);

        Siguiente.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("login").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        final String userName = UserName.getText().toString();
                        final String password = Password.getText().toString();

                        if(userName.isEmpty() || password.isEmpty())
                        {
                            Toast.makeText(LoginActivity.this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            if(dataSnapshot.child(userName).exists())
                            {
                                if(dataSnapshot.child(userName).child("password").getValue(String.class).equals(password))
                                {
                                    if(active.isChecked())
                                    {
                                        if(dataSnapshot.child(userName).child("puesto").getValue(String.class).equals("Administrador"))
                                        {
                                            preferences.setDataLogin(LoginActivity.this, true);
                                            preferences.setDataAs(LoginActivity.this, "Administrador");
                                            preferences.setData_User(LoginActivity.this, userName);
                                            goToAdmin();
                                        }
                                        else if (dataSnapshot.child(userName).child("puesto").getValue(String.class).equals("Tecnico"))
                                        {
                                            preferences.setDataLogin(LoginActivity.this, true);
                                            preferences.setDataAs(LoginActivity.this, "Tecnico");
                                            preferences.setData_User(LoginActivity.this, userName);

                                            goToUser();
                                        }
                                        else if (dataSnapshot.child(userName).child("puesto").getValue(String.class).equals("Administrador_Almacen"))
                                        {
                                            preferences.setDataLogin(LoginActivity.this, true);
                                            preferences.setDataAs(LoginActivity.this, "Administrador_Almacen");
                                            preferences.setData_User(LoginActivity.this, userName);
                                            goToAdminAlmacen();
                                        }
                                    }
                                    else
                                    {
                                        if(dataSnapshot.child(userName).child("puesto").getValue(String.class).equals("Administrador"))
                                        {
                                            preferences.setDataLogin(LoginActivity.this, false);
                                            preferences.setData_User(LoginActivity.this, userName);
                                            goToAdmin();

                                        }
                                        else if(dataSnapshot.child(userName).child("puesto").getValue(String.class).equals("Tecnico"))
                                        {
                                            preferences.setDataLogin(LoginActivity.this, false);
                                            preferences.setData_User(LoginActivity.this, userName);
                                            goToUser();
                                        }
                                        else if (dataSnapshot.child(userName).child("puesto").getValue(String.class).equals("Administrador_Almacen"))
                                        {
                                            preferences.setDataLogin(LoginActivity.this, true);
                                            preferences.setDataAs(LoginActivity.this, "Administrador_Almacen");
                                            preferences.setData_User(LoginActivity.this, userName);
                                            goToAdminAlmacen();
                                        }
                                    }
                                }
                                else
                                {
                                    Toast.makeText(LoginActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(LoginActivity.this, "Usuario inexistente", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    public void goToUser()
    {
        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
        startActivity(intent);
    }

    public void goToAdmin()
    {
        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
        Toast.makeText(getApplicationContext(), "" + preferences.getData_User(this), Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
    public void goToAdminAlmacen()
    {
        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);

        startActivity(intent);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        if(preferences.getData_login(this))
        {
            if(preferences.getDataAs(this).equals("Administrador"))
            {
                goToAdmin();
                finish();
            }
            else if(preferences.getDataAs(this).equals("Tecnico"))
            {
                goToUser();
                finish();
            }
            else if(preferences.getDataAs(this).equals("Administrador_Almacen"))
            {
                goToAdminAlmacen();
                finish();
            }
        }
    }
}