package com.example.pruebademdm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class main_resultadosmatch  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_resultadosmatch);

        //Comienza el codigo para colocar icono en el action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        // Finaliza codigo icono action bar
    }
    // Comienza metodo boton ir_al_chat
    public void ir_al_chat(View view) {
        Intent ir_al_chat = new Intent(this, chat.class);
        startActivity(ir_al_chat);
        // Finaliza metodo boton para ir al chat
    }
}


