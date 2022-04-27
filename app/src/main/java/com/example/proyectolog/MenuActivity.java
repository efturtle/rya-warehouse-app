package com.example.proyectolog;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.util.Strings;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MenuActivity extends AppCompatActivity {

    public AppBarConfiguration mAppBarConfiguration;
    public String Navegador= "";
    public NavigationView navigationView;
    TextView puesto, nombre;
    ImageView foto;

    Menu menu;


    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_administrador);

        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.homeFragment, R.id.tareasTecnicoFragment, R.id.usuariosAdminFragment, R.id.slideshowFragment).setDrawerLayout(drawer).build();
        Menu menu;

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        updateNavHeader();



        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("login").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.child(preferences.getData_User(getApplicationContext())).child("puesto").getValue(String.class).equals("Administrador"))
                {
                    MostarAdministrador();
                }
                else if (dataSnapshot.child(preferences.getData_User(getApplicationContext())).child("puesto").getValue(String.class).equals("Tecnico"))
                {
                    MostarTecnico();
                }
                else if (dataSnapshot.child(preferences.getData_User(getApplicationContext())).child("puesto").getValue(String.class).equals("Administrador_Almacen"))
                {
                    MostarAlmacen();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void logout()
    {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        preferences.clearData(getApplicationContext());
    }

    public void MostarAdministrador()
    {
        /*menu = navigationView.getMenu();
        MenuItem vivible = menu.findItem(R.id.grupoTecnico);
        vivible.setVisible(false);*/
    }

    public void MostarTecnico()
    {

    }

    public void MostarAlmacen()
    {

    }

    public void updateNavHeader()
    {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_administrador);
        View headerView = navigationView.getHeaderView(0);
        TextView navPuesto = headerView.findViewById(R.id.puesto);
        TextView navUsername = headerView.findViewById(R.id.nombre);
        ImageView imagen = headerView.findViewById(R.id.imageView);

        navPuesto.setText("Puesto");
        navUsername.setText("User");

        Glide.with(MenuActivity.this).load("https://firebasestorage.googleapis.com/v0/b/rtdb-6fe7e.appspot.com/o/img_compimidas%2Fno%20perfil.png?alt=media&token=2de01c7a-77e1-4970-bf91-126c138f3b98").into(imagen);

    }
}
