package com.example.pruebademdm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    // Comienza metodo boton ingresar
    public void botcrearusuario (View view) {
        Intent botcrearusuario = new Intent(this,creaciondeusuario.class);
        startActivity(botcrearusuario);
    }
}
