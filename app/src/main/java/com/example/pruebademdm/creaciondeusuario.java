package com.example.pruebademdm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class creaciondeusuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creaciondeusuario);
    }
    // Comienza metodo boton crear usuario para ir a perfil de usuario
    public void validarusuario(View view) {
        Intent validarusuario = new Intent(this,Perfildeusuario .class);
        startActivity(validarusuario);
        // Finaliza metodo boton crear usuario para ir a perfil de usuario
    }
}


