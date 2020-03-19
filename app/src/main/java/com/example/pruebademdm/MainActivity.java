package com.example.pruebademdm;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.pruebademdm.data.LoginDataSource;
import com.example.pruebademdm.data.LoginRepository;
import com.example.pruebademdm.data.model.LoggedInUser;
import com.example.pruebademdm.ui.home.HomeViewModel;

import javax.security.auth.login.LoginException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Comienza el codigo para colocar icono en el action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        // Finaliza codigo icono action bar
    }

    // Comienza metodo boton crear usuario
    public void botcrearusuario(View view) {
        Intent botcrearusuario = new Intent(this, creaciondeusuario.class);
        startActivity(botcrearusuario);
        // Finaliza metodo boton crear usuario
    }

    // Comienza metodo boton Inicio
    public void botingresar(View view) {
        Intent botingresar = new Intent(this,Login.class);
        startActivity(botingresar);
    }
    // Finaliza metodo boton inicio
}





