package com.example.pruebademdm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class creaciondeusuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creaciondeusuario);

        //Comienza el codigo para colocar icono en el action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        // Finaliza codigo icono action bar
    }

    // Comienza metodo boton crear usuario para ir a seleccion de habilidades
    public void validarusuario(View view) {
        Intent validarusuario = new Intent(this,selec_habilidades .class);
        startActivity(validarusuario);
        // Finaliza metodo boton crear usuario para ir a seleccion de habilidades
    }
}



