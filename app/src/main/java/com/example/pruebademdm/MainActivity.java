package com.example.pruebademdm;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.pruebademdm.data.LoginDataSource;
import com.example.pruebademdm.data.LoginRepository;
import com.example.pruebademdm.data.model.LoggedInUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    // Comienza metodo boton crear usuario
    public void botcrearusuario (View view) {
        Intent botcrearusuario = new Intent(this,creaciondeusuario.class);
        startActivity(botcrearusuario);
<<<<<<< HEAD
     // Finaliza metodo boton crear usuario
    }

    // Comienza metodo boton Inicio
    public void botingresar (View view) {
        Intent botingresar = new Intent(this,LoginDataSource.class);
        startActivity(botingresar);
        // Finaliza metodo boton crear usuario
=======

>>>>>>> 54681b2d0a80e4309f496f312f536cd24da13cc5
    }
}

